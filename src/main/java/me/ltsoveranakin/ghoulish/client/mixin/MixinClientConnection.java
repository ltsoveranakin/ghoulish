package me.ltsoveranakin.ghoulish.client.mixin;

import me.ltsoveranakin.ghoulish.client.event.sub.Subscriptions;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.IDispatchable;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.packet.ISubSPacket;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class MixinClientConnection {

    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static <T extends PacketListener> void handlePacket(Packet<T> packet, PacketListener listener, CallbackInfo ci) {
        if(packet instanceof BundleS2CPacket) {
            for(ISubSPacket subSPacket : Subscriptions.S_PACKETS) {
                if(subSPacket instanceof IDispatchable d && !d.shouldDispatch()) continue;
                subSPacket.onSPacket(packet, ci);
            }
        }
    }
}
