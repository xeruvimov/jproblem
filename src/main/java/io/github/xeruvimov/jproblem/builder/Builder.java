package io.github.xeruvimov.jproblem.builder;

import io.github.xeruvimov.jproblem.problem.Problem;
import io.github.xeruvimov.jproblem.problem.ProblemId;

import java.util.function.Function;

public interface Builder {
    Builder id(ProblemId id);

    Builder what(String shortDescription);

    Builder why(String reason);

    Builder where(String context);

    Builder withLongDescription(String longDescription);

    Builder addSolution(String solution);

    Builder documentedAt(String link);

    Builder cause(Throwable cause);

    Problem build();

    RuntimeException buildAsRuntimeException();

    <T extends Exception> T buildAsException(Function<String, T> exception);
}
