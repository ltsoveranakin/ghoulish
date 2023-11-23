package me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.number;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.Argument;
import me.ltsoveranakin.ghoulish.client.util.parser.ParserUtil;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.parser.parser.MethodParser;
import org.jetbrains.annotations.NotNull;

public class NumArgument<T extends Number & Comparable<T>> extends Argument<T> {
    private final T min;
    private final T max;
    private final MethodParser<T> parser;

    public NumArgument(String name, String desc, T min, T max, MethodParser<T> parser) {
        super(name, desc);

        this.min = min;
        this.max = max;
        this.parser = parser;
    }

    @Override
    protected @NotNull T parse(String arg) throws ParseException {
        return parser.parse(arg);
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public static NumArgument<Integer> createInt(String name, String desc, int min, int max) {
        return new NumArgument<>(name, desc, min, max, ParserUtil.INTEGER_PARSER);
    }
}
