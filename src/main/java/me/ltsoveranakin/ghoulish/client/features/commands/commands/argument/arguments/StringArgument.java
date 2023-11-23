package me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.Argument;
import me.ltsoveranakin.ghoulish.client.util.parser.ParserUtil;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StringArgument extends Argument<String> {
    @Nullable
    private final String regexFilter;

    public StringArgument(String name, String desc, @Nullable String regexFilter) {
        super(name, desc);

        this.regexFilter = regexFilter;
    }

    public StringArgument(String name, String desc) {
        this(name, desc, null);
    }

    @Override
    public @NotNull String parse(String arg) throws ParseException {
        if(regexFilter != null && !arg.matches(regexFilter)) {
            throw new ParseException(arg);
        }
        return ParserUtil.STRING_PARSER.parse(arg);
    }

    @Override
    public StringArgument optional() {
        return (StringArgument) super.optional();
    }


}
