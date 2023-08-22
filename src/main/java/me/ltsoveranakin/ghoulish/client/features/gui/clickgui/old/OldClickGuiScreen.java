package me.ltsoveranakin.ghoulish.client.features.gui.clickgui.old;

import me.ltsoveranakin.ghoulish.client.features.gui.clickgui.AbstractClickGui;
import me.ltsoveranakin.ghoulish.client.features.gui.clickgui.old.widget.GuiWidget;
import me.ltsoveranakin.ghoulish.client.features.gui.clickgui.old.widget.widgets.CategoryWidget;
import me.ltsoveranakin.ghoulish.client.features.gui.clickgui.old.widget.widgets.ModuleWidget;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.client.GuiModule;
import me.ltsoveranakin.ghoulish.client.misc.MouseData;
import me.ltsoveranakin.ghoulish.client.misc.Pos;
import me.ltsoveranakin.ghoulish.client.util.RenderUtil2d;
import me.ltsoveranakin.ghoulish.client.util.WindowUtil;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.Nullable;

import java.util.*;


public class OldClickGuiScreen extends AbstractClickGui {
    public static @Nullable CategoryWidget dragging;
    public static GuiWidget<?> renderingDesc;
    public static int dragx;
    public static int dragy;
    private final List<CategoryWidget> cats = new ArrayList<>();
    private final GuiModule guiModule = ModuleManager.getModule(GuiModule.class);

    public OldClickGuiScreen() {
        super("click_gui");
        renderingDesc = null;

        Map<Category, CategoryWidget> catMap = new HashMap<>();

        int posInc = 50;
        for (Module module : ModuleManager.MODULES) {

            CategoryWidget catWidget;
            if (!catMap.containsKey(module.getCategory())) {
                posInc += RenderUtil2d.measureText(module.getCategory().name());
                catWidget = new CategoryWidget(module.getCategory(), new Pos(50, posInc));
                catMap.put(module.getCategory(), catWidget);
                cats.add(catWidget);
            } else {
                catWidget = catMap.get(module.getCategory());
            }

            catWidget.getChildren().add(new ModuleWidget(module));
            catWidget.postInit();
        }

    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        RenderUtil2d.drawBox(ctx, 0, 0, WindowUtil.getWidth(), WindowUtil.getHeight(), guiModule.GUI_BG_COLOR.getColor());
        for (CategoryWidget cat : cats) {
            cat.render(null, -1, ctx, new MouseData(mouseX, mouseY));
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void updateNarrator() {
        super.updateNarrator();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (CategoryWidget categoryWidget : cats) {
            if (categoryWidget.onMouseDown(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = null;
        for (CategoryWidget categoryWidget : cats) {
            if (categoryWidget.onMouseUp(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if (dragging != null) {
            dragging.setPos(new Pos((int) mouseX - dragx, (int) mouseY - dragy));
        }

        for (CategoryWidget categoryWidget : cats) {
            if (categoryWidget.onMouseMove(mouseX, mouseY)) {
                return;
            }
        }
    }
}
