package me.ltsoveranakin.ghoulish.client.util;

import me.ltsoveranakin.ghoulish.client.misc.MinecraftInstance;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class CrystalUtil implements MinecraftInstance {
    public static boolean canPlaceCrystal(Block block) {
        return block.equals(Blocks.OBSIDIAN) || block.equals(Blocks.BEDROCK);
    }

    public static boolean canPlaceCrystal(BlockPos pos) {
        return canPlaceCrystal(BlockUtil.getBlock(pos))
                &&
                BlockUtil.getBlock(pos.up()).equals(Blocks.AIR)
                &&
                mc.world.getEntitiesByClass(Entity.class, new Box(pos.add(0, 1, 0)), (e) -> true).size() == 0;
    }
}
