package io.github.xeruvimov.jproblem.problem;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface Problem {
    default Optional<ProblemId> getId() {
        return Optional.empty();
    }

    String getShortDescription();

    default Optional<String> getLongDescription() {
        return Optional.empty();
    }

    default Optional<String> getWhy() {
        return Optional.empty();
    }

    default List<String> getSolutions() {
        return Collections.emptyList();
    }

    default Optional<String> getDocumentationLink() {
        return Optional.empty();
    }

    default Optional<String> getWhere() {
        return Optional.empty();
    }

    default Optional<Throwable> getCause() {
        return Optional.empty();
    }
}
