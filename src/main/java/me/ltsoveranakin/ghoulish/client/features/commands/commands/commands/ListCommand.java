package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;

public class ListCommand extends Command {

    public ListCommand() {
        super("list", "lists modules");
    }

    @Override
    public boolean commandIn(String[] args) {
        StringBuilder sb = new StringBuilder();

        if(args.length == 1) {
            Module module = ModuleManager.getModule(args[0]);

            if(module == null) {
                error("Module not found");
                return false;
            }

            for(Setting<?> setting : module.getSettings()) {
                sb
                        .append("\n")
                        .append(setting.getName())
                        .append(": ")
                        .append(setting.get());
            }
        } else {
            for(int i = 0; i < ModuleManager.MODULES.size(); i++) {
                var module = ModuleManager.MODULES.get(i);
                sb
                        .append("\n")
                        .append(module.getName());
            }
        }

        info(sb.toString());
        return true;
    }
}
