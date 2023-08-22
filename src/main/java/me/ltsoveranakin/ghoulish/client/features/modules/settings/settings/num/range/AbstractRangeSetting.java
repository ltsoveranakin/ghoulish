package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.range;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public abstract class AbstractRangeSetting<T extends Number & Comparable<T>> {
    private final static double INC_AMT = Math.toRadians(15);

    private double sinInc = 0;
    private double sinVal = 0;

    private final NumSetting<T> minSet;
    private final NumSetting<T> maxSet;

    protected final Random random = new Random();

    public AbstractRangeSetting(NumSetting<T> minSet, NumSetting<T> maxSet) {
        this.minSet = minSet;
        this.maxSet = maxSet;
    }


    public T getMax() {
        return maxSet.get();
    }

    public T getMin() {
        return minSet.get();
    }

    public abstract @NotNull T getRandom();

    public NumSetting<T> getMinSet() {
        return minSet;
    }

    public NumSetting<T> getMaxSet() {
        return maxSet;
    }

    public <K> AbstractRangeSetting<T> requires(Setting<K> setting, K toBe) {
        minSet.requiresSetting(setting, toBe);
        maxSet.requiresSetting(setting, toBe);
        return this;
    }

    public final T getRealisticRandom() {
        // 3.14/2 = 1.57 -- sin = y
        if(getMin().equals(getMax())) {
            return getMax();
        }

        sinInc += random.nextDouble(INC_AMT);
        sinInc %= Math.PI * 2; // wrap to coterminal angle

        sinVal = Math.sin(sinInc);
        return realisticRandomImpl();
    }

    /**
     *
     * @return smooth-ish sin value, always positive
     */

    protected double getSinVal() {
        return Math.abs(sinVal);
    }

    protected abstract T realisticRandomImpl();
}
