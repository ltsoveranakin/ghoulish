package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.storage.StorageHandler;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("toggle", "toggles modules");
    }

    @Override
    public boolean commandIn(String[] args) {
        Module m = ModuleManager.getModule(args[0]);
        if (m != null) {
            m.toggle();
            StorageHandler.saveConfig();
            return true;
        }

        info("Unable to find module");
        return false;
    }
}
