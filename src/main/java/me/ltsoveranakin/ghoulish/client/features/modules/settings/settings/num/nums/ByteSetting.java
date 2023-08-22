package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums;

import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ByteSetting extends NumSetting<Byte> {
    public ByteSetting(String name, String desc, Byte defVal, Byte minVal, Byte maxVal, Module mod) {
        super(name, desc, defVal, minVal, maxVal, EnumSettingType.BYTE, mod);
    }

    @Override
    public @NotNull Byte parseNum(String str) throws Exception {
        return Byte.parseByte(str);
    }

    @Override
    public boolean setLong(long lon) {
        return set((byte) lon);
    }


    @Override
    public void toBytesImpl(DataOutputStream dos) throws IOException {
        dos.writeByte(get());
    }

    @Override
    public Byte fromBytesImpl(DataInputStream dis) throws IOException {
        return dis.readByte();
    }
}
