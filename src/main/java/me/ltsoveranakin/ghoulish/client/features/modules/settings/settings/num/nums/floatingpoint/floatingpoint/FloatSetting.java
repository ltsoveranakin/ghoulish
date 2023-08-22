package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.FloatingPointSetting;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FloatSetting extends FloatingPointSetting<Float> {
    public FloatSetting(String name, String desc, Float defVal, Float minVal, Float maxVal, Module mod) {
        super(name, desc, defVal, minVal, maxVal, EnumSettingType.FLOAT, mod);
    }

    @Override
    public boolean setDouble(double dub) {
        return set((float) dub);
    }

    @Override
    public @NotNull Float parseNum(String str) {
        return Float.parseFloat(str);
    }

    @Override
    public boolean setLong(long lon) {
        return set((float) lon);
    }

    @Override
    public void toBytesImpl(DataOutputStream dos) throws IOException {
        dos.writeFloat(get());
    }

    @Override
    public Float fromBytesImpl(DataInputStream dis) throws IOException {
        return dis.readFloat();
    }

    @Override
    public <K> FloatSetting requiresSetting(Setting<K> setting, K toBe) {
        return (FloatSetting) super.requiresSetting(setting, toBe);
    }
}
