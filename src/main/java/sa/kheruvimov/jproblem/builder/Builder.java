package sa.kheruvimov.jproblem.builder;

import sa.kheruvimov.jproblem.problem.Problem;
import sa.kheruvimov.jproblem.problem.ProblemId;

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
