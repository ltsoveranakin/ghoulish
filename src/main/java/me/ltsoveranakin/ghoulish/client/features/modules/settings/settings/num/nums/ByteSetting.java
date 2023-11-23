package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums;

import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import me.ltsoveranakin.ghoulish.client.util.parser.ParserUtil;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class ByteSetting extends NumSetting<Byte> {
    public ByteSetting(String name, String desc, Byte defVal, Byte minVal, Byte maxVal, Module mod) {
        super(name, desc, defVal, minVal, maxVal, EnumSettingType.BYTE, mod);
    }

    @Override
    public @NotNull Byte parseNum(String str) throws ParseException {
        return ParserUtil.BYTE_PARSER.parse(str);
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
