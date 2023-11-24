package me.ltsoveranakin.ghoulish.client.features.gui.screens;

import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.hud.AbstractHudModule;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.hud.hud.HudEditorModule;
import me.ltsoveranakin.ghoulish.client.misc.MinecraftInstance;
import me.ltsoveranakin.ghoulish.client.util.RenderUtil2d;
import me.ltsoveranakin.ghoulish.client.util.WindowUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class HudEditorScreen extends Screen implements MinecraftInstance {
    private final HudEditorModule hudEditor = ModuleManager.getModule(HudEditorModule.class);
    private @Nullable AbstractHudModule draggingModule = null;
    private int dragX;
    private int dragY;

    public HudEditorScreen() {
        super(Text.of("hud_editor"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderUtil2d.drawBox(context, 0, 0, WindowUtil.getWidth(), WindowUtil.getHeight(), hudEditor.HUD_BG.getColor());
        for (Module mod : ModuleManager.MODULES) {
            if (mod.isEnabled() && mod instanceof AbstractHudModule hudModule) {
                hudModule.onRender(context, delta, true);
            }
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (draggingModule != null) return false;
        if (button == 0) {
            for (Module mod : ModuleManager.MODULES) {
                if (mod.isEnabled() && mod instanceof AbstractHudModule hudModule) {
                    if (hudModule.doesMouseCollide(mouseX, mouseY)) {
                        draggingModule = hudModule;
                        dragX = (int) (mouseX - hudModule.getPos().getX());
                        dragY = (int) (mouseY - hudModule.getPos().getY());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (draggingModule == null || button != 0) return false;

        draggingModule = null;
        return false;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if (draggingModule == null) return;

        draggingModule.setPos((int) mouseX - dragX, (int) mouseY - dragY);

        if (hudEditor.SNAPPING.get()) {
            draggingModule.snap();
        }
    }
}
