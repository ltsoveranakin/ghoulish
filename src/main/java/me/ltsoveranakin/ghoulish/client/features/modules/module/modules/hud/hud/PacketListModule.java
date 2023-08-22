package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.hud.hud;

import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.packet.ISubCPacket;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.packet.ISubSPacket;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.hud.AbstractHudModule;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.BoolSetting;
import me.ltsoveranakin.ghoulish.client.util.RenderUtil2d;
import me.ltsoveranakin.ghoulish.client.util.WindowUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.Color;
import java.util.*;

public class PacketListModule extends AbstractHudModule implements ISubCPacket, ISubSPacket {
    private final BoolSetting sentPackets = addBool("sent", "shows sent packets", true);
    private final BoolSetting receivedPackets = addBool("received", "shows received packets", true);

    private final List<Packet<?>> packets = new LinkedList<>();
    private int packetCount = 0;

    public PacketListModule() {
        super("packetlist", "shows a list of packets sent", 2f / 3f, 0);
    }

    @Override
    public void onRenderImpl(DrawContext ctx, float tickDelta, boolean isGui) {
        int width = WindowUtil.getWidth() / 3;
        int height = WindowUtil.getHeight() / 2;
        packetCount = height / RenderUtil2d.FONT_HEIGHT;

        RenderUtil2d.drawBox(ctx, getPos(), width, height, Color.BLACK);

        int i = 0;
        for(Packet<?> packet : packets) {
            RenderUtil2d.drawText(ctx, packet.getClass().getSimpleName(), getPos().getX() + 2, getPos().getY() + 2 + (RenderUtil2d.FONT_HEIGHT * i), Color.WHITE);
            i++;
        }
    }

    @Override
    public void onCPacket(Packet<?> packet, CallbackInfo ci) {
        if(sentPackets.get()) addPacket(packet);
    }

    @Override
    public void onSPacket(Packet<?> packet, CallbackInfo ci) {
        if(receivedPackets.get()) addPacket(packet);
    }

    private void addPacket(Packet<?> packet) {
        packets.add(packet);
        while (packets.size() > packetCount) packets.remove(0);
    }
}
