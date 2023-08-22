package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.range.range;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.range.AbstractRangeSetting;
import org.jetbrains.annotations.NotNull;

public class RangeFloatSetting extends AbstractRangeSetting<Float> {
    public RangeFloatSetting(NumSetting<Float> minSet, NumSetting<Float> maxSet) {
        super(minSet, maxSet);
    }

    @Override
    public @NotNull Float getRandom() {
        return getMin() >= getMax() ? getMin() : random.nextFloat(getMin(), getMax());
    }

    @Override
    protected Float realisticRandomImpl() {
        float diff = getMax() - getMin();
        double perc = getSinVal() / (Math.PI * 2);

        return (float) (getMin() + (diff * perc));
    }
}
