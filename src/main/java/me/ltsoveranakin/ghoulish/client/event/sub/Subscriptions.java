package me.ltsoveranakin.ghoulish.client.event.sub;

import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.*;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.packet.ISubCPacket;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.packet.ISubSPacket;

import java.util.ArrayList;
import java.util.List;

public class Subscriptions {
    public static final List<ISubCPacket> C_PACKETS = new ArrayList<>();
    public static final List<ISubSPacket> S_PACKETS = new ArrayList<>();
    public static final List<ISubTick> TICKS = new ArrayList<>();
    public static final List<ISubKey> KEYS = new ArrayList<>();
    public static final List<ISubHudRender> HUD_RENDER = new ArrayList<>();
    public static final List<ISubWorldRenderEnd> WORLD_RENDER_END = new ArrayList<>();

    public static void addSub(ISubscription s) {
        if (s instanceof ISubCPacket) {
            C_PACKETS.add((ISubCPacket) s);
        }

        if (s instanceof ISubSPacket) {
            S_PACKETS.add((ISubSPacket) s);
        }

        if (s instanceof ISubTick) {
            TICKS.add((ISubTick) s);
        }

        if (s instanceof ISubKey) {
            KEYS.add((ISubKey) s);
        }

        if (s instanceof ISubHudRender) {
            HUD_RENDER.add((ISubHudRender) s);
        }

        if (s instanceof ISubWorldRenderEnd) {
            WORLD_RENDER_END.add((ISubWorldRenderEnd) s);
        }
    }
}
