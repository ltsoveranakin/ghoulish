package me.ltsoveranakin.ghoulish.client.util.parser.parser.exception;

import me.ltsoveranakin.ghoulish.client.util.parser.parser.parser.IParser;

public class OutOfBoundsParseException extends ParseException {
    public OutOfBoundsParseException(Number value, Number min, Number max, IParser type) {
        super(value + " due to number out of bounds of min: " + min + " max: " + max, type);
    }
}
