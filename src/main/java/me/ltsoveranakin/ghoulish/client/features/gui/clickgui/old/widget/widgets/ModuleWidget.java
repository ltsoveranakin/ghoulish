package me.ltsoveranakin.ghoulish.client.features.gui.clickgui.old.widget.widgets;

import me.ltsoveranakin.ghoulish.client.features.gui.clickgui.old.widget.GuiWidget;
import me.ltsoveranakin.ghoulish.client.features.modules.group.SettingGroup;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.other.label.LabelSetting;
import me.ltsoveranakin.ghoulish.client.misc.MouseData;
import me.ltsoveranakin.ghoulish.client.util.RenderUtil2d;
import net.minecraft.client.gui.DrawContext;

public class ModuleWidget extends GuiWidget<SettingWidget> {
    private final Module module;
    private int lChild;

    public ModuleWidget(Module m) {
        super(m.getName());
        module = m;

        for (SettingGroup group : module.getGroups().values()) {
            LabelSetting labelSetting = new LabelSetting(group.getName(), group.getDesc());
            getChildren().add(new SettingWidget(labelSetting, this));

            for (Setting<?> setting : group.getSettings()) {
                getChildren().add(new SettingWidget(setting, this));
            }
        }
    }

    @Override
    public void render(GuiWidget<?> parent, int largestChild, DrawContext ctx, MouseData mouseData) {
        RenderUtil2d.drawBox(ctx, getPos(), largestChild, RenderUtil2d.FONT_HEIGHT, module.isEnabled() ? guiModule.GUI_ENABLED.getColor() : guiModule.GUI_COLOR.getColor());
        RenderUtil2d.drawText(ctx, getName(), getPos().add((largestChild - RenderUtil2d.measureText(getName())) / 2), guiModule.GUI_TEXT_COLOR.getColor());
        lChild = largestChild;

        if (!isExpanded()) return;

        int subI = 0;
        // rewrite setting requirements

        renLoop:
        for (int i = 0; i < getChildren().size(); i++) {
            SettingWidget settingWidget = getChildren().get(i);

            if (settingWidget.getSetting().getRequirements() != null) {
                for (var req : settingWidget.getSetting().getRequirements()) {
                    if (!req.test()) {
                        subI++;
                        settingWidget.setPos(getPos().add(largestChild, ((i - subI) * RenderUtil2d.FONT_HEIGHT)));
                        continue renLoop;
                    }
                }
            }

            settingWidget.setPos(getPos().add(largestChild, ((i - subI) * RenderUtil2d.FONT_HEIGHT)));
            settingWidget.render(this, getLargest(), ctx, mouseData);
        }
    }

    @Override
    public boolean onMouseDown(double mouseX, double mouseY, int button) {
        if (mouseX >= getPos().getX() && mouseX <= getPos().getX() + lChild && mouseY >= getPos().getY() && mouseY <= getPos().getY() + RenderUtil2d.FONT_HEIGHT) {
            if (button == 1) {
                setExpanded(!isExpanded());
            } else if (button == 0) {
                module.toggle();
            }
            return true;
        }

        if (!isExpanded()) return false;

        return super.onMouseDown(mouseX, mouseY, button);
    }

    @Override
    public boolean onMouseMove(double mouseX, double mouseY) {
        if (!isExpanded()) return false;
        return super.onMouseMove(mouseX, mouseY);
    }
}
