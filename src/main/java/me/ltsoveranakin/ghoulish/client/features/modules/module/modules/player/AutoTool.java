package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.player;

import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubTick;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.EnumSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.IntSetting;
import me.ltsoveranakin.ghoulish.client.util.BlockUtil;
import me.ltsoveranakin.ghoulish.client.util.InvUtil;
import me.ltsoveranakin.ghoulish.client.util.PlayerUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;

public class AutoTool extends Module implements ISubTick {
    private final EnumSetting<AutoToolMode> mode = addEnum("mode", "macro mode: switches to the best tool hotbar and disables the module; active mode: switchest to the best tool when you start to mine a block", AutoToolMode.Active);

    private final IntSetting delay = addInt("delay", "delay from beginning breaking a block, to selecting a tool", 3, 0, 20).requiresSetting(mode, AutoToolMode.Active);

    private int tick = 0;

    public AutoTool() {
        super("autotool", "automatically switches to the best tool in your hotbar", Category.PLAYER);
    }

    @Override
    public void onTick(ClientWorld world) {
        if (!mc.options.attackKey.isPressed()) {
            tick = 0;
            return;
        }


        if (tick >= delay.get()) {
            performSwitch();
            tick = 0;
        }

        tick++;
    }

    private void performSwitch() {
        if (mc.targetedEntity != null) {
            return;// for now
        }

        BlockHitResult result = (BlockHitResult) mc.player.raycast(PlayerUtil.getBlockReach(), 1, false);
        if (BlockUtil.getBlock(result.getBlockPos()) == Blocks.AIR) {
            return;
        }

        BlockState state = BlockUtil.getState(result.getBlockPos());

        int highest = mc.player.getInventory().selectedSlot;
        float highestVal = InvUtil.mainHanndStack().getMiningSpeedMultiplier(state);
        for (int i = 0; i < 9; i++) {
            float val = InvUtil.getItem(i).getMiningSpeed(InvUtil.getStack(i), state);
            if (val > highestVal) {
                highest = i;
                highestVal = val;
            }
        }

        mc.player.getInventory().selectedSlot = highest;
    }

    @Override
    public void onEnable() {
        if (mc.player.isCreative()) {
            error("You should probably be in survival for this...");
        }

        if (mode.get() == AutoToolMode.Macro) {
            performSwitch();
            disable();
        }
    }

    private enum AutoToolMode {
        Active,
        Macro
    }
}
