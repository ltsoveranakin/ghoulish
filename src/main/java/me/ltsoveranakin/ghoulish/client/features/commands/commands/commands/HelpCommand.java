package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.CommandManager;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.ListArgument;

public class HelpCommand extends Command {
    private final ListArgument commandName = addList("command name", "the name of the command to choose", () -> CommandManager.COMMANDS
            .stream()
            .map(Command::getName)
            .toList()).optional();

    public HelpCommand() {
        super("help", "lists commands");
    }

    private void infoCmds() {
        var sb = new StringBuilder();

        for(int i = 0; i < CommandManager.COMMANDS.size(); i++) {
            var module = CommandManager.COMMANDS.get(i);
            sb.append(module.getName()).append(i == CommandManager.COMMANDS.size() - 1 ? "" : ", ");
        }

        info(sb.toString());
    }

    @Override
    protected void handleCommandImpl() {
        if(commandName.get() == null) {
            infoCmds();
            return;
        }

        Command command = CommandManager.getCommand(commandName.get());

        if(command == null) {
            info("No command exists with that name");
            return;
        }

        info(command.getDesc());
    }
}
