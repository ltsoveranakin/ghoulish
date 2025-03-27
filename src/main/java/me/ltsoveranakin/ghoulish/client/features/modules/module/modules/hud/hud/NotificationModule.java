package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.hud.hud;

import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.hud.AbstractHudModule;
import me.ltsoveranakin.ghoulish.client.misc.Box2D;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class NotificationModule extends AbstractHudModule {
    private Box2D notifPos;

    public NotificationModule() {
        super("notifications", "shows notifications when modules are toggled", 1, .7f);
    }

    @Override
    public boolean doesMouseCollide(double mx, double my) {
        return false;
    }

    @Override
    public void onRenderImpl(DrawContext ctx, RenderTickCounter tickDelta, boolean isGui) {
    }
}
