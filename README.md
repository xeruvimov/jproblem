# JProblem

A small Java library for building clear, structured exception messages.

## Installation

```xml
<dependency>
    <groupId>io.github.xeruvimov</groupId>
    <artifactId>jproblem</artifactId>
    <version>1.1.0</version>
</dependency>
```

```groovy
implementation group: 'io.github.xeruvimov', name: 'jproblem', version: '1.1.0'
```

## Recommended API (StrictProblemBuilder)

`StrictProblemBuilder` is recommended for new code. It enforces required fields at compile time:
- `id` is required
- `what` is required

```java
import io.github.xeruvimov.jproblem.builder.StrictProblemBuilder;

throw StrictProblemBuilder.withId("AUTH-401")
        .what("Authorization failed")
        .where("Auth filter")
        .why("Access token is missing")
        .addSolution("Provide Bearer token")
        .documentedAt("https://example.com/docs/auth")
        .buildAsException(IllegalArgumentException::new);
```

You can also pass `ProblemId` explicitly:

```java
import io.github.xeruvimov.jproblem.builder.StrictProblemBuilder;
import io.github.xeruvimov.jproblem.problem.ProblemId;

throw StrictProblemBuilder.withId(ProblemId.of("AUTH-401"))
        .what("Authorization failed")
        .buildAsRuntimeException();
```

## Legacy API (DefaultProblemBuilder)

`DefaultProblemBuilder` is still supported for backward compatibility.
For new code, prefer `StrictProblemBuilder`.

```java
import io.github.xeruvimov.jproblem.builder.DefaultProblemBuilder;

throw DefaultProblemBuilder.newBuilder()
        .id("DB-001") // also supports: id(ProblemId.of(...)) and id(() -> "...")
        .what("Database connection failed")
        .why("Connection pool is exhausted")
        .addSolution("Increase pool size")
        .buildAsRuntimeException();
```

## HTTP/JSON Friendly Single-Line Message

When exception message is returned in JSON, line breaks appear as `\n`.
Use `DefaultTextRender.compactToSingleLine(...)` to normalize a rendered message to one line:

```java
import io.github.xeruvimov.jproblem.render.DefaultTextRender;

String messageForHttp = DefaultTextRender.compactToSingleLine(exception.getMessage());
```

Example output:

```text
A problem happened | Problem ID : AUTH-401 | Where? : Auth filter | What? : Authorization failed | Why? : Access token is missing
```

### inspired by [JDoctor](https://github.com/melix/jdoctor)
