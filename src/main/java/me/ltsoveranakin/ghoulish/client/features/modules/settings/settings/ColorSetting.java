package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.util.parser.ParserUtil;
import me.ltsoveranakin.ghoulish.client.util.parser.parser.exception.ParseException;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.io.*;

// TODO: change the way colorsetting works
public class ColorSetting extends Setting<Color> {
    public ColorSetting(String name, String desc, Color defVal, Module mod) {
        super(name, desc, defVal, mod, EnumSettingType.COLOR);
    }

    @Override
    public Color fromBytesImpl(DataInputStream dis) throws IOException {
        return new Color(dis.readInt(), true);
    }

    @Override
    public void toBytesImpl(DataOutputStream dos) throws IOException {
        dos.writeInt(get().hashCode());
    }

    @Override
    public @NotNull Color parse(String str) throws ParseException {
        return ParserUtil.COLOR_PARSER.parse(str);
    }

    @Override
    public String toString() {
        return get().getRed() + "," + get().getGreen() + "," + get().getBlue() + "," + get().getAlpha();
    }
}
