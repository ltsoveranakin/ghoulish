package me.ltsoveranakin.ghoulish.client.util;

import me.ltsoveranakin.ghoulish.client.misc.MCInst;

public class WindowUtil implements MCInst {
    public static int getWidth() {
        return mc.getWindow().getScaledWidth();
    }

    public static int getHeight() {
        return mc.getWindow().getScaledHeight();
    }
}
