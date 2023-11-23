package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings;

import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import me.ltsoveranakin.ghoulish.client.util.parser.ParserUtil;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StringSetting extends Setting<String> {
    public StringSetting(String name, String desc, String defVal, Module mod) {
        super(name, desc, defVal, mod, EnumSettingType.STRING);
    }

    @Override
    public void toBytesImpl(DataOutputStream dos) throws IOException {
        dos.writeUTF(get());
    }

    @Override
    public String fromBytesImpl(DataInputStream dis) throws IOException {
        return dis.readUTF();
    }

    public boolean set(@NotNull String val) {
        forceSet(val);
        return true;
    }

    @Override
    public @NotNull String parse(String str) throws ParseException {
        return ParserUtil.STRING_PARSER.parse(str);
    }
}
