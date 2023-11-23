package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.ListArgument;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.StringArgument;
import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.storage.StorageHandler;

import java.util.ArrayList;

public class SetCommand extends Command {
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
    });
    private final StringArgument settingValue = addStr("setting value", "the value to set the setting to");

    public SetCommand() {
        super("set", "sets module settings");
    }

    @Override
    protected void handleCommandImpl() {
        Module module = ModuleManager.getModule(moduleName.get());

        if (module == null) {
            info("unable to find module");
            return;
        }

        Setting<?> setting = module.getSetting(settingName.get().replaceAll("-", " "));

        if (setting == null) {
            info("unable to find setting");
            return;
        }
        try {
            setting.set(settingValue.get());
        } catch (Exception e) {
            info("unable to set setting, unable to parse value: " + settingValue.get() + " for type: " + setting.getSettingType());
            return;
        }

        StorageHandler.saveConfig();

        info("Set setting " + settingName.get() + " to " + settingValue.get());
    }
}
