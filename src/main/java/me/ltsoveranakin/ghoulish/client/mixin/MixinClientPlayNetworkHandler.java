package me.ltsoveranakin.ghoulish.client.mixin;

import me.ltsoveranakin.ghoulish.client.event.sub.Subscriptions;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.IDispatchable;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.packet.ISubCPacket;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {

    @Inject(method = "sendPacket(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void sendPacket(Packet<?> packet, CallbackInfo ci) {

        for(ISubCPacket scp : Subscriptions.C_PACKETS) {
            if(scp instanceof IDispatchable d && !d.shouldDispatch()) continue;
            scp.onCPacket(packet, ci);
        }
    }
}
