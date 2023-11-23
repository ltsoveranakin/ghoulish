package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.util.parser.ParserUtil;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BoolSetting extends Setting<Boolean> {
    public BoolSetting(String name, String desc, Boolean defVal, Module mod) {
        super(name, desc, defVal, mod, EnumSettingType.BOOLEAN);
    }

    @Override
    public Boolean fromBytesImpl(DataInputStream dis) throws IOException {
        return dis.readBoolean();
    }

    @Override
    public void toBytesImpl(DataOutputStream dos) throws IOException {
        dos.writeBoolean(get());
    }

    @Override
    public boolean set(@NotNull Boolean val) {
        return super.set(val);
    }

    @Override
    public @NotNull Boolean parse(String str) throws ParseException {
        return ParserUtil.BOOLEAN_PARSER.parse(str);
    }

    @Override
    public BoolSetting onSet(OnSet r) {
        return (BoolSetting) super.onSet(r);
    }

    @Override
    public <K> BoolSetting requiresSetting(Setting<K> setting, K toBe) {
        return (BoolSetting) super.requiresSetting(setting, toBe);
    }
}
