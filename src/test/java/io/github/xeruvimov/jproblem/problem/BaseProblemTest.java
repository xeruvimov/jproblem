package io.github.xeruvimov.jproblem.problem;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BaseProblemTest {

    @Test
    void constructorMakesDefensiveCopyOfSolutions() {
        List<String> sourceSolutions = new ArrayList<>();
        sourceSolutions.add("First");

        BaseProblem problem = new BaseProblem(
                ProblemId.of("BP-1"),
                "Failure",
                null,
                null,
                sourceSolutions,
                null,
                null,
                null
        );

        sourceSolutions.add("Second");

        assertEquals(1, problem.getSolutions().size());
        assertEquals("First", problem.getSolutions().get(0));
    }

    @Test
    void returnedSolutionsListIsUnmodifiable() {
        BaseProblem problem = new BaseProblem(
                ProblemId.of("BP-2"),
                "Failure",
                null,
                null,
                List.of("Only"),
                null,
                null,
                null
        );

        assertThrows(UnsupportedOperationException.class, () -> problem.getSolutions().add("Another"));
    }
}
