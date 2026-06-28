package me.ltsoveranakin.ghoulish.client.features.commands.commands.commands;

import me.ltsoveranakin.ghoulish.client.features.commands.commands.Command;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.ListArgument;
import me.ltsoveranakin.ghoulish.client.features.commands.commands.argument.arguments.StringArgument;
import me.ltsoveranakin.ghoulish.client.features.modules.BindingModule;
import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.util.KeyUtil;

public class BindCommand extends Command {
    private final ListArgument moduleName = addList("module name", "the name of the module to choose", ModuleManager.MODULE_NAMES);
    private final StringArgument bindKey = addStr("none bind", "bind to none").optional();

    public BindCommand() {
        super("bind", "binds modules");
    }

    @Override
    protected void handleCommandImpl() {
        Module module = ModuleManager.getModule(moduleName.get());

        if (module == null) {
            info("Unable to find module");
            return;
        }

        if (bindKey.get() == null) {
            ModuleManager.setBindingModule(new BindingModule(module, module.getBind()));
            return;
        }

        if (bindKey.get().equalsIgnoreCase("none")) {
            module.getBind().set(-1);
            info("Set bind to none");
            return;
        }

        int keyCode = KeyUtil.getKeyCode(bindKey.get());

        if (keyCode == -1) {
            info("Invalid key");
            return;
        }

        module.getBind().set(keyCode);
        info("Set bind to " + bindKey.get());
    }
}
