package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums;

import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ShortSetting extends NumSetting<Short> {
    public ShortSetting(String name, String desc, Short defVal, Short minVal, Short maxVal, Module mod) {
        super(name, desc, defVal, minVal, maxVal, EnumSettingType.SHORT, mod);
    }

    @Override
    public @NotNull Short parseNum(String str) throws Exception {
        return Short.parseShort(str);
    }

    @Override
    public boolean setLong(long lon) {
        return set((short) lon);
    }

    @Override
    public void toBytesImpl(DataOutputStream dos) throws IOException {
        dos.writeShort(get());
    }

    @Override
    public Short fromBytesImpl(DataInputStream dis) throws IOException {
        return dis.readShort();
    }
}
