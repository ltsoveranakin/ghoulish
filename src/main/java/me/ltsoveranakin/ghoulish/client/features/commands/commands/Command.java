package me.ltsoveranakin.ghoulish.client.features.commands.commands;

import me.ltsoveranakin.ghoulish.client.misc.NamedDesc;
import me.ltsoveranakin.ghoulish.client.util.ChatUtil;

public abstract class Command extends NamedDesc {
    public Command(String name, String desc) {
        super(name, desc);
    }

    public abstract boolean commandIn(String[] args) throws Exception;

    public void info(String msg) {
        ChatUtil.info(getName() + " : " + msg);
    }
}
