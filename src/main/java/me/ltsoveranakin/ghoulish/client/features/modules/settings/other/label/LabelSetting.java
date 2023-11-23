package me.ltsoveranakin.ghoulish.client.features.modules.settings.other.label;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 * A setting not intended to be used as a setting in a module, but rather to display a simple label. This setting should not be registered to a module.
 */
public class LabelSetting extends Setting<Object> {
    public LabelSetting(String name, String desc) {
        super(String.valueOf(name).toUpperCase(), desc, null, null, EnumSettingType.LABEL);
    }

    @Override
    protected Object fromBytesImpl(DataInputStream dis) throws IOException {
        return null;
    }

    @Override
    protected void toBytesImpl(DataOutputStream dos) throws IOException {

    }

    @NotNull
    @Override
    public Object parse(String str) throws ParseException {
        return null;
    }
}