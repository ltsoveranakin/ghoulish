package me.ltsoveranakin.ghoulish.client.event;

import me.ltsoveranakin.ghoulish.client.event.events.HudRenderEvent;
import me.ltsoveranakin.ghoulish.client.event.events.TickEvent;
import me.ltsoveranakin.ghoulish.client.event.events.WorldRenderEndEvent;

public class EventInitializer {
    public static void init() {
        TickEvent.init();
        HudRenderEvent.init();
        WorldRenderEndEvent.init();
    }
}
