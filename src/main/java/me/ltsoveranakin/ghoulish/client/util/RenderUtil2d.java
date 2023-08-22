package me.ltsoveranakin.ghoulish.client.util;

import me.ltsoveranakin.ghoulish.client.misc.*;
import net.minecraft.client.gui.DrawContext;

import java.awt.Color;

public class RenderUtil2d implements MCInst {
    public static final int FONT_HEIGHT = mc.textRenderer.fontHeight;

    public static int measureText(String text) {
        if(mc.textRenderer == null) return 50;
        return mc.textRenderer.getWidth(text);
    }

    public static void drawText(DrawContext ctx, String text, int x, int y, Color color) {
        ctx.drawText(mc.textRenderer, text, x, y, color.hashCode(), false);
    }

    public static Box2D drawBox(DrawContext ctx, int x, int y, int x1, int y1, Color color) {
        ctx.fill(x, y, x1, y1, color.hashCode());
        return new Box2D(x, y, x1, y1);
    }

    public static Box2D drawBox(DrawContext ctx, Pos pos, int w, int h, Color color) {
        return drawBox(ctx, pos.getX(), pos.getY(), pos.getX() + w, pos.getY() + h, color);
    }

    public static void drawText(DrawContext ctx, String text, Pos pos, Color color) {
        drawText(ctx, text, pos.getX(), pos.getY(), color);
    }
}
