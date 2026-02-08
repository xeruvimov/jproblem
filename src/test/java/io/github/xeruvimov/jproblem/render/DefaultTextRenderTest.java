package io.github.xeruvimov.jproblem.render;

import io.github.xeruvimov.jproblem.builder.DefaultProblemBuilder;
import io.github.xeruvimov.jproblem.problem.Problem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultTextRenderTest {

    @Test
    void renderShowsSingleSolutionLabelForOneSolution() {
        Problem problem = DefaultProblemBuilder.newBuilder()
                .what("Single solution case")
                .addSolution("Do one thing")
                .build();

        String rendered = DefaultTextRender.render(problem);

        assertTrue(rendered.contains("Possible solution : Do one thing"));
    }

    @Test
    void renderShowsListLabelForMultipleSolutions() {
        Problem problem = DefaultProblemBuilder.newBuilder()
                .what("Multiple solutions case")
                .addSolution("First option")
                .addSolution("Second option")
                .build();

        String rendered = DefaultTextRender.render(problem);

        assertTrue(rendered.contains("Possible solutions : "));
        assertTrue(rendered.contains("    - First option"));
        assertTrue(rendered.contains("    - Second option"));
    }

    @Test
    void compactToSingleLinePreservesSections() {
        String input = "A problem happened\n\nWhat? : Something failed\nWhy? : Bad input\n\nPossible solutions : \n    - Retry";

        String compacted = DefaultTextRender.compactToSingleLine(input);

        assertEquals(
                "A problem happened | What? : Something failed Why? : Bad input | Possible solutions : - Retry",
                compacted
        );
    }

    @Test
    void renderWithOnlyRequiredFieldsDoesNotPrintOptionalSections() {
        Problem problem = DefaultProblemBuilder.newBuilder()
                .what("Only required")
                .build();

        String rendered = DefaultTextRender.render(problem);

        assertEquals("A problem happened\n\nWhat? : Only required", rendered);
    }

    @Test
    void renderWithNullHeaderKeepsFormatting() {
        Problem problem = DefaultProblemBuilder.newBuilder()
                .what("Header test")
                .build();

        String rendered = DefaultTextRender.render(null, problem);

        assertEquals("null\n\nWhat? : Header test", rendered);
    }

    @Test
    void renderSupportsEmptyAndWhitespaceValues() {
        Problem problem = DefaultProblemBuilder.newBuilder()
                .id("  ")
                .what("")
                .where(" ")
                .why("   ")
                .withLongDescription(" ")
                .addSolution(" ")
                .documentedAt(" ")
                .build();

        String rendered = DefaultTextRender.render(problem);

        assertTrue(rendered.contains("Problem ID :   "));
        assertTrue(rendered.contains("Where? :  "));
        assertTrue(rendered.contains("What? : "));
        assertTrue(rendered.contains("Why? :    "));
        assertTrue(rendered.contains("Long description :  "));
        assertTrue(rendered.contains("Possible solution :  "));
        assertTrue(rendered.contains("Documentation link :  "));
    }

    @Test
    void compactToSingleLineReturnsNullForNullInput() {
        assertEquals(null, DefaultTextRender.compactToSingleLine(null));
    }

    @Test
    void compactToSingleLineNormalizesMixedLineEndingsAndWhitespace() {
        String input = "Header\r\n\r\nWhat? : Failed\t\t\rWhy? : Bad  data\n\n\nPossible solution : Retry";

        String compacted = DefaultTextRender.compactToSingleLine(input);

        assertEquals("Header | What? : Failed Why? : Bad data | Possible solution : Retry", compacted);
    }

    @Test
    void renderProducesExactTextForMultipleSolutionsGoldenMaster() {
        Problem problem = DefaultProblemBuilder.newBuilder()
                .id("GM-1")
                .where("Service layer")
                .what("Cannot execute")
                .why("Dependency timeout")
                .withLongDescription("Remote system did not respond in time")
                .addSolution("Retry request")
                .addSolution("Increase timeout")
                .documentedAt("https://example.com/docs/timeouts")
                .build();

        String rendered = DefaultTextRender.render(problem);

        assertEquals(
                "A problem happened\n\n" +
                        "Problem ID : GM-1\n\n" +
                        "Where? : Service layer\n\n" +
                        "What? : Cannot execute\n\n" +
                        "Why? : Dependency timeout\n\n" +
                        "Long description : Remote system did not respond in time\n\n" +
                        "Possible solutions : \n" +
                        "    - Retry request\n" +
                        "    - Increase timeout\n\n" +
                        "Documentation link : https://example.com/docs/timeouts",
                rendered
        );
    }
}
