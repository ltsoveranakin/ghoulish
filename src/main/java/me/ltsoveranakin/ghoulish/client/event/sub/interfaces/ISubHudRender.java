package me.ltsoveranakin.ghoulish.client.event.sub.interfaces;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public interface ISubHudRender extends ISubscription {
    void onRender(DrawContext ctx, RenderTickCounter tickDelta, boolean isGui);
}
