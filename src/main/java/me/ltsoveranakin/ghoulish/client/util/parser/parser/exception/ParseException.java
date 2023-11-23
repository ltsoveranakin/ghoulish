package me.ltsoveranakin.ghoulish.client.util.parser.parser.exception;

import me.ltsoveranakin.ghoulish.client.util.parser.parser.parser.IParser;

public class ParseException extends Exception {
    public ParseException(String value, IParser type) {
        super("Error parsing " + value + (type == null ? " with no parser" : " as " + type));
    }

    public ParseException(String value) {
        this(value, null);
    }
}
