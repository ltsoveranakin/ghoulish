package me.ltsoveranakin.ghoulish.client.features.gui.clickgui.classic;

import me.ltsoveranakin.ghoulish.client.features.gui.clickgui.AbstractClickGui;
import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import net.minecraft.client.gui.DrawContext;

public class ClassicClickGui extends AbstractClickGui {
    public ClassicClickGui() {
        super("classic_click_gui");
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        for (Module module : ModuleManager.MODULES) {

        }
    }
}
