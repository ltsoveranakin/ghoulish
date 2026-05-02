package me.ltsoveranakin.ghoulish.client.features.modules.conglomeration;

import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.misc.named.NamedDesc;

import java.util.HashSet;
import java.util.Set;

public class Conglomeration extends NamedDesc {
    private final Set<Module> disallowList = new HashSet<>();
    private final Set<Module> allowList = new HashSet<>();

    public Conglomeration(String name, String desc) {
        super(name, desc);
    }

    protected void allow(Class<? extends Module> moduleClass) {
        allowList.add(ModuleManager.getModule(moduleClass));
    }

    protected void disallow(Class<? extends Module> moduleClass) {
        disallowList.add(ModuleManager.getModule(moduleClass));
    }

    public void enable() {
        for (Module module : allowList) {
            module.tryEnable();
        }

        for (Module module : disallowList) {
            module.tryDisable();
        }
    }
}
