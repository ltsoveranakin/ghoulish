package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.player;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.IntSetting;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubTick;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.util.BlockUtil;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;

public class Eagle extends Module implements ISubTick {
    private boolean onG = false;
    private final IntSetting sneakTicks = addInt("sneakticks", "amount of ticks to be sneaking", 0, 0, 10);

    public Eagle() {
        super("eagle", "sneaks at the edge of blocks", Category.PLAYER);
    }

    int tick = 0;

    @Override
    public void onTick(ClientWorld world) {

        if (!mc.player.isOnGround()) {
            if (!onG) {
                mc.options.sneakKey.setPressed(false);
            }
            onG = true;
            return;
        } else {
            onG = false;
        }

        boolean blockBelow = BlockUtil.getBlock(mc.player.getBlockPos().down()).equals(Blocks.AIR);

        if(!blockBelow) {
            if(tick >= sneakTicks.get()) {
                mc.options.sneakKey.setPressed(false);
                tick = 0;
            }
            tick++;
        } else {
            mc.options.sneakKey.setPressed(true);
            tick = 0;
        }


    }

    private void simpleSneak() {

    }

    private void customSneak() {

    }

    @Override
    public void onDisable() {
        tick = 0;
        mc.options.sneakKey.setPressed(false);
    }
}
