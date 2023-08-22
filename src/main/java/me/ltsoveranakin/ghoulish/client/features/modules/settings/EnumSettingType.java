package me.ltsoveranakin.ghoulish.client.features.modules.settings;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.other.label.LabelSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.*;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.*;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.DoubleSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.FloatSetting;

import java.awt.Color;

public enum EnumSettingType {
    BIND,
    BOOLEAN,
    BYTE,
    SHORT,
    INT,
    LONG,
    FLOAT,
    DOUBLE,
    COLOR,
    STRING,
    ENUM,
    LABEL;


    public Setting<?> getDummy() {
        Setting<?> setting = null;

        switch (this) {
            case BIND -> setting = new BindSetting(null, null, 0, null);
            case INT -> setting = new IntSetting(null, null, 0, 0, 0, null);
            case ENUM -> setting = new EnumSetting<>(null, null, null, null);
            case BYTE -> setting = new ByteSetting(null, null, (byte) 0, (byte) 0, (byte) 0, null);
            case LONG -> setting = new LongSetting(null, null, 0L, 0L, 0L, null);
            case COLOR -> setting = new ColorSetting(null, null, Color.BLACK, null);
            case FLOAT -> setting = new FloatSetting(null, null, 0F, 0F, 0F, null);
            case LABEL -> setting = new LabelSetting(null, null);
            case SHORT -> setting = new ShortSetting(null, null, (short) 0, (short) 0, (short) 0, null);
            case DOUBLE -> setting = new DoubleSetting(null, null, 0D, 0D, 0D, null);
            case STRING -> setting = new StringSetting(null, null, null, null);
            case BOOLEAN -> setting = new BoolSetting(null, null, false, null);
        }

        return setting;
    }
}
