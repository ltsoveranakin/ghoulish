package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums;

import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IntSetting extends NumSetting<Integer> {
    public IntSetting(String name, String desc, Integer defVal, Integer minVal, Integer maxVal, Module mod) {
        super(name, desc, defVal, minVal, maxVal, EnumSettingType.INT, mod);
    }

    @Override
    public @NotNull Integer parseNum(String str) throws Exception {
        return Integer.parseInt(str);
    }

    @Override
    public boolean setLong(long lon) {
        return set((int) lon);
    }

    @Override
    public void toBytesImpl(DataOutputStream dos) throws IOException {
        dos.writeInt(get());
    }

    @Override
    public Integer fromBytesImpl(DataInputStream dis) throws IOException {
        return dis.readInt();
    }

    @Override
    public <K> IntSetting requiresSetting(Setting<K> setting, K toBe) {
        return (IntSetting) super.requiresSetting(setting, toBe);
    }
}
