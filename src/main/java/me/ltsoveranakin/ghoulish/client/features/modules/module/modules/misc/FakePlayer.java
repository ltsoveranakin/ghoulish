package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.misc;

import com.mojang.authlib.GameProfile;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FakePlayer extends Module {
    private @Nullable OtherClientPlayerEntity oPlayer;

    public FakePlayer() {
        super("fakeplayer", "spawns a fake player", Category.MISC);
    }

    @Override
    public void onDisable() {
        if (oPlayer == null) return;
        mc.world.removeEntity(oPlayer.getId(), Entity.RemovalReason.DISCARDED);
        oPlayer = null;
    }

    @Override
    public void onEnable() {
        if (mc.world == null) disable();
        oPlayer = new OtherClientPlayerEntity(mc.world, new GameProfile(UUID.randomUUID(), "Fake.Player"));
        oPlayer.copyFrom(mc.player);
        oPlayer.setInvulnerable(true);
        mc.world.addEntity(oPlayer);
    }
}
