package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import org.jetbrains.annotations.NotNull;

public abstract class NumSetting<T extends Number & Comparable<T>> extends Setting<T> {
    private final T min;
    private final T max;

    public NumSetting(String name, String desc, T defVal, T minVal, T maxVal, EnumSettingType settingType, Module mod) {
        super(name, desc, defVal, mod, settingType);

        if(defVal.compareTo(minVal) < 0 || defVal.compareTo(maxVal) > 0) {
            throw new RuntimeException("Default value must be between min and max! GOT: " + defVal + " as default, " + minVal + " as min, and " + maxVal + " as max");
        }

        if (maxVal.compareTo(minVal) < 0) {
            throw new RuntimeException("Maximum value cannot be below minimum! GOT: " + minVal + " as min, and " + maxVal + " as max");
        }
        min = minVal;
        max = maxVal;
    }

    @Override
    public boolean set(@NotNull T val) {
        if (isInBounds(val)) {
            return super.set(val);
        } else {
            if (val.compareTo(getMin()) < 0) {
                return super.set(getMin());
            } else if (val.compareTo(getMax()) > 0) {
                return super.set(getMax());
            }
        }
        return false;
    }

    public boolean isInBounds(T num) {
        return num.compareTo(min) >= 0 && num.compareTo(max) <= 0;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public T parse(String str) throws Exception {
        T parsed = parseNum(str);
        if (!isInBounds(parsed)) {
            throw new Exception("OutOfRange");
        }
        return parsed;
    }

    public abstract @NotNull T parseNum(String str) throws Exception;

    public abstract boolean setLong(long lon);

    private boolean isGreaterThan(T num) {
        return get().compareTo(num) > 0;
    }

    private boolean isLessThan(T num) {
        return get().compareTo(num) < 0;
    }
}
