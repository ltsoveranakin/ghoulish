package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.range.range;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.range.AbstractRangeSetting;
import org.jetbrains.annotations.NotNull;

public class RangeIntSetting extends AbstractRangeSetting<Integer> {
    public RangeIntSetting(NumSetting<Integer> minSet, NumSetting<Integer> maxSet) {
        super(minSet, maxSet);
    }

    @Override
    public @NotNull Integer getRandom() {
        return getMin() >= getMax() ? getMin() : random.nextInt(getMin(), getMax());
    }

    @Override
    protected Integer realisticRandomImpl() {
        int diff = getMax() - getMin();
        double perc = getSinVal() / (Math.PI * 2);

        return (int) (getMin() + (diff * perc));
    }
}
