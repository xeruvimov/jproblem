package io.github.xeruvimov.jproblem.builder;

import io.github.xeruvimov.jproblem.problem.BaseProblem;
import io.github.xeruvimov.jproblem.problem.Problem;
import io.github.xeruvimov.jproblem.problem.ProblemId;
import io.github.xeruvimov.jproblem.render.DefaultTextRender;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Type-safe staged builder that forces users to provide problem id and short description
 * before build methods become available.
 */
public final class StrictProblemBuilder {

    private StrictProblemBuilder() {
    }

    public static WhatStep withId(String id) {
        return withId(ProblemId.of(Objects.requireNonNull(id, "id must not be null")));
    }

    public static WhatStep withId(ProblemId id) {
        StrictBuilder builder = new StrictBuilder();
        builder.problemData.id = Objects.requireNonNull(id, "id must not be null");
        return builder;
    }

    public interface WhatStep {
        OptionalStep what(String shortDescription);
    }

    public interface OptionalStep {
        OptionalStep why(String reason);

        OptionalStep where(String context);

        OptionalStep withLongDescription(String longDescription);

        OptionalStep addSolution(String solution);

        OptionalStep documentedAt(String link);

        OptionalStep cause(Throwable cause);

        Problem build();

        RuntimeException buildAsRuntimeException();

        <T extends Exception> T buildAsException(Function<String, T> exception);
    }

    private static final class StrictBuilder implements WhatStep, OptionalStep {
        private final ProblemData problemData = new ProblemData();

        @Override
        public OptionalStep what(String shortDescription) {
            problemData.shortDescription = Objects.requireNonNull(shortDescription, "shortDescription must not be null");
            return this;
        }

        @Override
        public OptionalStep why(String reason) {
            problemData.reason = reason;
            return this;
        }

        @Override
        public OptionalStep where(String context) {
            problemData.context = context;
            return this;
        }

        @Override
        public OptionalStep withLongDescription(String longDescription) {
            problemData.longDescription = longDescription;
            return this;
        }

        @Override
        public OptionalStep addSolution(String solution) {
            problemData.solutions.add(solution);
            return this;
        }

        @Override
        public OptionalStep documentedAt(String link) {
            problemData.link = link;
            return this;
        }

        @Override
        public OptionalStep cause(Throwable cause) {
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
    }

    private static final class ProblemData {
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
