package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.combat;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.range.range.RangeIntSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.BoolSetting;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubTick;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.Hand;

public class TriggerBot extends Module implements ISubTick {
    private final RangeIntSetting hitDelay = addMinMaxInt("hitdelay", "random delay for how long an ememy should be on your cursor before attacking", 2, 0, 10);
    private final BoolSetting playersOnly = addBool("playersonly", "only attacks players", true);
    private final BoolSetting stopSprint = addBool("stopsprint", "sends stop sprint packet, and cancels your sprint", true);
    
    private int tick;
    private int nextTick = -1;
    public TriggerBot() {
        super("triggerbot", "auto attacks enemies on your cursor", Category.COMBAT);
    }

    @Override
    public void onEnable() {
        nextTick = hitDelay.getRandom();
        tick = 0;
    }

    @Override
    public void onTick(ClientWorld world) {
        if(mc.interactionManager != null && (playersOnly.get() ? mc.targetedEntity instanceof PlayerEntity : mc.targetedEntity != null) && mc.player != null && mc.player.getAttackCooldownProgress(.5f) == 1F && mc.targetedEntity.isAlive() && !mc.player.isUsingItem()) {
            tick++;

            if(tick > nextTick) {
                tick = 0;
                nextTick = hitDelay.getRandom();

                if(stopSprint.get() && mc.player.isSprinting()) {
                    mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
                    mc.player.setSprinting(false);
                }

                mc.interactionManager.attackEntity(mc.player, mc.targetedEntity);
                mc.player.swingHand(Hand.MAIN_HAND);
            }
        }
    }
}
