package io.github.xeruvimov.jproblem.render;

import io.github.xeruvimov.jproblem.problem.Problem;

public class DefaultTextRender {

    public static String render(Problem problem) {
        return render("A problem happened", problem);
    }

    public static String render(String header, Problem problem) {
        var sb = new StringBuilder();
        sb.append(header);

        problem.getId().ifPresent(id -> {
            sb.append("\n");
            sb.append("\n");
            sb.append("Problem ID : ").append(id.getId());
        });

        problem.getWhere().ifPresent(where -> {
            sb.append("\n");
            sb.append("\n");
            sb.append("Where? : ").append(where);
        });

        sb.append("\n");
        sb.append("\n");
        sb.append("What? : ").append(problem.getShortDescription());

        problem.getWhy().ifPresent(why -> {
            sb.append("\n");
            sb.append("\n");
            sb.append("Why? : ").append(why);
        });

        problem.getLongDescription().ifPresent(longDescription -> {
            sb.append("\n");
            sb.append("\n");
            sb.append("Long description : ").append(longDescription);
        });

        if (problem.getSolutions().size() == 1) {
            sb.append("\n");
            sb.append("\n");
            sb.append("Possible solution : ").append(problem.getSolutions().get(0));
        } else if (!problem.getSolutions().isEmpty()) {
            sb.append("\n");
            sb.append("\n");
            sb.append("Possible solutions : ");
            problem.getSolutions().forEach(solution -> {
                sb.append("\n");
                sb.append("    ").append("- ").append(solution);
            });
        }

        problem.getDocumentationLink().ifPresent(documentationLink -> {
            sb.append("\n");
            sb.append("\n");
            sb.append("Documentation link : ").append(documentationLink);
        });
        return sb.toString();
    }
}
