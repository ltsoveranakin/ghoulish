package me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.Argument;
import me.ltsoveranakin.ghoulish.client.util.parser.ParserUtil;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * An argument that has a specified number of values it can choose from. Like an enum, but uses strings for a dynamic approach. Differs from a "joint" argument in that the proceeding arguments are not affected by the ListArgument's value.
 */
public class ListArgument extends Argument<String> {
    private Set<String> values;
    private final GetValues getValues;

    public ListArgument(String name, String desc, GetValues getValues) {
        super(name, desc);
        this.getValues = getValues;

        // don't pass in getValues, since return value might be null
        setValues(List.of());
    }

    @Override
    protected @NotNull String parse(String arg) throws ParseException {
        this.values = new HashSet<>(getValues.get());

        String value = ParserUtil.STRING_PARSER.parse(arg);

        if (!values.contains(value)) {
            throw new ParseException(arg);
        }

        return value;
    }

    public void setValues(List<String> values) {
        this.values = new HashSet<>(values);
    }

    @Override
    public ListArgument optional() {
        return (ListArgument) super.optional();
    }

    public interface GetValues {
        List<String> get();
    }
}
