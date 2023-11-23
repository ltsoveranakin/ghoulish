package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums;

import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import me.ltsoveranakin.ghoulish.client.util.parser.ParserUtil;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class LongSetting extends NumSetting<Long> {
    public LongSetting(String name, String desc, Long defVal, Long minVal, Long maxVal, Module mod) {
        super(name, desc, defVal, minVal, maxVal, EnumSettingType.LONG, mod);
    }

    @Override
    public @NotNull Long parseNum(String str) throws ParseException {
        return ParserUtil.LONG_PARSER.parse(str);
    }

    @Override
    public boolean setLong(long lon) {
        return set(lon);
    }

    @Override
    public Long fromBytesImpl(DataInputStream dis) throws IOException {
        return dis.readLong();
    }

    @Override
    public void toBytesImpl(DataOutputStream dos) throws IOException {
        dos.writeLong(get());
    }
}
