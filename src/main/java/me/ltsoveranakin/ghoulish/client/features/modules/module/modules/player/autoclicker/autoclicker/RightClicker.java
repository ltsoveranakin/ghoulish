package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.player.autoclicker.autoclicker;

import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.player.autoclicker.AbstractAutoClicker;
import net.minecraft.client.option.KeyBinding;

public class RightClicker extends AbstractAutoClicker {
    public RightClicker() {
        super("right");
    }

    @Override
    protected KeyBinding getKeyBinding() {
        return mc.options.useKey;
    }
}
