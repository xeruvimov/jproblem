package sa.kheruvimov.jproblem.builder;

import sa.kheruvimov.jproblem.problem.BaseProblem;
import sa.kheruvimov.jproblem.problem.Problem;
import sa.kheruvimov.jproblem.problem.ProblemId;
import sa.kheruvimov.jproblem.render.DefaultTextRender;

import java.util.ArrayList;
import java.util.List;

public class DefaultProblemBuilder implements Builder {
    private ProblemData problemData;

    public DefaultProblemBuilder() {
        this.problemData = new ProblemData();
    }

    public static Builder newBuilder() {
        return new DefaultProblemBuilder();
    }

    @Override
    public Builder id(ProblemId id) {
        problemData.id = id;
        return this;
    }

    @Override
    public Builder what(String shortDescription) {
        problemData.shortDescription = shortDescription;
        return this;
    }

    @Override
    public Builder why(String reason) {
        problemData.reason = reason;
        return this;
    }

    @Override
    public Builder where(String context) {
        problemData.context = context;
        return this;
    }

    @Override
    public Builder withLongDescription(String longDescription) {
        problemData.longDescription = longDescription;
        return this;
    }

    @Override
    public Builder addSolution(String solution) {
        problemData.solutions.add(solution);
        return this;
    }

    @Override
    public Builder documentedAt(String link) {
        problemData.link = link;
        return this;
    }

    @Override
    public Problem build() {
        return new BaseProblem(problemData.id, problemData.shortDescription, problemData.longDescription, problemData.reason, problemData.solutions, problemData.link, problemData.context);
    }

    @Override
    public RuntimeException buildAsRuntimeException() {
        var problem = build();
        return new RuntimeException(DefaultTextRender.render(problem));
    }

    private static class ProblemData {
        ProblemId id;
        String shortDescription;
        String reason;
        String longDescription;
        List<String> solutions = new ArrayList<>();
        String link;
        String context;
    }

}
