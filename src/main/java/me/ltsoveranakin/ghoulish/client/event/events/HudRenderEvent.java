package me.ltsoveranakin.ghoulish.client.event.events;

import me.ltsoveranakin.ghoulish.client.event.sub.Subscriptions;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.IDispatchable;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubHudRender;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class HudRenderEvent {
    public static void init() {
        HudRenderCallback.EVENT.register(HudRenderEvent::dispatch);
    }

    public static void dispatch(DrawContext ctx, RenderTickCounter tickDelta) {
        for(ISubHudRender ist : Subscriptions.HUD_RENDER) {
            if(ist instanceof IDispatchable d && !d.shouldDispatch()) continue;
            ist.onRender(ctx, tickDelta, false);
        }
    }
}
