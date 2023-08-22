package me.ltsoveranakin.ghoulish.client.storage.config.config;

import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.SettingParseException;

import java.io.*;

/**
 * Bytes: [...settingName(str), ...settingData(?)]
 */

public class ConfigSetting implements StorageCategory {
    private Setting<?> setting;

    private boolean usedDummy = false;

    public ConfigSetting(DataInputStream dis, Module module) throws IOException, SettingParseException {
        String settingName = dis.readUTF();
        byte enumSettingOrdinal = dis.readByte();

        EnumSettingType settingType = EnumSettingType.values()[enumSettingOrdinal];

        setting = module.getSetting(settingName);
        if (setting == null) {
            System.out.println("Unable to find setting " + settingName + " in module " + module.getName() + "... using dummy setting");

            setting = settingType.getDummy();
            usedDummy = true;
        }

        if (settingType != setting.getSettingType()) {
            throw new SettingParseException(setting, settingType.toString());
        }

        setting.fromBytes(dis);
    }

    public ConfigSetting(Setting<?> setting) {
        this.setting = setting;
    }

    @Override
    public void write(DataOutputStream dos) throws Exception {
        if (setting.getSettingType() == EnumSettingType.LABEL) {
            return;
        }
        if(setting.getName() == null) {
            System.out.println("SETTING NAME NULL; MODULE: " + setting.getMod() + " SETTING: " + setting.getSettingType());
        }
        dos.writeUTF(setting.getName());
        setting.toBytes(dos);
    }

    public Setting<?> getSetting() {
        return setting;
    }

    public boolean didUseDummy() {
        return usedDummy;
    }

    @Override
    public String toString() {
        return "ConfigSetting{" +
                "setting=" + setting +
                '}';
    }
}
