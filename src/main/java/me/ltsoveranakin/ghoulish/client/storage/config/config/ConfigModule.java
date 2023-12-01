package me.ltsoveranakin.ghoulish.client.storage.config.config;

import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.other.label.LabelSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.interfaces.state.ManagedSerializableData;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Bytes: [...moduleName(str), ...settingCount(byte), ...settings(ConfigSetting[])]
 */

public class ConfigModule implements ManagedSerializableData {
    private Module module;
    private final Map<Setting<?>, ConfigSetting> configSettings = new HashMap<>();

    public ConfigModule() {

    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "ConfigModule{" +
                "module=" + module +
                ", configSettings=" + configSettings +
                '}';
    }

    @Override
    public void writeData(DataOutputStream dos) throws IOException {
        dos.writeUTF(module.getName());
        dos.writeByte(configSettings.size());

        for (ConfigSetting cfgSetteng : configSettings.values()) {
            cfgSetteng.writeData(dos);
        }
    }

    @Override
    public void readData(DataInputStream dis) throws IOException {
        String modName = dis.readUTF();
        int settingCount = dis.readByte();

        module = ModuleManager.getModule(modName);

        if (module == null) {
            throw new IOException("Unable to find module " + modName);
        }

        for (int i = 0; i < settingCount; i++) {
            ConfigSetting configSetting = new ConfigSetting(module);
            configSetting.readData(dis);
            if (configSetting.didUseDummy())
                continue; // used a dummy setting, don't add it because will always write setting out and cause crash
            configSettings.put(configSetting.getSetting(), configSetting);
        }
    }

    @Override
    public void setDefaultDataImpl() {
        // no default data
    }

    @Override
    public void init() {
        for (Setting<?> setting : module.getSettings()) {
            if (setting instanceof LabelSetting) continue;
            ConfigSetting configSetting = new ConfigSetting(module);
            configSetting.setSetting(setting);
            configSetting.setDefaultDataImpl();
            configSettings.put(setting, configSetting);
        }
    }
}
