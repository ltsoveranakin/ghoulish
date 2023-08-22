package me.ltsoveranakin.ghoulish.client.features.modules.settings.other.color;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.other.color.colorsettings.BoolColorSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.other.color.colorsettings.FloatColorSetting;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.other.color.colorsettings.IntColorSetting;

import java.awt.Color;

public class RGBASettingCollection {
    private final IntColorSetting r;
    private final IntColorSetting g;
    private final IntColorSetting b;
    private final IntColorSetting a;
    private final BoolColorSetting rainbow;
    private final FloatColorSetting speed;
    private final FloatColorSetting saturation;
    private final FloatColorSetting brightness;
    private final Setting<?>[] colorSettings;
    private Color color;
    private float rainBowHSB = 0;

    public RGBASettingCollection(IntColorSetting r,
                                 IntColorSetting g,
                                 IntColorSetting b,
                                 IntColorSetting a,
                                 BoolColorSetting rainbow,
                                 FloatColorSetting speed,
                                 FloatColorSetting saturation,
                                 FloatColorSetting brightness) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.rainbow = rainbow;
        this.speed = speed;
        this.saturation = saturation;
        this.brightness = brightness;

        r.setCollection(this);
        g.setCollection(this);
        b.setCollection(this);
        a.setCollection(this);
        rainbow.setCollection(this);
        speed.setCollection(this);
        saturation.setCollection(this);
        brightness.setCollection(this);

        rainbow.onSet(this::setColor);

        speed.requiresSetting(rainbow, true);
        saturation.requiresSetting(rainbow, true);
        brightness.requiresSetting(rainbow, true);


        r.requiresSetting(rainbow, false);
        g.requiresSetting(rainbow, false);
        b.requiresSetting(rainbow, false);
//        a.requires(rainBow, false);

        setColor();

        colorSettings = new Setting<?>[]{r, g, b, a, rainbow, speed, saturation, brightness};
    }

    public boolean setColor() {
        color = new Color(r.get(), g.get(), b.get(), a.get());
        return true;
    }

    public Color getColor() {
        if (rainbow.get()) {
            rainBowHSB += speed.get();

            var tmpCol = Color.getHSBColor(rainBowHSB, saturation.get(), brightness.get());
            color = new Color(tmpCol.getRed(), tmpCol.getGreen(), tmpCol.getBlue(), a.get());


            if (rainBowHSB >= 1) rainBowHSB = 0;
        }
        return color;
    }

    public <K> RGBASettingCollection requires(Setting<K> setting, K toBe) {
        for (Setting<?> set : colorSettings) {
            set.requiresSetting(setting, toBe);
        }
        return this;
    }
}
