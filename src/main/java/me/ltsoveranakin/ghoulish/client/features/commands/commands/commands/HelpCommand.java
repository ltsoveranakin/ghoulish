package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.CommandManager;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.Argument;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.ListArgument;

import java.util.ArrayList;

public class HelpCommand extends Command {
    private final ListArgument commandName = addList("command name", "the name of the command to choose", () -> CommandManager.COMMANDS
            .stream()
            .map(Command::getName)
            .toList()).optional();

    private final ListArgument argumentName = addList("argument name", "the name of the argument to choose", () -> {
        Command command = CommandManager.getCommand(commandName.get());
        if (command == null) {
            return null;
        }

        return new ArrayList<>(command.getArguments()
                .stream()
                .map(setting -> setting.getName().replaceAll(" ", "-"))
                .toList());
    }).optional();

    public HelpCommand() {
        super("help", "lists commands");
    }

    private void infoCmds() {
        var sb = new StringBuilder();

        for (int i = 0; i < CommandManager.COMMANDS.size(); i++) {
            var module = CommandManager.COMMANDS.get(i);
            sb.append(module.getName()).append(i == CommandManager.COMMANDS.size() - 1 ? "" : ", ");
        }

        info(sb.toString());
    }

    @Override
    protected void handleCommandImpl() {
        if (commandName.get() == null) {
            infoCmds();
            return;
        }

        Command command = CommandManager.getCommand(commandName.get());

        if (command == null) {
            info("No command exists with that name");
            return;
        }

        if (argumentName.get() == null) {
            info(command.getDesc());
            StringBuilder argFmt = new StringBuilder();
            argFmt.append(Command.PREFIX)
                    .append(command.getName());

            for (Argument<?> argument : command.getArguments()) {
                argFmt.append(" <")
                        .append(argument.getName())
                        .append(argument.isOptional() ? "?>" : ">");
            }

            info(argFmt.toString());
        } else {
            String argName = argumentName.get().replaceAll("-", " ");
            Argument<?> argument = command.getArgument(argName);

            if (argument == null) {
                error("No such argument exists");
                return;
            }

            argument.help();
        }
    }
}
