package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.ListArgument;
import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;

import java.util.ArrayList;

public class GetCommand extends Command {
    private final ListArgument moduleName = addList("module name", "the name of the module to choose", ModuleManager.MODULE_NAMES);
    private final ListArgument settingName = addList("setting name", "the name of the setting to choose", () -> {
        Module module = ModuleManager.getModule(moduleName.get());
        if (module == null) {
            return null;
        }

        return new ArrayList<>(module.getSettings()
                .stream()
                .map(setting -> setting.getName().replaceAll(" ", "-"))
                .toList());
    }).optional();

    public GetCommand() {
        super("get", "gets module & setting info");
    }

    @Override
    protected void handleCommandImpl() {
        Module module = ModuleManager.getModule(moduleName.get());

        if (module == null) {
            info("No module exists with that name");
            return;
        }

        if (settingName.get() == null) {
            info(module.getDesc());
            return;
        }

        Setting<?> setting = module.getSetting(settingName.get().replaceAll("-", " "));

        if (setting == null) {
            info("No setting exists in " + module.getName() + " with that name");
            return;
        }

        info(setting.getDesc());

    }
}
