package me.ltsoveranakin.ghoulish.client.util.parser.parser.exception;

import me.ltsoveranakin.ghoulish.client.util.parser.parser.parser.IParser;
import org.jetbrains.annotations.Nullable;

public class ParseException extends Exception {
    @Nullable
    private final IParser parserType;

    public ParseException(String value, @Nullable IParser type) {
        super("Error parsing " + value + (type == null ? " with no parser" : " as " + type));
        parserType = type;
    }

    @Nullable
    public IParser getParserType() {
        return parserType;
    }

    public ParseException(String value) {
        this(value, null);
    }
}
