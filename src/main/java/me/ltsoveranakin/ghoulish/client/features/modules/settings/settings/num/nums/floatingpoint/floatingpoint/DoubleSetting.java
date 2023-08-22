package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.FloatingPointSetting;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DoubleSetting extends FloatingPointSetting<Double> {
    public DoubleSetting(String name, String desc, Double defVal, Double minVal, Double maxVal, Module mod) {
        super(name, desc, defVal, minVal, maxVal, EnumSettingType.DOUBLE, mod);
    }

    @Override
    public @NotNull Double parseNum(String str) {
        return Double.parseDouble(str);
    }

    @Override
    public boolean setLong(long lon) {
        return set((double) lon);
    }

    @Override
    public boolean setDouble(double dub) {
        return set(dub);
    }

    @Override
    public void toBytesImpl(DataOutputStream dos) throws IOException {
        dos.writeDouble(get());
    }

    @Override
    public Double fromBytesImpl(DataInputStream dis) throws IOException {
        return dis.readDouble();
    }

    @Override
    public <K> DoubleSetting requiresSetting(Setting<K> setting, K toBe) {
        return (DoubleSetting) super.requiresSetting(setting, toBe);
    }
}
