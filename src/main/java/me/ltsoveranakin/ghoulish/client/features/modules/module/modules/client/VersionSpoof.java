package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.client;

import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;

public class VersionSpoof extends Module {
//    private final StringSetting version = addStr("version", "custom version", "vanila")
    public VersionSpoof() {
        super("versionspoof", "spoofs your version sent to the server", Category.CLIENT);
    }
}
