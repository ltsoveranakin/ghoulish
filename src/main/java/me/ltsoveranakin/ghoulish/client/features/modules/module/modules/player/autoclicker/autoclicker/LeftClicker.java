package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.player.autoclicker.autoclicker;

import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.player.autoclicker.AbstractAutoClicker;
import net.minecraft.client.option.KeyBinding;

public class LeftClicker extends AbstractAutoClicker {
    public LeftClicker() {
        super("left");
    }

    @Override
    protected KeyBinding getKeyBinding() {
        return mc.options.attackKey;
    }
}
