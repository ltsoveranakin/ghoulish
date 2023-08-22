package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint;

import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.NumSetting;

public abstract class FloatingPointSetting<T extends Number & Comparable<T>> extends NumSetting<T> {
    public FloatingPointSetting(String name, String desc, T defVal, T minVal, T maxVal, EnumSettingType settingType, Module mod) {
        super(name, desc, defVal, minVal, maxVal, settingType, mod);
    }

    public abstract boolean setDouble(double dub);
}
