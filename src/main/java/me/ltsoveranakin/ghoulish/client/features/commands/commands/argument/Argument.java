package me.ltsoveranakin.ghoulish.client.features.commands.commands.argument;

import me.ltsoveranakin.ghoulish.client.misc.named.NamedDesc;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import org.jetbrains.annotations.NotNull;

public abstract class Argument<K> extends NamedDesc {
    private K argumentVal;
    private boolean optional = false;
    private final String commandName;

    public Argument(String name, String desc) {
        super(name, desc);
        commandName = name.replaceAll(" ", "-");
    }

    @NotNull
    protected abstract K parse(String arg) throws ParseException;

    protected abstract String getTypeName();

    public void setArg(String argStr) throws ParseException {
        argumentVal = parse(argStr);
    }

    public String getSuggestion(String currentArgStr) {
        return null;
    }

    public K get() {
        return argumentVal;
    }

    public Argument<K> optional() {
        optional = true;
        return this;
    }

    public boolean isOptional() {
        return optional;
    }

    public String getCommandName() {
        return commandName;
    }

    public void reset() {
        argumentVal = null;
    }

    public void help() {
        info(getName());
        info(getDesc());
        info("Type: " + getTypeName());
    }
}
