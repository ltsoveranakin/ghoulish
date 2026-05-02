package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.ListArgument;
import me.ltsoveranakin.ghoulish.client.features.modules.conglomeration.Conglomeration;
import me.ltsoveranakin.ghoulish.client.features.modules.conglomeration.ConglomerationManager;

import java.util.Objects;

public class ConglomerationCommand extends Command {
    private final ListArgument conglomerationName = addList("conglomeration name", "the name of the conglomeration to enable/disable", ConglomerationManager.CONGLOMERATION_NAMES);

    public ConglomerationCommand() {
        super("conglomeration", "perform actions on conglomerations");
    }

    @Override
    protected void handleCommandImpl() {
        Conglomeration conglomeration = Objects.requireNonNull(ConglomerationManager.getConglomeration(conglomerationName.get()));

        conglomeration.enable();
    }
}
