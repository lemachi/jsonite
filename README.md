# Jsonite

Jsonite is a fresh take on JSON serialization in Java. 

What you can do with Jsonite:

 * Parse a sequence of characters into an object hierarchy on `JsonValue`s
 * Converts an object hierarchy of `JsonValue`s into a sequence of characters.
 * Write objects into a stream.
 * Read a stream into objects.
 
## Parsing

Here is how you parse a JSON string:

```Java
    JsonValue foo = JsonValue.parse("42");
```

This may look a bit surprising, Jsonite can parse not just objects but also primitives such as
numbers and strings.

Here is a more complex example:

```Java
    JsonValue foo = JsonValue.parse("{\"foo\":42}");
    JsonObject fooObj = foo.asObject();
    JsonValue v = fooObj.get("foo");
    int i = v.intValue();
```

## String Conversions

To convert a hierarchy of `JsonValue` instances back into strings, just call `toString()`:

```Java
    JsonObject obj = new JsonObject();
    obj.put("foo", 42);
    obj.put("bar", "foobar");
    String str = obj.toString(); // {"foo":42,"bar":"foobar"}
```

## JSON Object Hierarchy

Jsonite parses JSON strings into a hierarchy of instances of `JsonValue`. A `JsonValue` can be
any one of

 * `JsonNull` is a null reference. 
 * `JsonBoolean` is a boolean value, either `true` or `false`.
 * `JsonNumber` is an integer or floating point number.
 * `JsonString` is a sequence of characters.
 * `JsonObject` is a map of string-to-`JsonValue` pairs.
 * `JsonArray` is a list of `JsonValue`s.

## Transformations

At the core of Jsonite, there is a streaming API in the `stream` package. The 2 major 
interfaces in this package are `Consumer` and `Producer`. A `Producer` creates a stream
of `Token`s that are consumed by a `Consumer`. 

There are 6 different types of tokens:

 * `START_OBJECT`: Occurs when the start object token `{` appears.   
 * `END_OBJECT`: Occurs when the end object token `}` appears. 
 * `START_ARRAY`: Occurs when the start array token `[` appears.   
 * `END_ARRAY`:  Occurs when the end araay token `]` appears.
 * `MEMBER_NAME`: Occurs when an object property occurs.
 * `PRIMITIVE`: Occurs when a primitive occurs as an element of an array or as a
        property value.
        
Every token has a type associated with is, the `MEMBER_NAME` and `PRIMITIVE` tokens also
have a value.

Here is some sample code to dump the events of a JSON string:

```Java
    JsonValue foo = JsonValue.parse("{\"foo\":[1,42]}");
    final StringBuilder buffer = new StringBuilder();

    Transformation.copy(new Consumer() {
        @Override
        public void append(Token token) throws BrokenStreamException {
            buffer.append(token);
            buffer.append(System.lineSeparator());
        }
    }, foo);
```

The output of this code will be:

```
    START_OBJECT
    MEMBER_NAME:"foo"
    START_ARRAY
    PRIMITIVE:1
    PRIMITIVE:42
    END_ARRAY
    END_OBJECT
```

Transformations can dramatically reduce the memory consumption of your code. The above code
reads a m,inimum amount of characters from the input string to determine the tokens to
emit to the consumer. When done right, you can scan input data of unbound size.

