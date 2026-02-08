package io.github.xeruvimov.jproblem.builder;

import io.github.xeruvimov.jproblem.problem.Problem;
import io.github.xeruvimov.jproblem.problem.ProblemId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultProblemBuilderTest {

    @Test
    void idCanBeSetViaStringOverload() {
        Problem problem = DefaultProblemBuilder.newBuilder()
                .id("SIMPLE-ID")
                .what("Failure")
                .build();

        assertTrue(problem.getId().isPresent());
        assertEquals("SIMPLE-ID", problem.getId().get().getId());
    }

    @Test
    void idCanBeSetViaProblemIdFactory() {
        Problem problem = DefaultProblemBuilder.newBuilder()
                .id(ProblemId.of("FACTORY-ID"))
                .what("Failure")
                .build();

        assertTrue(problem.getId().isPresent());
        assertEquals("FACTORY-ID", problem.getId().get().getId());
    }

    @Test
    void buildFailsWhenShortDescriptionIsMissing() {
        NullPointerException error = assertThrows(
                NullPointerException.class,
                () -> DefaultProblemBuilder.newBuilder().build()
        );
        assertEquals("shortDescription must not be null", error.getMessage());
    }

    @Test
    void buildAsExceptionIncludesRenderedMessageAndCause() {
        Throwable rootCause = new IllegalStateException("Root cause");
        ProblemId problemId = () -> "EXAMPLE-1";

        IllegalArgumentException exception = DefaultProblemBuilder.newBuilder()
                .id(problemId)
                .where("Request handler")
                .what("Cannot parse payload")
                .why("Input is malformed")
                .withLongDescription("JSON payload contains invalid field types")
                .addSolution("Validate payload with JSON schema before parsing")
                .documentedAt("https://example.com/docs/payload")
                .cause(rootCause)
                .buildAsException(IllegalArgumentException::new);

        assertSame(rootCause, exception.getCause());
        assertTrue(exception.getMessage().contains("A problem happened"));
        assertTrue(exception.getMessage().contains("Problem ID : EXAMPLE-1"));
        assertTrue(exception.getMessage().contains("Where? : Request handler"));
        assertTrue(exception.getMessage().contains("What? : Cannot parse payload"));
        assertTrue(exception.getMessage().contains("Why? : Input is malformed"));
        assertTrue(exception.getMessage().contains("Long description : JSON payload contains invalid field types"));
        assertTrue(exception.getMessage().contains("Possible solution : Validate payload with JSON schema before parsing"));
        assertTrue(exception.getMessage().contains("Documentation link : https://example.com/docs/payload"));
    }

    @Test
    void builtProblemDoesNotChangeAfterBuilderMutation() {
        Builder builder = DefaultProblemBuilder.newBuilder()
                .what("Failure")
                .addSolution("First solution");

        Problem builtProblem = builder.build();
        builder.addSolution("Second solution");

        assertEquals(1, builtProblem.getSolutions().size());
        assertEquals("First solution", builtProblem.getSolutions().get(0));
    }

    @Test
    void problemSolutionsListIsUnmodifiable() {
        Problem problem = DefaultProblemBuilder.newBuilder()
                .what("Failure")
                .addSolution("Fix it")
                .build();

        assertThrows(UnsupportedOperationException.class, () -> problem.getSolutions().add("Another fix"));
    }

    @Test
    void buildAsExceptionFailsWhenFactoryReturnsExceptionWithExistingCause() {
        Throwable existingCause = new IllegalStateException("existing");
        Throwable newCause = new IllegalArgumentException("new");

        IllegalStateException error = assertThrows(
                IllegalStateException.class,
                () -> DefaultProblemBuilder.newBuilder()
                        .id("EX-CAUSE-1")
                        .what("Failure")
                        .cause(newCause)
                        .buildAsException(message -> {
                            RuntimeException ex = new RuntimeException(message);
                            ex.initCause(existingCause);
                            return ex;
                        })
        );
        assertEquals("Can't overwrite cause with java.lang.IllegalArgumentException: new", error.getMessage());
    }

    @Test
    void buildAsExceptionFailsWhenFactoryReturnsNullAndCauseIsPresent() {
        assertThrows(
                NullPointerException.class,
                () -> DefaultProblemBuilder.newBuilder()
                        .id("EX-NULL-1")
                        .what("Failure")
                        .cause(new IllegalArgumentException("new"))
                        .buildAsException(message -> null)
        );
    }
}
