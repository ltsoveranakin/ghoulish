package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.hud.hud;

import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.ISubKey;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.hud.AbstractHudModule;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.other.color.RGBASettingCollection;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.BindSetting;
import me.ltsoveranakin.ghoulish.client.misc.Box2D;
import me.ltsoveranakin.ghoulish.client.storage.StorageHandler;
import me.ltsoveranakin.ghoulish.client.util.RenderUtil2d;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.Nullable;

import java.awt.Color;

import static org.lwjgl.glfw.GLFW.*;

public class TabGUIModule extends AbstractHudModule implements ISubKey {
    public final RGBASettingCollection textColor = addCol("textcolor", "color of the text", new Color(29, 68, 26, 255));
    private final BindSetting selectBind = addBind("select", "the keybind to select the current selection", GLFW_KEY_RIGHT);
    private final BindSetting deselectBind = addBind("deselect", "the keybind to deselect the current selection", GLFW_KEY_LEFT);
    private final BindSetting upBind = addBind("up", "the keybind to select the above selection", GLFW_KEY_UP);
    private final BindSetting downBind = addBind("down", "the keybind to select the below selection", GLFW_KEY_DOWN);

    private final RGBASettingCollection bGColor = addCol("bgcolor", "the background color of the tabgui", new Color(0, 0, 0));
    private final RGBASettingCollection bGSelected = addCol("bgselectedcolor", "the selected background color of the tabgui", new Color(20, 255, 0, 255));
    private final RGBASettingCollection bgModuleEnabled = addCol("bgmoduleenabled", "the background color of settings which are enabled", new Color(191, 229, 189, 255));

    private int categoryIndex = 0;
    private Category categoryOn = Category.values()[categoryIndex];

    private int moduleIndex = 0;
    @Nullable
    private Module moduleOn = null;

    private boolean inModule = false;

    private int largestCategory = 0;
    private int largestModule = 0;

    public TabGUIModule() {
        super("tabgui", "manage modules without opening the cgui", 0f, 0f);
    }

    @Override
    public void onRenderImpl(DrawContext ctx, float tickDelta, boolean isGui) {
        int categoryRenX = getPos().getX();
        int categoryRenY = getPos().getY();

        for (Category category : Category.values()) {
            boolean renderingSelectedCategory = categoryOn == category;
            int txtSize = RenderUtil2d.measureText(category.name());

            if (txtSize > largestCategory) {
                largestCategory = txtSize;
            }

            Box2D categoryBox = RenderUtil2d.drawBox(ctx, categoryRenX, categoryRenY, categoryRenX + largestCategory, categoryRenY + RenderUtil2d.FONT_HEIGHT, renderingSelectedCategory ? bGSelected.getColor() : bGColor.getColor());
            getCollisionBoxes().add(categoryBox);
            RenderUtil2d.drawText(ctx, category.name(), categoryBox.add((largestCategory - txtSize) / 2), textColor.getColor());

            if (inModule && renderingSelectedCategory) {
                renderModules(categoryBox, category, ctx);
            }

            categoryRenY += RenderUtil2d.FONT_HEIGHT;
        }
    }

    private void renderModules(Box2D categoryBox, Category category, DrawContext ctx) {
        int moduleRenY = categoryBox.getY();

        for (Module module : category.getModules()) {
            boolean renderingSelectedModule = moduleOn == module;
            int modTxtSize = RenderUtil2d.measureText(module.getName());

            if (modTxtSize > largestModule) {
                largestModule = modTxtSize;
            }

            Box2D moduleBox = RenderUtil2d.drawBox(ctx, categoryBox.getEndX(), moduleRenY, categoryBox.getEndX() + largestModule, moduleRenY + RenderUtil2d.FONT_HEIGHT,
                    renderingSelectedModule ?
                            bGSelected.getColor() : module.isEnabled() ?
                            bgModuleEnabled.getColor() : bGColor.getColor());
            RenderUtil2d.drawText(ctx, module.getName(), moduleBox.add((largestModule - modTxtSize) / 2), textColor.getColor());
            moduleRenY += RenderUtil2d.FONT_HEIGHT;
        }
    }

    @Override
    public void onKey(long window, int key, int scancode, int action, int modifier) {
        if (action != GLFW_PRESS) return;

        if (key == selectBind.get()) {
            select();
        } else if (key == deselectBind.get()) {
            deselect();
        } else if (key == upBind.get()) {
            vertical(-1);
        } else if (key == downBind.get()) {
            vertical(1);
        }
    }

    private void select() {
        if (!inModule) {
            moduleIndex = 0;
            inModule = true;
        } else {
            if (moduleOn == null) {
                throw new NullPointerException("moduleOn shouldn't be null");
            }
            moduleOn.toggle();

            StorageHandler.saveConfig();
        }

        updateOn();
    }

    private void deselect() {
        if (inModule) {
            inModule = false;
            moduleOn = null;
            moduleIndex = 0;
        }
    }

    private void vertical(int amt) {
        if (inModule) {
            moduleIndex = wrapVal(moduleIndex, amt, categoryOn.getModules().size());
        } else {
            categoryIndex = wrapVal(categoryIndex, amt, Category.values().length);
        }

        updateOn();
    }

    private void updateOn() {
        categoryOn = Category.values()[categoryIndex];
        if (inModule) {
            moduleOn = categoryOn.getModules().get(moduleIndex);
        } else {
            moduleOn = null;
        }
    }

    private int wrapVal(int cur, int amt, int max) {
        cur += amt;

        if (cur < 0) {
            cur = max - 1;
        } else if (cur >= max) {
            cur = 0;
        }

        return cur;
    }
}
