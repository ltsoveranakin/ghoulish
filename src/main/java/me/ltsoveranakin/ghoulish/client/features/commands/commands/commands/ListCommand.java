package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.ListArgument;
import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;

public class ListCommand extends Command {
    private final ListArgument moduleName = addList("module name", "the name of the module to choose", ModuleManager.MODULE_NAMES).optional();

    public ListCommand() {
        super("list", "lists modules");
    }

    @Override
    protected void handleCommandImpl() {
        StringBuilder sb = new StringBuilder();

        if (moduleName.get() != null) {
            Module module = ModuleManager.getModule(moduleName.get());

            if (module == null) {
                error("Module not found");
                return;
            }

            for (Setting<?> setting : module.getSettings()) {
                sb
                        .append("\n")
                        .append(setting.getName())
                        .append(": ")
                        .append(setting.get());
            }

            info(sb.toString());
            return;
        }

        for (String moduleName : ModuleManager.MODULE_NAMES) {
            sb
                    .append("\n")
                    .append(moduleName);
        }

        info(sb.toString());
    }
}
