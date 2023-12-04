package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.client;

import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubKey;
import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubTick;
import me.ltsoveranakin.ghoulish.client.features.gui.clickgui.AbstractClickGui;
import me.ltsoveranakin.ghoulish.client.features.gui.clickgui.classic.ClassicClickGui;
import me.ltsoveranakin.ghoulish.client.features.gui.clickgui.old.OldClickGuiScreen;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.other.color.RGBASettingCollection;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.EnumSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.ByteSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.LongSetting;
import me.ltsoveranakin.ghoulish.client.storage.StorageHandler;
import net.minecraft.client.world.ClientWorld;
import org.lwjgl.glfw.GLFW;

import java.awt.Color;

public class GuiModule extends Module implements ISubKey, ISubTick {
    public final RGBASettingCollection GUI_BG_COLOR = addCol("gui background", "bg color", new Color(28, 42, 28, 63));
    public final RGBASettingCollection GUI_COLOR = addCol("gui", "color of the gui", new Color(0, 0, 0, 255));
    public final RGBASettingCollection GUI_TEXT_COLOR = addCol("text", "color of the text", new Color(29, 68, 26, 255));
    public final RGBASettingCollection GUI_ENABLED = addCol("gui enabled", "color of enabled modules/settings", new Color(20, 255, 0, 255));
    public final RGBASettingCollection GUI_LABEL_COLOR = addCol("gui label", "color of labels in the clickgui", new Color(154, 0, 0, 255));

    public final ByteSetting SIDE_BUFFER = addByte("side buffer", "buffer (in pixels) from the edge that text should be", 4, 1, 10);
    public final LongSetting DESC_DELAY = addLong("description delay", "delay (in ms) on when to begin rendering a description", 1000, 0, 5000);

    private final EnumSetting<GuiType> guiType = addEnum("guitype", "type of clickgui that is displayed", GuiType.OLD);
    private AbstractClickGui clickGuiScreen;
    private byte lastSideBuf;

    // TODO: add classic gui
    public GuiModule() {
        super("gui", "simple clickgui, (some settings will require the gui to relaunch to take effect)", Category.CLIENT);
    }

    @Override
    public void onDisable() {
        if (mc.currentScreen instanceof AbstractClickGui) mc.currentScreen.close();
        StorageHandler.saveConfig();
    }

    @Override
    public void onEnable() {
        AbstractClickGui cgui;
        // construct new cgui regardless to update for changed config
        switch (guiType.get()) {
            case OLD -> cgui = new OldClickGuiScreen();
            case CLASSIC -> cgui = new ClassicClickGui();
            default -> cgui = null;
        }

        lastSideBuf = SIDE_BUFFER.get();
        mc.setScreen(cgui);
    }

    @Override
    public void onKey(long window, int key, int scancode, int action, int modifier) {
        if (mc.currentScreen != null) return;
        if (key == getBind().get() && action == GLFW.GLFW_PRESS && isEnabled()) {
            disable();
        }
    }

    @Override
    public void onTick(ClientWorld world) {
        if (!(mc.currentScreen instanceof AbstractClickGui)) {
            disable();
        }
    }


    private enum GuiType {
        OLD,
        CLASSIC
    }
}
