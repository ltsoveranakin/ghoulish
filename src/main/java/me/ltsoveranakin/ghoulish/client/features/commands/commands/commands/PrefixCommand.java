package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.StringArgument;
import me.ltsoveranakin.ghoulish.client.storage.StorageHandler;

public class PrefixCommand extends Command {
    private final StringArgument prefix = addStr("glsh_prefix", "The new prefix");

    public PrefixCommand() {
        super("prefix", "Sets the command prefix");
    }

    @Override
    protected void handleCommandImpl() {
        Command.PREFIX = prefix.get();

        StorageHandler.saveClientData();
        info("Prefix set to " + Command.PREFIX);
    }
}
