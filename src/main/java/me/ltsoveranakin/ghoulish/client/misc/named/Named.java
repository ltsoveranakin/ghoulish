package me.ltsoveranakin.ghoulish.client.misc.named;

import me.ltsoveranakin.ghoulish.client.util.ChatUtil;
import net.minecraft.text.MutableText;

import static me.ltsoveranakin.ghoulish.client.util.TextUtil.green;
import static me.ltsoveranakin.ghoulish.client.util.TextUtil.red;

public class Named {
    private final String name;

    public Named(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    protected void info(MutableText txt) {
        ChatUtil.info(green(getName()), txt);
    }

    protected void info(String txt) {
        ChatUtil.info(green(getName()), green(txt));
    }

    protected void error(String txt) {
        ChatUtil.info(green(getName()), red(txt));
    }
}
