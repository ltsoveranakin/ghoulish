package me.ltsoveranakin.ghoulish.client.event.sub.interfaces.packet;

import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubscription;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Interface for subscribing to outgoing packets, uses a mixin to cancel the packet
 */
public interface ISubCPacket extends ISubscription {
   void onCPacket(Packet<?> packet, CallbackInfo ci);
}
