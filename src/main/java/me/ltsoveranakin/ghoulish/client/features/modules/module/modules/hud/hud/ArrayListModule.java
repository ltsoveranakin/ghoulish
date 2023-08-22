package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.hud.hud;

import me.ltsoveranakin.ghoulish.client.features.modules.ModuleManager;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.module.modules.hud.AbstractHudModule;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.other.color.RGBASettingCollection;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.BoolSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.EnumSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.IntSetting;
import me.ltsoveranakin.ghoulish.client.util.RenderUtil2d;
import net.minecraft.client.gui.DrawContext;

import java.awt.Color;
import java.util.*;

public class ArrayListModule extends AbstractHudModule {
    private final IntSetting sideBuf = addInt("padding", "padding beyond the module name added to the background", 3, 0, 8);
    private final RGBASettingCollection bgArCol = addCol("bgcol", "background color of the arraylist", new Color(33, 82, 171, 144));
    private final RGBASettingCollection arTxtCol = addCol("txtcol", "color of the text on the arraylist", new Color(12, 18, 35, 144));

    private List<Module> sortedModules;
    private boolean firstRen = true;

    public ArrayListModule() {
        super("arraylist", "visual list of enabled modules", 1f, 0f);
    }

    @Override
    public void onPostInit() {
        sortedModules = new ArrayList<>(ModuleManager.MODULES);
    }

    @Override
    public void onRenderImpl(DrawContext ctx, float tickDelta, boolean isGui) {
        if (firstRen) {
            sort();
            firstRen = false;
        }

        int renX = getPos().getX();
        int renY = getPos().getY();
        getCollisionBoxes().clear();

        for (Module module : sortedModules) {
            if (!module.isEnabled() || !module.shouldRender()) continue;
            int nameWidth = RenderUtil2d.measureText(module.getName());

            if (getXPercent() < .5) {
                getCollisionBoxes().add(RenderUtil2d.drawBox(ctx,
                        renX,
                        renY,
                        nameWidth + renX + (sideBuf.get() * 2),
                        renY + RenderUtil2d.FONT_HEIGHT,
                        bgArCol.getColor()));

                RenderUtil2d.drawText(ctx, module.getName(), (renX) + sideBuf.get(), renY, arTxtCol.getColor());
            } else {
                getCollisionBoxes().add(RenderUtil2d.drawBox(ctx,
                        (renX - nameWidth) - (sideBuf.get() * 2),
                        renY,
                        renX,
                        renY + RenderUtil2d.FONT_HEIGHT,
                        bgArCol.getColor()));

                RenderUtil2d.drawText(ctx, module.getName(), (renX - nameWidth) - sideBuf.get(), renY, arTxtCol.getColor());
            }

            renY += RenderUtil2d.FONT_HEIGHT;
        }
    }

    private boolean sort() {
        if (sortedModules == null || mc.textRenderer == null) return true;
        switch (sort.get()) {
            case ALPHABETICAL -> sortedModules.sort(Comparator.comparing(Module::getName));
            case SIZE -> sortedModules.sort(Comparator.comparingInt(m -> RenderUtil2d.measureText(m.getName())));
        }

        if (invertSort.get()) {
            Collections.reverse(sortedModules);
        }
        return true;
    }

    private enum SortMode {
        ALPHABETICAL,
        SIZE
    }    private final EnumSetting<SortMode> sort = addEnum("sortmode", "the order in which modules are listed", SortMode.ALPHABETICAL).onSet(this::sort);




    private final BoolSetting invertSort = addBool("invertsort", "inverts the direction in which modules are sorted", false).onSet(this::sort);


}
