package me.ltsoveranakin.ghoulish.client.util;

import me.ltsoveranakin.ghoulish.client.misc.MinecraftInstance;

public class WindowUtil implements MinecraftInstance {
    public static int getWidth() {
        return mc.getWindow().getScaledWidth();
    }

    public static int getHeight() {
        return mc.getWindow().getScaledHeight();
    }
}
