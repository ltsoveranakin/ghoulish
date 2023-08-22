package me.ltsoveranakin.ghoulish.client.mixin;

import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

//    @ModifyArg(method = "updateTargetedEntity", at = @At("INVOKE"), 0)
//    public boolean updateTE(boolean value) {
//
//        return true;
//    }
}
