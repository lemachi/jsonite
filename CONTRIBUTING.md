# Contributing

Thanks for helping. Jsonite is small on purpose: a JSON value model, a
streaming parser and writer, nothing else.

## Before you start

For anything beyond a small fix, open an issue first so we can agree on
the approach. Behavior changes need a reason, since applications depend on
the current behavior; the [changelog](CHANGELOG.md) shows the kind of
reasoning that helps.

## Making a change

1. Fork and branch from `master`.
2. Run `./gradlew build` with JDK 17 or newer. It runs the tests and
   fails below 90% line or branch coverage.
3. Add a test that fails without your change. Assert the behavior, not
   just that the code runs.
4. Keep the bytecode at Java 11 and add no runtime dependencies.
5. Add a line to the changelog under an "Unreleased" heading.
6. Open a pull request; CI builds it on JDK 17 and 21.

## Releases

A release is a tag `x.y.z` on `master`. JitPack builds it on first request.
