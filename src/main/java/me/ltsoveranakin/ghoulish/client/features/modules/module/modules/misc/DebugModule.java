package me.ltsoveranakin.ghoulish.client.features.modules.module.modules.misc;

import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.BoolSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.IntSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.DoubleSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.FloatSetting;
import me.ltsoveranakin.ghoulish.client.util.RenderUtil2d;
import me.ltsoveranakin.ghoulish.client.util.WindowUtil;

public class DebugModule extends Module {
    private final BoolSetting bool = addBool("bool", "", true);
    private final DoubleSetting dtest = addDub("dub", "", .4d, .3d, 1d);
    private final FloatSetting ftest = addFloat("flt", "", .4f, .3f, 1f);
    private final IntSetting itest = addInt("int", "", 4, 3, 10);
    public final BoolSetting fLog = addBool("plog", "", false);


    public DebugModule() {
        super("debug", "a test", Category.MISC);
    }

    @Override
    public void onEnable() {
        System.out.println("=====================================");
        System.out.println("YAW: " + mc.player.getYaw() + " PITCH: " + mc.player.getPitch());
        System.out.println("FONT HEIGHT: " + mc.textRenderer.fontHeight + " RENDER UTIL FONT HEIGHT: " + RenderUtil2d.FONT_HEIGHT);
        System.out.println("SCALED RESOLUTION: " + mc.getWindow().getScaledWidth() + " " + mc.getWindow().getScaledHeight());
        System.out.println("WINDOW RESOLUTION: " + WindowUtil.getWidth() + " " + WindowUtil.getHeight());
        System.out.println("GUI SCALE: " + mc.options.getGuiScale().getValue());
        System.out.println("=====================================");
    }
}
