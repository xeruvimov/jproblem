package io.github.xeruvimov.jproblem.render;

import io.github.xeruvimov.jproblem.problem.Problem;

/**
 * Utility class for rendering {@link Problem} into human-readable text.
 */
public class DefaultTextRender {

    /**
     * Renders problem text with default header.
     *
     * @param problem problem object
     * @return rendered text
     */
    public static String render(Problem problem) {
        return render("A problem happened", problem);
    }

    /**
     * Renders problem text with custom header.
     *
     * @param header header text
     * @param problem problem object
     * @return rendered text
     */
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

    /**
     * Compacts a rendered multi-line problem text into a single line while preserving section boundaries.
     * <p>
     * The method treats blank lines as section separators and converts them to {@code " | "}.
     * Single line breaks inside a section are converted to spaces, then repeated spaces are collapsed.
     *
     * @param renderedProblemText rendered problem text (for example, from {@link #render(Problem)})
     * @return a normalized single-line representation convenient for HTTP JSON responses
     */
    public static String compactToSingleLine(String renderedProblemText) {
        if (renderedProblemText == null) {
            return null;
        }
        return renderedProblemText
                .replace("\r\n", "\n")
                .replace('\r', '\n')
                .replaceAll("\\n\\s*\\n+", " | ")
                .replace('\n', ' ')
                .replaceAll("\\s{2,}", " ")
                .trim();
    }
}
