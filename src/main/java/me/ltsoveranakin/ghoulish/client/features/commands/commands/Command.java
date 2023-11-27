package me.ltsoveranakin.ghoulish.client.features.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.InsufficientArgumentException;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.Argument;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.*;
import me.ltsoveranakin.ghoulish.client.misc.named.NamedDesc;
import me.ltsoveranakin.ghoulish.client.util.ChatUtil;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class Command extends NamedDesc {
    public static String PREFIX = "$";

    private final List<Argument<?>> arguments = new ArrayList<>();

    public Command(String name, String desc) {
        super(name, desc);
    }

    public void info(String msg) {
        ChatUtil.info(getName() + " : " + msg);
    }

    @Nullable
    public String getSuggestion(String[] strArgs) {
        if (strArgs.length == 0 || strArgs.length > arguments.size()) {
            return null;
        }

        try {
            for (int i = 0; i < strArgs.length - 1; i++) {
                Argument<?> arg = arguments.get(i);
                arg.setArg(strArgs[i]);
            }
        } catch (ParseException e) {
            return null;
        }

        String finalArgument = strArgs[strArgs.length - 1];
        Argument<?> argument = arguments.get(strArgs.length - 1);

        return argument.getSuggestion(finalArgument);
    }

    public final void commandIn(String[] stringArgs) throws ParseException, InsufficientArgumentException {
        for (int i = 0; i < stringArgs.length; i++) {
            Argument<?> arg = arguments.get(i);
            arg.setArg(stringArgs[i]);
        }

        for (Argument<?> arg : arguments) {
            if (!arg.isOptional() && arg.get() == null) {
                throw new InsufficientArgumentException();
            }
        }

        handleCommandImpl();

        for (Argument<?> arg : arguments) {
            arg.reset();
        }
    }

    protected abstract void handleCommandImpl();

    private <K extends Argument<?>> K addArgument(K argument) {
        if (!arguments.isEmpty() && arguments.get(arguments.size() - 1).isOptional() && !argument.isOptional()) {
            throw new RuntimeException("Required argument registered after optional argument.");
        }
        arguments.add(argument);
        return argument;
    }

    protected StringArgument addStr(String name, String desc) {
        return addArgument(new StringArgument(name, desc));
    }

    protected StringArgument addStr(String name, String desc, String regexFilter) {
        return addArgument(new StringArgument(name, desc, regexFilter));
    }

    protected ListArgument addList(String name, String desc, List<String> values) {
        return addList(name, desc, () -> values);
    }

    protected ListArgument addList(String name, String desc, ListArgument.GetValues getValues) {
        return addArgument(new ListArgument(name, desc, getValues));
    }

    protected <T extends Enum<?>> EnumArgument<T> addEnum(String name, String desc, Class<T> enumClass) {
        return addArgument(new EnumArgument<>(name, desc, enumClass));
    }

}
