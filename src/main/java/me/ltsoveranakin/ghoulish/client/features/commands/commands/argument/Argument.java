package me.ltsoveranakin.ghoulish.client.features.commands.commands.argument;

import me.ltsoveranakin.ghoulish.client.misc.named.NamedDesc;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import org.jetbrains.annotations.NotNull;

public abstract class Argument<K> extends NamedDesc {
    private K argumentVal;
    private boolean optional = false;

    public Argument(String name, String desc) {
        super(name, desc);
    }

    @NotNull
    protected abstract K parse(String arg) throws ParseException;

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

    public void reset() {
        argumentVal = null;
    }
}
