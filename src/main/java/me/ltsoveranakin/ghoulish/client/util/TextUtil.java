package me.ltsoveranakin.ghoulish.client.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.awt.Color;

import static java.awt.Color.RED;

public class TextUtil {
    public static MutableText red(String txt) {
        return of(txt, RED);
    }

    public static MutableText of(String txt, Color color) {
        return of(Text.literal(txt), color.hashCode());
    }

    public static MutableText of(MutableText txt, int color) {
        return txt.styled(sU -> sU.withColor(color));
    }

    public static MutableText red(MutableText txt) {
        return of(txt, RED);
    }

    public static MutableText of(MutableText txt, Color color) {
        return of(txt, color.hashCode());
    }

    public static MutableText green(String txt) {
        return of(txt, Color.GREEN);
    }

    public static MutableText blue(String txt) {
        return of(txt, Color.BLUE);
    }

    public static MutableText collect(MutableText... texts) {
        MutableText head = texts[0];

        for (int i = 0; i < texts.length; i++) {
            if (i == 0) continue;

            head.append(texts[i]);
        }

        return head;
    }
}
