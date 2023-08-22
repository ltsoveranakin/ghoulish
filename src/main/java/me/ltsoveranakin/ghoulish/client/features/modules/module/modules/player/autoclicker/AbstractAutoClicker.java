package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.player.autoclicker;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.range.AbstractRangeSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.range.range.RangeIntSetting;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubWorldRenderEnd;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.BoolSetting;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.option.KeyBinding;

public abstract class AbstractAutoClicker extends Module implements ISubWorldRenderEnd {
    private final RangeIntSetting cps = addMinMaxInt("cps", "the amount of times to click in a second", 1, 1, 20);

    private final BoolSetting jitter = addBool("jitter", "enables jittering", false);
    private final AbstractRangeSetting<Float> jitterAmt = addMinMaxFloat("jitteramount", "automatically randomly rotates your camera small amounts", 0, -1, 1);


    private long nextClick = 0;

    public AbstractAutoClicker(String c) {
        super(c + "clicker", "automatically " + c + " clicks", Category.PLAYER);
    }

    @Override
    public void onWorldRenderEnd(WorldRenderContext ctx) {
        if(!getKeyBinding().isPressed()) return;
        long curTimeMillis = System.currentTimeMillis();

        if(curTimeMillis < nextClick) {
            return;
        }

        int timeMillis = 1000 / cps.getRandom();
        nextClick = curTimeMillis + timeMillis;

        KeyBinding.onKeyPressed(getKeyBinding().getDefaultKey());

        if(jitter.get()) {
            mc.player.setYaw((jitterAmt.getRandom()) + mc.player.getYaw());
            mc.player.setPitch((jitterAmt.getRandom()) + mc.player.getPitch());
        }
    }

    protected abstract KeyBinding getKeyBinding();
}
