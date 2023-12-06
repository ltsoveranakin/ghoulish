package me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.Argument;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * An argument that has a specified number of values it can choose from. Like an enum, but uses strings for a dynamic approach. Differs from a "joint" argument in that the proceeding arguments are not affected by the ListArgument's value.
 */
public class ListArgument extends Argument<String> {
    private List<String> previousValues = null;
    private final GetValues getValues;

    public ListArgument(String name, String desc, GetValues getValues) {
        super(name, desc);
        this.getValues = getValues;
    }

    @Override
    protected @NotNull String parse(String arg) throws ParseException {
        List<String> values = getValues.get();

        if (!values.contains(arg)) {
            throw new ParseException(arg);
        }

        return arg;
    }

    @Override
    public String getSuggestion(String currentArgStr) {
        for (String value : getValues.get()) {
            if (value.startsWith(currentArgStr)) {
                return value;
            }
        }

        return super.getSuggestion(currentArgStr);
    }

    @Override
    protected String getTypeName() {
        List<String> values = getValues.get();
        if (values == null) {
            return "list( <values not known> )";
        }
        StringBuilder valuesSb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i != 0) valuesSb.append(", ");
            valuesSb.append(values.get(i));
        }

        return "list(" + valuesSb + ")";
    }

    @Override
    public ListArgument optional() {
        return (ListArgument) super.optional();
    }

    public interface GetValues {
        List<String> get();
    }
}
