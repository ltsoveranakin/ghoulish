package me.ltsoveranakin.ghoulish.client.storage.config.config;

import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.interfaces.state.ManagedSerializableData;

import java.io.*;

/**
 * Bytes: [...settingName(str), ...settingData(?)]
 */

public class ConfigSetting implements ManagedSerializableData {
    private Setting<?> setting;
    private final Module module;

    private boolean usedDummy = false;

    public ConfigSetting(Module module) {
        this.module = module;
    }

    public Setting<?> getSetting() {
        return setting;
    }

    public void setSetting(Setting<?> setting) {
        this.setting = setting;
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

    @Override
    public void writeData(DataOutputStream dos) throws IOException {
        if (setting.getSettingType() == EnumSettingType.LABEL) {
            return;
        }

        if (setting.getName() == null) {
            System.out.println("SETTING NAME NULL; MODULE: " + setting.getMod() + " SETTING: " + setting.getSettingType());
        }

        dos.writeUTF(setting.getName());
        setting.toBytes(dos);
    }

    @Override
    public void readData(DataInputStream dis) throws IOException {
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
            throw new IOException();
        }

        setting.fromBytes(dis);
    }

    /**
     * No children to initialize, does nothing
     */
    @Override
    public void setDefaultDataImpl() {
    }

    @Override
    public void init() {

    }
}
