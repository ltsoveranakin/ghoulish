package me.ltsoveranakin.ghoulish.client.features.modules;

import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.util.RenderUtil2d;

import java.util.ArrayList;
import java.util.List;

public enum Category {
    PLAYER,
    COMBAT,
    CLIENT,
    MISC,
    World,
    HUD;

    // ArrayList for TabGui
    private final List<Module> modules = new ArrayList<>(); // updated in ModuleManager.java

    public static int getLongest() {
        int len = 0;
        for (Category category : values()) {
            int testLen = RenderUtil2d.measureText(category.name());
            if (testLen > len) {
                len = testLen;
            }
        }
        return len;
    }

    public boolean isUsed() {
        return !modules.isEmpty();
    }

    public List<Module> getModules() {
        return modules;
    }
}
