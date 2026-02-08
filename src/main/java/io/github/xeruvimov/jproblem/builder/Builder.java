package io.github.xeruvimov.jproblem.builder;

import io.github.xeruvimov.jproblem.problem.Problem;
import io.github.xeruvimov.jproblem.problem.ProblemId;

import java.util.function.Function;

/**
 * Legacy fluent builder contract for constructing {@link Problem} instances.
 * <p>
 * For new code, prefer {@link StrictProblemBuilder} because it enforces required fields
 * ({@code id} and {@code what}) at compile time.
 */
public interface Builder {
    /**
     * Sets problem id.
     *
     * @param id problem id provider
     * @return current builder
     */
    Builder id(ProblemId id);

    /**
     * Sets problem id using plain string value.
     *
     * @param id problem id
     * @return current builder
     */
    default Builder id(String id) {
        return id(() -> id);
    }

    /**
     * Sets short problem description.
     *
     * @param shortDescription short problem description
     * @return current builder
     */
    Builder what(String shortDescription);

    /**
     * Sets reason text.
     *
     * @param reason reason text
     * @return current builder
     */
    Builder why(String reason);

    /**
     * Sets context where the problem happened.
     *
     * @param context context description
     * @return current builder
     */
    Builder where(String context);

    /**
     * Sets long problem description.
     *
     * @param longDescription long description
     * @return current builder
     */
    Builder withLongDescription(String longDescription);

    /**
     * Adds possible solution entry.
     *
     * @param solution solution text
     * @return current builder
     */
    Builder addSolution(String solution);

    /**
     * Sets documentation link.
     *
     * @param link documentation link
     * @return current builder
     */
    Builder documentedAt(String link);

    /**
     * Sets root cause throwable.
     *
     * @param cause root cause
     * @return current builder
     */
    Builder cause(Throwable cause);

    /**
     * Builds problem object.
     *
     * @return constructed problem
     */
    Problem build();

    /**
     * Builds a {@link RuntimeException} with rendered problem text.
     *
     * @return runtime exception
     */
    RuntimeException buildAsRuntimeException();

    /**
     * Builds custom exception with rendered problem text.
     *
     * @param exception exception factory
     * @param <T> exception type
     * @return exception instance
     */
    <T extends Exception> T buildAsException(Function<String, T> exception);
}
