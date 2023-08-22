package me.ltsoveranakin.ghoulish.client.event.events;

import me.ltsoveranakin.ghoulish.client.event.sub.Subscriptions;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.IDispatchable;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubWorldRenderEnd;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class WorldRenderEndEvent {
    public static void init() {
        WorldRenderEvents.END.register(WorldRenderEndEvent::dispatchEnd);
    }

    private static void dispatchEnd(WorldRenderContext ctx) {
        for(ISubWorldRenderEnd iswre : Subscriptions.WORLD_RENDER_END) {
            if(iswre instanceof IDispatchable d && !d.shouldDispatch()) continue;
            iswre.onWorldRenderEnd(ctx);
        }
    }
}
