package io.github.xeruvimov.jproblem.builder;

import io.github.xeruvimov.jproblem.problem.BaseProblem;
import io.github.xeruvimov.jproblem.problem.Problem;
import io.github.xeruvimov.jproblem.problem.ProblemId;
import io.github.xeruvimov.jproblem.render.DefaultTextRender;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DefaultProblemBuilder implements Builder {
    private final ProblemData problemData;

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
    public Builder cause(Throwable cause) {
        problemData.cause = cause;
        return this;
    }

    @Override
    public Problem build() {
        return new BaseProblem(problemData.id,
                problemData.shortDescription,
                problemData.longDescription,
                problemData.reason,
                problemData.solutions,
                problemData.link,
                problemData.context,
                problemData.cause);
    }

    @Override
    public RuntimeException buildAsRuntimeException() {
        return buildAsException(RuntimeException::new);
    }

    @Override
    public <T extends Exception> T buildAsException(Function<String, T> exception) {
        var problem = build();
        var result = exception.apply(DefaultTextRender.render(problem));
        problem.getCause().ifPresent(result::initCause);
        return result;
    }

    private static class ProblemData {
        ProblemId id;
        String shortDescription;
        String reason;
        String longDescription;
        List<String> solutions = new ArrayList<>();
        String link;
        String context;
        Throwable cause;
    }

}
