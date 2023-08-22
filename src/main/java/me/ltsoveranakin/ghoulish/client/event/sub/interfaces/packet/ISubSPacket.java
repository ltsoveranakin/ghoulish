package me.ltsoveranakin.ghoulish.client.event.sub.interfaces.packet;

import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubscription;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface ISubSPacket extends ISubscription {
    void onSPacket(Packet<?> packet, CallbackInfo ci);
}
