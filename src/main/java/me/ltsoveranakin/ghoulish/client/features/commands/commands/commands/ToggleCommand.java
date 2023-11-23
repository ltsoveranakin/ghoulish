package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.ListArgument;
import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.storage.StorageHandler;

public class ToggleCommand extends Command {
    private final ListArgument moduleName = addList("module name", "the name of the module to choose", ModuleManager.MODULE_NAMES);

    public ToggleCommand() {
        super("toggle", "toggles modules");
    }

    @Override
    protected void handleCommandImpl() {
        Module m = ModuleManager.getModule(moduleName.get());
        if (m != null) {
            m.toggle();
            StorageHandler.saveConfig();
            return;
        }

        info("Unable to find module");
    }
}
