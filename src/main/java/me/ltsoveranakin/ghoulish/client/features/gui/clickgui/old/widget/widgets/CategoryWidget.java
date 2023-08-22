package me.ltsoveranakin.ghoulish.client.features.gui.clickgui.old.widget.widgets;

import me.ltsoveranakin.ghoulish.client.features.gui.clickgui.old.OldClickGuiScreen;
import me.ltsoveranakin.ghoulish.client.features.gui.clickgui.old.widget.GuiWidget;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.misc.*;
import me.ltsoveranakin.ghoulish.client.storage.config.ConfigFile;
import me.ltsoveranakin.ghoulish.client.storage.config.config.ConfigCategory;
import me.ltsoveranakin.ghoulish.client.util.RenderUtil2d;
import net.minecraft.client.gui.DrawContext;

public class CategoryWidget extends GuiWidget<ModuleWidget> implements MCInst {
    private final ConfigCategory cfgCat;
    private final Category category;

    public CategoryWidget(Category category, Pos startPos) {
        super(category.name());
        this.category = category;
        cfgCat = ConfigFile.CURRENT_CONFIG.getCats().get(category);
//        System.out.println("CFGCAT " + cfgCat.getName() + " xy " + cfgCat.getX() + " " + cfgCat.getY());

        boolean exp = cfgCat.isExpanded();

        setPos(new Pos(cfgCat.getX(), cfgCat.getY()));
        setExpanded(exp);
    }

    @Override
    protected void setExpanded(boolean exp) {
        super.setExpanded(exp);

        cfgCat.setExpanded(exp);
    }

    @Override
    public void setPos(Pos pos) {
        super.setPos(pos);
        cfgCat.setPos(pos);
    }

    @Override
    public void render(GuiWidget<?> parent, int largest, DrawContext ctx, MouseData mouseData) {
        RenderUtil2d.drawBox(ctx, getPos(), getLargest(), RenderUtil2d.FONT_HEIGHT, guiModule.GUI_COLOR.getColor());
        setLargest(getName());

        RenderUtil2d.drawText(ctx, category.name(), getPos().add((getLargest() - RenderUtil2d.measureText(getName())) / 2), guiModule.GUI_TEXT_COLOR.getColor());

        if (!isExpanded()) return;
        for (int i = 0; i < getChildren().size(); i++) {
            ModuleWidget moduleWidget = getChildren().get(i);
            setLargest(moduleWidget.getName());

            moduleWidget.setPos(getPos().add(0, (i * RenderUtil2d.FONT_HEIGHT) + RenderUtil2d.FONT_HEIGHT));
            moduleWidget.render(this, getLargest(), ctx, mouseData);
        }
    }

    @Override
    public boolean onMouseDown(double mouseX, double mouseY, int button) {
        if (mouseX >= getPos().getX() && mouseX <= getPos().getX() + getLargest() && mouseY >= getPos().getY() && mouseY <= getPos().getY() + RenderUtil2d.FONT_HEIGHT) {
            if (button == 1) {
                setExpanded(!isExpanded());
            } else if (button == 0) {
                if (OldClickGuiScreen.dragging == null) {
                    OldClickGuiScreen.dragging = this;
                    OldClickGuiScreen.dragx = (int) (mouseX - getPos().getX());
                    OldClickGuiScreen.dragy = (int) (mouseY - getPos().getY());
                }
            }

            return true;
        }

        return super.onMouseDown(mouseX, mouseY, button);
    }

    public void postInit() {
        for (ModuleWidget moduleWidget : getChildren()) {
            setLargest(moduleWidget.getName());
        }
    }
}
