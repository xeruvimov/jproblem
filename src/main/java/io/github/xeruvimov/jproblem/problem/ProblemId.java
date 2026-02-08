package io.github.xeruvimov.jproblem.problem;

/**
 * Functional interface representing a problem identifier.
 */
public interface ProblemId {
    /**
     * Returns id value.
     *
     * @return id value
     */
    String getId();

    /**
     * Creates problem id from plain string.
     *
     * @param id id value
     * @return problem id instance
     */
    static ProblemId of(String id) {
        return () -> id;
    }
}
