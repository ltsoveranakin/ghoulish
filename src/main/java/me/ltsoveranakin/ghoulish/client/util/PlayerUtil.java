package me.ltsoveranakin.ghoulish.client.util;

import me.ltsoveranakin.ghoulish.client.misc.MinecraftInstance;
import net.minecraft.entity.attribute.EntityAttributes;

public class PlayerUtil implements MinecraftInstance {
    public static double getBlockReach() {
        return mc.player.getAttributeValue(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE);
    }

}
