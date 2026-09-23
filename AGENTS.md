# Project Instructions

## Fundamental Principles

* Write clean, simple, and readable code.
* Implement features in the simplest possible way.
* Keep files small and focused.
* Use clear and consistent naming.
* Write simple, clean, and modular code.
* Use clear and easy-to-understand language.
* Keep explanations short and use short sentences.
* Prefer existing project patterns and components over introducing new approaches.
* Avoid unnecessary complexity and unnecessary changes.

## Error Fixing

* Do not jump to conclusions.
* Consider multiple possible causes before deciding what is causing the problem.
* Inspect the relevant code and error context before making changes.
* Explain the problem in plain English.
* Make the minimum necessary changes to fix the error.
* Change as few lines as reasonably possible.
* Do not refactor unrelated code while fixing an error.
* If the cause of a strange or unclear error cannot be determined with reasonable confidence, tell the user that the issue may require investigation with other tools rather than guessing.

## Building Process

* To build the project, run:

```bash
./gradlew assembleDebug
```

* After making code changes, build the project when appropriate to verify that the changes compile.
* If the build fails, inspect the build output and determine whether the failure is related to the changes before making further modifications.

## Comments

* Do not randomly delete existing comments.
* If a comment appears to be outdated or incorrect, inform the user and ask them to verify it before deleting it.
* Write comments only when they explain **why** something is done.
* Use comments for complex business logic or non-obvious decisions.
* Do not write comments that merely describe **what** the code is doing. The code should make the "what" clear through good naming and structure.

## Change Discipline

* Before modifying code, understand the relevant existing implementation.
* Prefer minimal, focused changes.
* Do not modify unrelated files.
* Do not introduce unnecessary dependencies.
* Do not rewrite working code unless there is a clear reason to do so.
* Preserve the existing architecture and coding patterns unless the task specifically requires changing them.
