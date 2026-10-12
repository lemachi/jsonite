# Changelog

## 1.0.1

The first real release. The tag `1.0.0` was pushed by mistake onto the
2021 commit before this work was merged, and JitPack built it from there;
do not use `1.0.0`.

The first published release. It brings the
library in line with the copy GPS Essentials has carried since 2021 and
fixes what new tests found.

Behavior changes:

- `JsonNumber.of` and `JsonValue.of` accept NaN and infinity again. Writing
  such a number throws `BrokenStreamException`, since JSON has no syntax
  for it. Applications hold such values at run time; the check belongs
  where JSON is produced.
- `JsonValueConsumer.getValue` throws `JsonException` for an incomplete
  token stream, as its other errors do, instead of `IllegalStateException`.
- `JsonArray.equals` compares elements in order. It used to compare sizes
  and `containsAll`, so `[1, 2]` equalled `[2, 1]` and `[1, 1, 2]` equalled
  `[1, 2, 2]`.

Fixes:

- `JsonArray` and `JsonObject` implement `hashCode`, consistent with
  `equals`, so they work as keys in hash maps and sets.
- The parser no longer loops forever on an unterminated string.
- The parser rejects unknown escapes such as `\x` and malformed `\u`
  escapes instead of dropping or garbling them, and decodes surrogate pairs
  written as two `\u` escapes.
- The parser accepts `+` in exponents (`2.5E+2`) and rejects a sign or an
  exponent without digits instead of producing no value or a
  `NumberFormatException`.
- The writer escapes all control characters, not only the five with names.
- The pretty printer indents nested containers, and keeps empty ones on
  one line. `Transformation.asString(producer, true)` pretty prints.
- `JsonValue.objectValue` names the right type in its error.

Build:

- Gradle 9 with the Kotlin DSL, Maven Central instead of jcenter, Java 11
  bytecode, `maven-publish`, sources and javadoc jars, JitPack.
- `@NonNull` comes from JSpecify instead of androidx, so Kotlin sees the
  nullness and nothing pulls in Android.
- JaCoCo with a 90% line and branch gate; tests grew from 58 to 128.
