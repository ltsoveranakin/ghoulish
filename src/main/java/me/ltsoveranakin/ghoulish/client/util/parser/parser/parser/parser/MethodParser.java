package me.ltsoveranakin.ghoulish.client.util.parser.parser.parser.parser;

import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;

public class MethodParser<T> implements IStdParser<T> {
    private final ParseMethod<T> parseMethod;

    public MethodParser(ParseMethod<T> parseMethod) {
        this.parseMethod = parseMethod;
    }

    @Override
    public T parse(String string) throws ParseException {
        try {
            return parseMethod.parse(string);
        } catch (Exception e) {
            throw new ParseException(string, this);
        }
    }

    @FunctionalInterface
    public interface ParseMethod<T> {
        T parse(String string) throws Exception;
    }
}
