package me.ltsoveranakin.ghoulish.client.event.events;

import me.ltsoveranakin.ghoulish.client.event.sub.Subscriptions;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.IDispatchable;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubTick;
import me.ltsoveranakin.ghoulish.client.misc.MinecraftInstance;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.world.ClientWorld;


public class TickEvent implements MinecraftInstance {
    public static void init() {
        ClientTickEvents.START_WORLD_TICK.register(TickEvent::dispatch);
    }

    private static void dispatch(ClientWorld clientWorld) {
        for (ISubTick ist : Subscriptions.TICKS) {
            if (ist instanceof IDispatchable d && !d.shouldDispatch()) continue;
            ist.onTick(clientWorld);
        }
    }
}
