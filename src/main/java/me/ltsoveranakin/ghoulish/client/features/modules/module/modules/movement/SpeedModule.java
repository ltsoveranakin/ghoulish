package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.movement;

import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubKey;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class SpeedModule extends Module implements ISubKey {
    public SpeedModule() {
        super("speed", "gotta go fast (doesnt work)", Category.Movement);
    }


    @Override
    public void onKey(long window, int key, int scancode, int action, int modifier) {
        boolean moving = false;
        float relMoveAngle = 0;

        if (key == GLFW.GLFW_KEY_W) {
            moving = true;
        }

        if (key == GLFW.GLFW_KEY_S) {
            moving = true;
            relMoveAngle = 180;
        }

        if (key == GLFW.GLFW_KEY_A) {
            moving = true;
            relMoveAngle -= 90;
        }

        if (key == GLFW.GLFW_KEY_D) {
            moving = true;
            relMoveAngle += 90;
        }

        if (!moving) {
            return;
        }

        Vec3d moveDir = mc.player.getRotationVector(0, mc.player.renderYaw + relMoveAngle);

//        mc.player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, moveDir);
    }
}
