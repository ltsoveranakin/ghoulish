package me.ltsoveranakin.ghoulish.client.event.sub.interfaces;

import net.minecraft.client.gui.DrawContext;

public interface ISubHudRender extends ISubscription {
    void onRender(DrawContext ctx, float tickDelta, boolean isGui);
}
