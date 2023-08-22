package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;

public class BindCommand extends Command {
    public BindCommand() {
        super("bind", "binds modules");
    }

    @Override
    public boolean commandIn(String[] args) throws Exception {
        Module m = ModuleManager.getModule(args[0]);

        if (m == null) {
            info("Unable to find module");
            return true;
        }

        if (args.length > 1) {
            if (args[1].equals("none")) {
                m.getBind().set(-1);
                info("Set bind to none");
            } else {
                info("expected 2nd argument to be 'none' or empty");
                return false;
            }
            return true;
        }

        ModuleManager.setBindingModule(m);

        return true;
    }
}
