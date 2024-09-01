package io.github.xeruvimov.jproblem.builder;

import io.github.xeruvimov.jproblem.problem.Problem;
import io.github.xeruvimov.jproblem.problem.ProblemId;

public interface Builder {
    Builder id(ProblemId id);

    Builder what(String shortDescription);

    Builder why(String reason);

    Builder where(String context);

    Builder withLongDescription(String longDescription);

    Builder addSolution(String solution);

    Builder documentedAt(String link);

    Problem build();

    RuntimeException buildAsRuntimeException();
}
