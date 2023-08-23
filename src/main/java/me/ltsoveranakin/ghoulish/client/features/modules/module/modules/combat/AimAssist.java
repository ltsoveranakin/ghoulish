package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.combat;

import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubWorldRenderEnd;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.BoolSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.EnumSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.FloatSetting;
import me.ltsoveranakin.ghoulish.client.misc.Rot;
import me.ltsoveranakin.ghoulish.client.util.EntityUtil;
import me.ltsoveranakin.ghoulish.client.util.MathUtil;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class AimAssist extends Module implements ISubWorldRenderEnd {
    private final FloatSetting distance = addFloat("distance", "distance to begin aiming", 4, 3, 6);
    private final EnumSetting<RotMode> rotMode = addEnum("rotation", "the mode for rotations", RotMode.LERP);

    private final FloatSetting lerpStrength = addFloat("strength", "strength of rotation", .3f, .0001f, 1).requiresSetting(rotMode, RotMode.LERP);
    private final FloatSetting normalStrength = addFloat("strength", "strength of rotation", .3f, .0001f, 1).requiresSetting(rotMode, RotMode.NORMAL);

    private final BoolSetting seeOnly = addBool("seeonly", "only aims when you can see the target", true);
    private final FloatSetting distStop = addFloat("diststop", "the rotation distance (in degrees) on when to stop aim assist... (stops when rotation distance is less than this value)", 2, 0, 10);
    private final FloatSetting fov = addFloat("fov", "what fov range the target player should be in to attack (kinda the opposite of diststop)", 360, 20, 360);

    private final BoolSetting yawAssist = addBool("yaw", "assists your yaw aim", true);
    private final BoolSetting pitchAssist = addBool("pitch", "assists your pitch aim", true);


    public AimAssist() {
        super("aimassist", "makes you aim better", Category.COMBAT);
    }

    @Override
    public void onWorldRenderEnd(WorldRenderContext ctx) {
        if (mc.currentScreen != null) return;
//        EntityQuery<PlayerEntity> query = new EntityQuery<>().byClass(PlayerEntity.class);
//        System.out.println(query.getEntities());
        PlayerEntity targetPlayer = EntityUtil.findClosest(PlayerEntity.class, distance.get());

        //                                                      incompatible with blocks such as fences
        if (targetPlayer == null || (seeOnly.get() && !mc.player.canSee(targetPlayer))) {
            return;
        }

        Rot targetRot = MathUtil.getDir(mc.player, targetPlayer.getPos());

        float yawDist = MathHelper.subtractAngles((float) targetRot.yaw(), mc.player.getYaw());
        float pitchDist = MathHelper.subtractAngles((float) targetRot.pitch(), mc.player.getPitch());
        float absYawDist = Math.abs(yawDist);

        if (absYawDist < distStop.get() || Math.abs(pitchDist) < distStop.get() || absYawDist > fov.get()) {
            return;
        }

        float yaw;
        float pitch;


        switch (rotMode.get()) {
            case LERP -> {
                float stren = lerpStrength.get() / 50;
                yaw = MathHelper.lerpAngleDegrees(stren, mc.player.getYaw(), (float) targetRot.yaw());
                pitch = MathHelper.lerpAngleDegrees(stren, mc.player.getPitch(), (float) targetRot.pitch());
            }

            case NORMAL -> {
                float stren = normalStrength.get();

                yaw = mc.player.getYaw();
                if (Math.abs(yawDist) > stren) {
                    yaw = mc.player.getYaw();
                    if (yawDist < 0) {
                        yaw += stren;
                    } else if (yawDist > 0) {
                        yaw -= stren;
                    }
                } else {
                    // find a greater than coterminal angle?
//                    yaw = (float) targetRot.yaw();
                }

                pitch = mc.player.getPitch();
                if (Math.abs(pitchDist) > stren) {
                    pitch = mc.player.getPitch();
                    if (pitchDist < 0) {
                        pitch += stren;
                    } else if (pitchDist > 0) {
                        pitch -= stren;
                    }
                } else {
//                    pitch = (float) targetRot.pitch();
                }
            }

            default -> {
                yaw = mc.player.getYaw();
                pitch = mc.player.getPitch();
            }
        }

        if (yawAssist.get()) mc.player.setYaw(yaw);
        if (pitchAssist.get()) mc.player.setPitch(pitch);
    }

    private enum RotMode {
        LERP,
        NORMAL
    }
}
