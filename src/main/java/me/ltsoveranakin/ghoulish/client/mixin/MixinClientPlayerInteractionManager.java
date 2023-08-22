package me.ltsoveranakin.ghoulish.client.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {

//    @Inject(method = "getReachDistance", at = @At("HEAD"), cancellable = true)
//    public void getReachDistance(CallbackInfoReturnable<Float> cir) {
//        Reach reach = ModuleManager.getModule(Reach.class);
//        if(reach.isEnabled()) {
//            cir.setReturnValue(reach.BLOCK_REACH.get() * 2);
//        }
//    }


//    @Inject(method = "hasExtendedReach", at = @At("HEAD"), cancellable = true)
//    public void hasExtReach(CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(ModuleManager.getModule(Reach.class).isEnabled());
//    }
}
