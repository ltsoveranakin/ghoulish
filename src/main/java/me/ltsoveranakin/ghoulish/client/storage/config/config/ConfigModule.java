package me.ltsoveranakin.ghoulish.client.storage.config.config;

import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.other.label.LabelSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Bytes: [...moduleName(str), ...settingCount(byte), ...settings(ConfigSetting[])]
 */

public class ConfigModule implements StorageCategory {
    private final Module module;
    private final Map<Setting<?>, ConfigSetting> configSettings = new HashMap<>();

    public ConfigModule(DataInputStream dis) throws Exception {
        String modName = dis.readUTF();
        int settingCount = dis.readByte();

        module = ModuleManager.getModule(modName);

        if (module == null) {
            throw new Exception("Unable to find module " + modName);
        }

        for (int i = 0; i < settingCount; i++) {
            ConfigSetting configSetting = new ConfigSetting(dis, module);
            if(configSetting.didUseDummy()) continue; // used a dummy setting, don't add it because will always write setting out and cause crash
            configSettings.put(configSetting.getSetting(), configSetting);
        }
    }

    public ConfigModule(Module module) {
        this.module = module;

        for (Setting<?> setting : module.getSettings()) {
            if (setting instanceof LabelSetting) continue;
            configSettings.put(setting, new ConfigSetting(setting));
        }
    }

    @Override
    public void write(DataOutputStream dos) throws Exception {
        dos.writeUTF(module.getName());
        dos.writeByte(configSettings.size());

        for (ConfigSetting cfgSetteng : configSettings.values()) {
            cfgSetteng.write(dos);
        }
    }

    public Module getModule() {
        return module;
    }

    @Override
    public String toString() {
        return "ConfigModule{" +
                "module=" + module +
                ", configSettings=" + configSettings +
                '}';
    }
}
