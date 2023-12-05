package me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.Argument;
import me.ltsoveranakin.ghoulish.client.util.parser.ParserUtil;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import org.jetbrains.annotations.NotNull;

public class EnumArgument<T extends Enum<?>> extends Argument<T> {
    private final Class<T> enumClass;

    public EnumArgument(String name, String desc, Class<T> enumClass) {
        super(name, desc);
        this.enumClass = enumClass;
    }

    @Override
    protected @NotNull T parse(String arg) throws ParseException {
        return ParserUtil.ENUM_PARSER.parseFromClass(arg, enumClass);
    }

    @Override
    protected String getTypeName() {
        T[] values = enumClass.getEnumConstants();
        StringBuilder valuesSb = new StringBuilder();
        
        for (int i = 0; i < values.length; i++) {
            if (i != 0) valuesSb.append(", ");
            valuesSb.append(values[i].name());
        }

        return "enum(" + valuesSb + ")";
    }
}
