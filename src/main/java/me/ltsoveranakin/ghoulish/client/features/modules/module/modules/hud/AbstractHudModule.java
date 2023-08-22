package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.hud;

import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubHudRender;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.FloatSetting;
import me.ltsoveranakin.ghoulish.client.misc.Box2D;
import me.ltsoveranakin.ghoulish.client.misc.Pos;
import me.ltsoveranakin.ghoulish.client.util.WindowUtil;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHudModule extends Module implements ISubHudRender {
    private final List<Box2D> collisionBoxes = new ArrayList<>();

    private final FloatSetting xPercent;
    private final FloatSetting yPercent;

    public AbstractHudModule(String name, String desc, @Range(from = 0, to = 1) float pX, @Range(from = 0, to = 1) float pY) {
        super(name, desc, Category.HUD);

        xPercent = addFloat("x", "x percentage of this module when rendered", pX, 0, 1);
        yPercent = addFloat("y", "y percentage of this module when rendered", pY, 0, 1);
    }

    public boolean doesMouseCollide(double mx, double my) {
        for (Box2D box : collisionBoxes) {
            if (box.isInside(mx, my)) {
                return true;
            }
        }

        return false;
    }

    public void setPos(int x, int y) {
        xPercent.set(x / (float) WindowUtil.getWidth());
        yPercent.set(y / (float) WindowUtil.getHeight());
    }

    protected Quadrant getQuadrant() {
        int x = getPos().getX();
        int y = getPos().getY();
        int hWidth = WindowUtil.getWidth() / 2;
        int hHeight = WindowUtil.getWidth() / 2;

        if (x < hWidth) {
            if (y < hHeight) {
                return Quadrant.LEFT_UP;
            } else {
                return Quadrant.LEFT_DOWN;
            }
        } else {
            if (y < hHeight) {
                return Quadrant.RIGHT_UP;
            } else {
                return Quadrant.RIGHT_DOWN;
            }
        }
    }

    public Pos getPos() {
        return new Pos((int) (xPercent.get() * WindowUtil.getWidth()), (int) (yPercent.get() * WindowUtil.getHeight()));
    }

    public float getXPercent() {
        return xPercent.get();
    }

    public float getYPercent() {
        return yPercent.get();
    }

    public void snap() {
    }

    @Override
    public final void onRender(DrawContext ctx, float tickDelta, boolean isGui) {
        collisionBoxes.clear();

        onRenderImpl(ctx, tickDelta, isGui);
    }

    public abstract void onRenderImpl(DrawContext ctx, float tickDelta, boolean isGui);

    public List<Box2D> getCollisionBoxes() {
        return collisionBoxes;
    }

    protected enum Quadrant {
        LEFT_UP,
        RIGHT_UP,
        LEFT_DOWN,
        RIGHT_DOWN
    }
}
