package com.mictale.jsonite.stream;

import java.io.IOException;
import java.io.Writer;

/**
 * A {@link JsonStreamConsumer} with formatted output.
 */
public class JsonPrettyStreamConsumer extends JsonStreamConsumer {

    private static final String SPACES = "    ";

    private int indent;

    public JsonPrettyStreamConsumer(Writer writer) {
        super(writer);
    }

    protected void newLine() throws IOException {
        writer.write("\n");

        for (int i = 0; i < indent; i++) {
            writer.write(SPACES);
        }
    }

    @Override
    protected void space() throws IOException {
        writer.write(" ");
    }

    protected void addIndent() throws IOException {
        indent++;
        newLine();
    }

    protected void removeIndent() throws IOException {
        indent--;
        newLine();
    }


}
