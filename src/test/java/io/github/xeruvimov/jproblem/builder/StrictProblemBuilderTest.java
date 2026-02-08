package io.github.xeruvimov.jproblem.builder;

import io.github.xeruvimov.jproblem.problem.Problem;
import io.github.xeruvimov.jproblem.problem.ProblemId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StrictProblemBuilderTest {

    @Test
    void buildsProblemWithRequiredIdAndWhatUsingStringId() {
        Problem problem = StrictProblemBuilder.withId("REQ-1")
                .what("Required fields are present")
                .build();

        assertEquals("REQ-1", problem.getId().orElseThrow().getId());
        assertEquals("Required fields are present", problem.getShortDescription());
    }

    @Test
    void buildsProblemWithProblemIdInstance() {
        Problem problem = StrictProblemBuilder.withId(ProblemId.of("REQ-2"))
                .what("Required fields are present")
                .build();

        assertEquals("REQ-2", problem.getId().orElseThrow().getId());
    }

    @Test
    void buildAsRuntimeExceptionRendersMessage() {
        RuntimeException exception = StrictProblemBuilder.withId("REQ-3")
                .what("Cannot process request")
                .why("Validation failed")
                .buildAsRuntimeException();

        assertTrue(exception.getMessage().contains("Problem ID : REQ-3"));
        assertTrue(exception.getMessage().contains("What? : Cannot process request"));
        assertTrue(exception.getMessage().contains("Why? : Validation failed"));
    }

    @Test
    void buildAsExceptionSetsCause() {
        Throwable rootCause = new IllegalArgumentException("bad input");

        IllegalStateException exception = StrictProblemBuilder.withId("REQ-4")
                .what("Failed to create entity")
                .cause(rootCause)
                .buildAsException(IllegalStateException::new);

        assertSame(rootCause, exception.getCause());
        assertTrue(exception.getMessage().contains("Problem ID : REQ-4"));
    }

    @Test
    void withIdRejectsNullString() {
        NullPointerException error = assertThrows(
                NullPointerException.class,
                () -> StrictProblemBuilder.withId((String) null)
        );
        assertEquals("id must not be null", error.getMessage());
    }

    @Test
    void withIdRejectsNullProblemId() {
        NullPointerException error = assertThrows(
                NullPointerException.class,
                () -> StrictProblemBuilder.withId((ProblemId) null)
        );
        assertEquals("id must not be null", error.getMessage());
    }

    @Test
    void withIdAcceptsBlankAndWhitespaceIds() {
        Problem blank = StrictProblemBuilder.withId("")
                .what("Blank id")
                .build();
        Problem whitespace = StrictProblemBuilder.withId("   ")
                .what("Whitespace id")
                .build();

        assertEquals("", blank.getId().orElseThrow().getId());
        assertEquals("   ", whitespace.getId().orElseThrow().getId());
    }

    @Test
    void whatRejectsNullShortDescription() {
        NullPointerException error = assertThrows(
                NullPointerException.class,
                () -> StrictProblemBuilder.withId("REQ-5").what(null)
        );
        assertEquals("shortDescription must not be null", error.getMessage());
    }
}
