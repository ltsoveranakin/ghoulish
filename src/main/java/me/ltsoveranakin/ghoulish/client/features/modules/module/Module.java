package me.ltsoveranakin.ghoulish.client.features.modules.module;

import me.ltsoveranakin.ghoulish.client.event.sub.interfaces.IDispatchable;
import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.group.SettingGroup;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.other.color.RGBASettingCollection;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.other.color.colorsettings.*;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.other.label.LabelSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.*;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.*;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.DoubleSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.FloatSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.range.AbstractRangeSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.range.range.RangeFloatSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.num.range.range.RangeIntSetting;
import me.ltsoveranakin.ghoulish.client.misc.MCInst;
import me.ltsoveranakin.ghoulish.client.misc.named.NamedDesc;
import me.ltsoveranakin.ghoulish.client.util.TextUtil;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.awt.Color;
import java.util.*;

public class Module extends NamedDesc implements MCInst, IDispatchable {
    private final List<Setting<?>> settings = new ArrayList<>();
    private final Map<String, SettingGroup> groups = new LinkedHashMap<>();

    private final Category category;

    private final SettingGroup globalSettings = group("global", "these settings are the same on every module, and change how the module behaves in the same way");

    private final BoolSetting enabled = globalSettings
            .register(addBool("enabled", "setting on whether or not the module is enable", false));

    private final BindSetting bind = globalSettings
            .register(addBind("bind", "keybinding, when this key is pressed it will toggle the module"));

    private final EnumSetting<NotifMode> notifs = globalSettings
            .register(addEnum("notifications", "", NotifMode.NONE));

    private final BoolSetting renderOnList = globalSettings
            .register(addBool("renderonlist", "renders this module on the arraylist", true));

    private final SettingGroup defaultLabel = group("default", "these settings are different on each module and vary greatly");


    public Module(String name, String desc, Category cat) {
        super(name, desc);
        category = cat;
    }

    public boolean shouldRender() {
        return renderOnList.get();
    }

    public @Nullable Setting<?> getSetting(String name) {
        for (Setting<?> s : getSettings()) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }

        return null;
    }

    public List<Setting<?>> getSettings() {
        return settings;
    }

    public Map<String, SettingGroup> getGroups() {
        return groups;
    }

    public final void toggle() {
        if (enabled.get()) {
            disable();
        } else enable();
    }

    public final void disable() {
        if (notifs.get() == NotifMode.CHAT) info(TextUtil.red("disabled"));
        enabled.set(false);
        onDisable();
    }

    public final void enable() {
        if (notifs.get() == NotifMode.CHAT) info(TextUtil.green("enabled"));
        enabled.set(true);
        onEnable();
    }

    public void onDisable() {}
    public void onEnable() {}
    public void onPostInit() {}

    protected void info(String txt) {
        info(Text.literal(txt));
    }

    public final void postInit() {
        for(Setting<?> setting : settings) {
            if(setting.getGroup() == null) {
                defaultLabel.register(setting);
            }
        }
        onPostInit();
    }

    protected SettingGroup group(String name, String desc) {
        return new SettingGroup(name, desc, this);
    }

    private <T extends Setting<?>> T addSetting(T setting) {
        if(setting instanceof LabelSetting) {
            throw new RuntimeException("Cannot add label setting to module");
        }

        settings.add(setting);
        return setting;
    }

    protected BoolSetting addBool(String name, String desc, boolean defaultVal) {
        return addSetting(new BoolSetting(name, desc, defaultVal, this));
    }

    protected BindSetting addBind(String name, String desc) {
        return addBind(name, desc, -1);
    }

    protected BindSetting addBind(String name, String desc, int defBind) {
        return addSetting(new BindSetting(name, desc, defBind, this));
    }

    protected ByteSetting addByte(String name, String desc, @Range(from = Byte.MIN_VALUE, to = Byte.MAX_VALUE) int defVal, @Range(from = Byte.MIN_VALUE, to = Byte.MAX_VALUE) int min, @Range(from = Byte.MIN_VALUE, to = Byte.MAX_VALUE) int max) {
        return addSetting(new ByteSetting(name, desc, (byte) defVal, (byte) min, (byte) max, this));
    }

    protected RGBASettingCollection addCol(String name, String desc, Color defCol) {
        SettingGroup collectionGroup = group(name + " color settings", "various settings to change the color appearance");

        var r = collectionGroup.register(addIntCol(name + " red", desc, defCol.getRed()));
        var g = collectionGroup.register(addIntCol(name + " green", desc, defCol.getGreen()));
        var b = collectionGroup.register(addIntCol(name + " blue", desc, defCol.getBlue()));
        var a = collectionGroup.register(addIntCol(name + " alpha", desc, defCol.getAlpha()));

        var rainbow = collectionGroup.register(addSetting(new BoolColorSetting(name + "rainbow", desc, false, this)));
        var speed = collectionGroup.register(addFloatCol(name + " rainbow speed", desc, .001f, 0, .001f));
        var saturation = collectionGroup.register(addFloatCol(name + " rainbow saturation", desc, 1));
        var brightness = collectionGroup.register(addFloatCol(name + " rainbow brightness", desc, 1));

        return new RGBASettingCollection(r, g, b, a, rainbow, speed, saturation, brightness);
    }

    private IntColorSetting addIntCol(String name, String desc, int defVal) {
        return addSetting(new IntColorSetting(name, desc, defVal, this));
    }

    private FloatColorSetting addFloatCol(String name, String desc, float defVal, float min, float max) {
        return addSetting(new FloatColorSetting(name, desc, defVal, min, max, this));
    }

    private FloatColorSetting addFloatCol(String name, String desc, float defVal) {
        return addSetting(new FloatColorSetting(name, desc, defVal, this));
    }

    protected DoubleSetting addDub(String name, String desc, double defVal, double min, double max) {
        return addSetting(new DoubleSetting(name, desc, defVal, min, max, this));
    }

    protected RangeIntSetting addMinMaxInt(String name, String desc, int defVal, int min, int max) {
        IntSetting intMin = addInt(name + "min", desc, defVal, min, max);
        IntSetting intMax = addInt(name + "max", desc, defVal, min, max);
        return new RangeIntSetting(intMin, intMax);
    }

    protected IntSetting addInt(String name, String desc, int defVal, int min, int max) {
        return addSetting(new IntSetting(name, desc, defVal, min, max, this));
    }

    protected AbstractRangeSetting<Float> addMinMaxFloat(String name, String desc, float defVal, float min, float max) {
        FloatSetting fMin = addFloat(name + "min", desc, defVal, min, max);
        FloatSetting fMax = addFloat(name + "max", desc, defVal, min, max);
        return new RangeFloatSetting(fMin, fMax);
    }

    protected FloatSetting addFloat(String name, String desc, float defVal, float min, float max) {
        return addSetting(new FloatSetting(name, desc, defVal, min, max, this));
    }

    protected LongSetting addLong(String name, String desc, long defVal, long min, long max) {
        return addSetting(new LongSetting(name, desc, defVal, min, max, this));
    }

    protected <T extends Enum<?>> EnumSetting<T> addEnum(String name, String desc, T defVal) {
        return addSetting(new EnumSetting<>(name, desc, defVal, this));
    }

    protected StringSetting addStr(String name, String desc, String defVal) {
        return addSetting(new StringSetting(name, desc, defVal, this));
    }

    public Category getCategory() {
        return category;
    }

    public BoolSetting getEnabled() {
        return enabled;
    }

    public boolean isEnabled() {
        return enabled.get();
    }

    public BindSetting getBind() {
        return bind;
    }

    @Override
    public boolean shouldDispatch() {
        return enabled.get();
    }

    protected void error(String txt, boolean shouldDisable) {
        super.error(txt);
        if (shouldDisable) {
            disable();
        }
    }

    enum NotifMode {
        NONE,
        CHAT
    }
}
