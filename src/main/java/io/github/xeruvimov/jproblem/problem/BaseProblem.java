package io.github.xeruvimov.jproblem.problem;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BaseProblem implements Problem {

    public BaseProblem(ProblemId problemId,
                       String shortDescription,
                       String longDescription,
                       String reason,
                       List<String> solutions,
                       String documentationLink,
                       String context) {
        this.problemId = problemId;
        this.shortDescription = Objects.requireNonNull(shortDescription, "shortDescription must not be null");
        this.longDescription = longDescription;
        this.reason = reason;
        this.solutions = Objects.requireNonNull(solutions);
        this.documentationLink = documentationLink;
        this.context = context;
    }

    private final ProblemId problemId;
    private final String shortDescription;
    private final String longDescription;
    private final String reason;
    private final List<String> solutions;
    private final String documentationLink;
    private final String context;

    @Override
    public Optional<ProblemId> getId() {
        return Optional.ofNullable(problemId);
    }

    @Override
    public String getShortDescription() {
        return this.shortDescription;
    }

    @Override
    public Optional<String> getLongDescription() {
        return Optional.ofNullable(this.longDescription);
    }

    @Override
    public Optional<String> getWhy() {
        return Optional.ofNullable(this.reason);
    }

    @Override
    public List<String> getSolutions() {
        return this.solutions;
    }

    @Override
    public Optional<String> getDocumentationLink() {
        return Optional.ofNullable(this.documentationLink);
    }

    @Override
    public Optional<String> getWhere() {
        return Optional.ofNullable(this.context);
    }
}
