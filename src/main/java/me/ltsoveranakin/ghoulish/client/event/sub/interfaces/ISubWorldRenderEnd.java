package me.ltsoveranakin.ghoulish.client.event.sub.interfaces;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;

public interface ISubWorldRenderEnd extends ISubscription {
    void onWorldRenderEnd(WorldRenderContext ctx);
}
