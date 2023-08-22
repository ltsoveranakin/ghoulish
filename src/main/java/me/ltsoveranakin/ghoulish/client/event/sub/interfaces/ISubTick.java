package me.ltsoveranakin.ghoulish.client.event.sub.interfaces;

import net.minecraft.client.world.ClientWorld;

public interface ISubTick extends ISubscription {
    void onTick(ClientWorld world);
}
