package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.hud.hud;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.other.color.RGBASettingCollection;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubTick;
import me.ltsoveranakin.ghoulish.client.features.gui.screens.HudEditorScreen;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.BoolSetting;
import net.minecraft.client.world.ClientWorld;

import java.awt.Color;

public class HudEditorModule extends Module implements ISubTick {
    private HudEditorScreen hudScreen = null;

    public final RGBASettingCollection HUD_BG = addCol("hudbg", "background color of the hud editor", new Color(0, 0, 0, 113));
    public final BoolSetting SNAPPING = addBool("snapping", "snaps elements when you move them (if supported)", true);
    public HudEditorModule() {
        super("hudeditor", "allows you to edit hud elements", Category.HUD);
    }

    @Override
    public void onEnable() {
        if(mc.currentScreen != null) {
            mc.currentScreen.close();
        }

        if(hudScreen == null) {
            hudScreen = new HudEditorScreen();
        }

        mc.setScreen(hudScreen);
    }

    @Override
    public void onDisable() {
        if(mc.currentScreen instanceof HudEditorScreen) {
            mc.currentScreen.close();
        }
    }

    @Override
    public void onTick(ClientWorld world) {
        if(!(mc.currentScreen instanceof HudEditorScreen)) {
            disable();
        }
    }
}
