package io.github.xeruvimov.jproblem.problem;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Immutable read-only view of a problem description used for rendering and exception creation.
 */
public interface Problem {
    /**
     * Returns optional problem id.
     *
     * @return optional problem id
     */
    default Optional<ProblemId> getId() {
        return Optional.empty();
    }

    /**
     * Returns short problem description.
     *
     * @return short description
     */
    String getShortDescription();

    /**
     * Returns optional long description.
     *
     * @return optional long description
     */
    default Optional<String> getLongDescription() {
        return Optional.empty();
    }

    /**
     * Returns optional reason.
     *
     * @return optional reason
     */
    default Optional<String> getWhy() {
        return Optional.empty();
    }

    /**
     * Returns possible solutions list.
     *
     * @return possible solutions
     */
    default List<String> getSolutions() {
        return Collections.emptyList();
    }

    /**
     * Returns optional documentation link.
     *
     * @return optional documentation link
     */
    default Optional<String> getDocumentationLink() {
        return Optional.empty();
    }

    /**
     * Returns optional context where problem happened.
     *
     * @return optional context
     */
    default Optional<String> getWhere() {
        return Optional.empty();
    }

    /**
     * Returns optional root cause throwable.
     *
     * @return optional cause
     */
    default Optional<Throwable> getCause() {
        return Optional.empty();
    }
}
