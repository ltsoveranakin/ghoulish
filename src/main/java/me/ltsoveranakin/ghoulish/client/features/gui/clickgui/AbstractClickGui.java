package me.ltsoveranakin.ghoulish.client.features.gui.clickgui;

import me.ltsoveranakin.ghoulish.client.misc.MinecraftInstance;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public abstract class AbstractClickGui extends Screen implements MinecraftInstance {
    protected AbstractClickGui(String title) {
        super(Text.of(title));
    }

    @Override
    public abstract void render(DrawContext context, int mouseX, int mouseY, float delta);
}
