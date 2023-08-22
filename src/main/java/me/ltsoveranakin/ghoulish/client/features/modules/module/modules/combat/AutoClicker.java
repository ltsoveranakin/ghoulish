package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.combat;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.range.AbstractRangeSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.BoolSetting;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubTick;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.world.ClientWorld;

public class AutoClicker extends Module implements ISubTick {
    private final BoolSetting leftClick = addBool("left", "if left clicking is enabled", true);
    private final AbstractRangeSetting<Integer> leftmm = addMinMaxInt("clickdelayleft", "delay (in ticks) between clicks", 2, 0, 40).requires(leftClick, true);

    private final BoolSetting rightClick = addBool("right", "if right clicking is enabled", true);
    private final AbstractRangeSetting<Integer> rightmm = addMinMaxInt("clickdelayright", "delay (in ticks) between clicks", 2, 0, 40).requires(rightClick, true);

    private final BoolSetting jitter = addBool("jitter", "shakey", false);
    private final AbstractRangeSetting<Float> jitterAmt = addMinMaxFloat("jitteramount", "shakey amount", .5f, -5, 5);

    private int lTicks = 0;
    private int lNextTickClick = 0;

    private int rTicks = 0;
    private int rNextTickClick = 0;

    public AutoClicker() {
        super("autoclicker", "automatically clicks", Category.COMBAT);
    }


    @Override
    public void onTick(ClientWorld world) {
        lTicks++;
        rTicks++;
        boolean clicked = false;

        if(leftClick.get() && mc.options.attackKey.isPressed() && lTicks > lNextTickClick) {
            lTicks = 0;
            lNextTickClick = leftmm.getRandom();
            KeyBinding.onKeyPressed(mc.options.attackKey.getDefaultKey());
            clicked = true;
        }


        if(rightClick.get() && mc.options.useKey.isPressed() && rTicks > rNextTickClick) {

            rTicks = 0;
            rNextTickClick = rightmm.getRandom();
            KeyBinding.onKeyPressed(mc.options.useKey.getDefaultKey());
            clicked = true;
        }

        if(clicked && jitter.get()) {
            mc.player.setYaw(jitterAmt.getRandom() + mc.player.getYaw());
            mc.player.setPitch(jitterAmt.getRandom() + mc.player.getPitch());
        }
    }
}
