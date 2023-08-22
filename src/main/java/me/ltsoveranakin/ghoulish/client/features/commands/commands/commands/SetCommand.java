package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.storage.StorageHandler;

public class SetCommand extends Command {
    public SetCommand() {
        super("set", "sets module settings");
    }

    @Override
    public boolean commandIn(String[] args) throws Exception {
        Module m = ModuleManager.getModule(args[0]);

        if (m == null) {
            info("unable to find module");
            return true;
        }

        Setting<?> s = m.getSetting(args[1].replaceAll("-", ""));

        if (s == null) {
            info("unable to find setting");
            return true;
        }

        s.set(args[2]);

        StorageHandler.saveConfig();

        info("Set setting " + args[1] + " to " + args[2]);
        return true;
    }
}
