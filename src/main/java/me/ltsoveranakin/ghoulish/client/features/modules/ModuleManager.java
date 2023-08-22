package me.ltsoveranakin.ghoulish.client.features.modules;

import me.ltsoveranakin.ghoulish.client.event.sub.Subscriptions;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubKey;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubscription;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.client.GuiModule;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.client.VersionSpoof;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.combat.*;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.hud.hud.*;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.misc.DebugModule;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.misc.FakePlayer;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.player.AutoTool;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.player.Eagle;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.player.autoclicker.autoclicker.LeftClicker;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.player.autoclicker.autoclicker.RightClicker;
import me.ltsoveranakin.ghoulish.client.misc.MCInst;
import me.ltsoveranakin.ghoulish.client.storage.StorageHandler;
import me.ltsoveranakin.ghoulish.client.util.ChatUtil;
import me.ltsoveranakin.ghoulish.client.util.KeyUtil;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.*;


public class ModuleManager implements ISubKey, MCInst {
    public static final List<Module> MODULES = new ArrayList<>();
    private static final Map<Class<? extends Module>, Module> moduleMap = new HashMap<>();
    private static final Map<String, Module> moduleNameMap = new HashMap<>();

    private static @Nullable Module bindingModule;

    public static void init() {
        addModules();
        MODULES.sort(Comparator.comparing(Module::getName));
        Subscriptions.addSub(new ModuleManager());
    }

    private static void addModules() {
        add(new GuiModule());
        add(new AutoCrystal());
        add(new VersionSpoof());
        add(new TriggerBot());
        add(new FakePlayer());
        add(new AimAssist());
        add(new ArrayListModule());
        add(new Eagle());
        add(new HudEditorModule());
        add(new AutoTool());
        add(new DebugModule());
        add(new RightClicker());
        add(new LeftClicker());
        add(new TabGUIModule());
        add(new PacketListModule());
    }

    private static <T extends Module> void add(T module) {
        if (moduleMap.containsKey(module.getClass())) {
            throw new RuntimeException("Module " + module.getName() + " already registered!");
        }

        moduleMap.put(module.getClass(), module);
        MODULES.add(module);
        moduleNameMap.put(module.getName(), module);

        if (module instanceof ISubscription) {
            Subscriptions.addSub((ISubscription) module);
        }

        module.getCategory().getModules().add(module);
    }

    public static @Nullable Module getModule(String modName) {
        return moduleNameMap.get(modName);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getModule(Class<T> moduleClass) {
        return (T) moduleMap.get(moduleClass);
    }

    public static @Nullable Module getBindingModule() {
        return bindingModule;
    }

    public static void setBindingModule(@Nullable Module bindingModule) {
        ModuleManager.bindingModule = bindingModule;
        if (bindingModule != null) ChatUtil.info("Binding " + bindingModule.getName() + " ...");
    }

    public static List<Module> inCategory(Category category) {
        return MODULES.stream().filter(mod -> mod.getCategory().equals(category)).toList();
    }

    @Override
    public void onKey(long window, int key, int scancode, int action, int modifier) {
        if (action != GLFW.GLFW_PRESS || mc.currentScreen != null || key == -1) return;

        if (bindingModule != null) {
            bindingModule.getBind().set(key);
            bindingModule = null;
            StorageHandler.saveConfig();
            ChatUtil.info("Set bind to " + key + " (key " + KeyUtil.getKeyName(key) + ")");
            return;
        }

        for (Module m : MODULES) {
            if (m.getBind().get().equals(key)) {
                m.toggle();
            }
        }
    }
}
