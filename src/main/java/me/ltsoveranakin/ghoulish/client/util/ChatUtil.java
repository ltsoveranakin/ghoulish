package me.ltsoveranakin.ghoulish.client.util;

import me.ltsoveranakin.ghoulish.client.misc.MCInst;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class ChatUtil implements MCInst {
    // TODO: add ChatUtil.error
    public static void info(MutableText name, MutableText msg) {
        sendMsg(TextUtil.collect(TextUtil.blue("["), name, TextUtil.blue("] -> "), msg));
    }

    private static void sendMsg(Text txt) {
        if (mc.player == null) return;

        mc.player.sendMessage(txt);
    }

    public static void info(String msg) {
        sendMsg(TextUtil.collect(TextUtil.blue("["), TextUtil.green("GHOULISH"), TextUtil.blue("]"), TextUtil.green(" -> "), TextUtil.green(msg)));
    }

    private static void sendMsg(String msg) {
        sendMsg(Text.of(msg));
    }
}
