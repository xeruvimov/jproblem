# JProblem

A simple Java library for helpful exception messages

Feel free to leave an issue or make pull requests

## Example

You could use class `DefaultProblemBuilder` to build some `Problem` and throw it as `RuntimeException`

```java
throw DefaultProblemBuilder.newBuilder()
        .id(() -> "Example ID, provide ID that will help you to identify a problem")
        .what("Test JProblem") //required
        .where("Just in main class")
        .why("I want to show how to use JProblem")
        .documentedAt("https://github.com/xeruvimov/jproblem")
        .withLongDescription("Use this field to provide long description of error; you may need to write some context or anything else")
        .addSolution("Use JProblem to write code that works as indeed")
        .addSolution("Or just ignore this exception")
        .buildAsRuntimeException();
```

The code above will produce next exception in console

```text
Exception in thread "main" java.lang.RuntimeException: A problem happened

Problem ID : Example ID, provide ID that will help you to identify a problem

Where? : Just in main class

What? : Test JProblem

Why? : I want to show how to use JProblem

Long description : Use it field to provide long description of error, may be you will need to write some context or anything else

Possible solutions : 
    - Use JProblem to write code that works as indeed
    - Or just ignore this exception

Documentation link : https://github.com/xeruvimov/jproblem
	at io.github.xeruvimov.jproblem.DefaultProblemBuilder.buildAsRuntimeException(DefaultProblemBuilder.java:72)
	at com.skheruvimov.script.ScriptTestProjApp.main(ScriptTestProjApp.java:27)
```
---
**_Only `what` field is required_**, so you also can make small messages

```java
throw DefaultProblemBuilder.newBuilder()
        .what("I don't want to force my collegues to read 'War and Peace' in every error case")
        .buildAsRuntimeException();
```

```text
Exception in thread "main" java.lang.RuntimeException: A problem happened

What? : I dont want to force my colleges to read 'War and Peace' in every error cases
	at io.github.xeruvimov.jproblem.builder.DefaultProblemBuilder.buildAsRuntimeException(DefaultProblemBuilder.java:72)
	at com.skheruvimov.script.ScriptTestProjApp.main(ScriptTestProjApp.java:20)
```

## Installation

```xml
<dependency>
    <groupId>io.github.xeruvimov</groupId>
    <artifactId>jproblem</artifactId>
    <version>1.0.0</version>
</dependency>
```

```
implementation group: 'io.github.xeruvimov', name: 'jproblem', version: '1.0.0'
```

### inspired by [JDoctor](https://github.com/melix/jdoctor)