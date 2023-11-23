package me.ltsoveranakin.ghoulish.client.util;

import org.lwjgl.glfw.GLFW;

import java.util.*;

public class KeyUtil {
    /**
     * Map from key name to key code
     */
    private static final Map<String, Integer> keyMap = new HashMap<>();

    /**
     * Map from key code to key name
     */
    private static final Map<Integer, String> keyCodeMap = new HashMap<>();

    static {
        for (int i = 0; i < 100; i++) {
            String name = String.valueOf(GLFW.glfwGetKeyName(i, 0));
            if (name.equals("null")) continue;
            keyMap.put(name.toUpperCase(), i);
        }


        // used
        // https://github.com/MeteorDevelopment/meteor-client/blob/master/src/main/java/meteordevelopment/meteorclient/utils/Utils.java#L342

        keyMap.put("NONE", -1);
        keyMap.put("ESC", GLFW.GLFW_KEY_ESCAPE);
        keyMap.put("GRAVE-ACCENT", GLFW.GLFW_KEY_GRAVE_ACCENT);
        keyMap.put("WORLD-1", GLFW.GLFW_KEY_WORLD_1);
        keyMap.put("WORLD-2", GLFW.GLFW_KEY_WORLD_2);
        keyMap.put("PRINT-SCREEN", GLFW.GLFW_KEY_PRINT_SCREEN);
        keyMap.put("PAUSE", GLFW.GLFW_KEY_PAUSE);
        keyMap.put("INSERT", GLFW.GLFW_KEY_INSERT);
        keyMap.put("DELETE", GLFW.GLFW_KEY_DELETE);
        keyMap.put("HOME", GLFW.GLFW_KEY_HOME);
        keyMap.put("PAGE-UP", GLFW.GLFW_KEY_PAGE_UP);
        keyMap.put("PAGE-DOWN", GLFW.GLFW_KEY_PAGE_DOWN);
        keyMap.put("END", GLFW.GLFW_KEY_END);
        keyMap.put("TAB", GLFW.GLFW_KEY_TAB);
        keyMap.put("LEFT-CONTROL", GLFW.GLFW_KEY_LEFT_CONTROL);
        keyMap.put("RIGHT-CONTROL", GLFW.GLFW_KEY_RIGHT_CONTROL);
        keyMap.put("LEFT-ALT", GLFW.GLFW_KEY_LEFT_ALT);
        keyMap.put("RIGHT-ALT", GLFW.GLFW_KEY_RIGHT_ALT);
        keyMap.put("LEFT-SHIFT", GLFW.GLFW_KEY_LEFT_SHIFT);
        keyMap.put("RIGHT-SHIFT", GLFW.GLFW_KEY_RIGHT_SHIFT);
        keyMap.put("UP", GLFW.GLFW_KEY_UP);
        keyMap.put("DOWN", GLFW.GLFW_KEY_DOWN);
        keyMap.put("LEFT", GLFW.GLFW_KEY_LEFT);
        keyMap.put("RIGHT", GLFW.GLFW_KEY_RIGHT);
        keyMap.put("APOSTROPHE", GLFW.GLFW_KEY_APOSTROPHE);
        keyMap.put("BACKSPACE", GLFW.GLFW_KEY_BACKSPACE);
        keyMap.put("CAPS-LOCK", GLFW.GLFW_KEY_CAPS_LOCK);
        keyMap.put("MENU", GLFW.GLFW_KEY_MENU);
        keyMap.put("LEFT-SUPER", GLFW.GLFW_KEY_LEFT_SUPER);
        keyMap.put("RIGHT-SUPER", GLFW.GLFW_KEY_RIGHT_SUPER);
        keyMap.put("ENTER", GLFW.GLFW_KEY_ENTER);
        keyMap.put("KP-ENTER", GLFW.GLFW_KEY_KP_ENTER);
        keyMap.put("NUM-LOCK", GLFW.GLFW_KEY_NUM_LOCK);
        keyMap.put("SPACE", GLFW.GLFW_KEY_SPACE);
        keyMap.put("F1", GLFW.GLFW_KEY_F1);
        keyMap.put("F2", GLFW.GLFW_KEY_F2);
        keyMap.put("F3", GLFW.GLFW_KEY_F3);
        keyMap.put("F4", GLFW.GLFW_KEY_F4);
        keyMap.put("F5", GLFW.GLFW_KEY_F5);
        keyMap.put("F6", GLFW.GLFW_KEY_F6);
        keyMap.put("F7", GLFW.GLFW_KEY_F7);
        keyMap.put("F8", GLFW.GLFW_KEY_F8);
        keyMap.put("F9", GLFW.GLFW_KEY_F9);
        keyMap.put("F10", GLFW.GLFW_KEY_F10);
        keyMap.put("F11", GLFW.GLFW_KEY_F11);
        keyMap.put("F12", GLFW.GLFW_KEY_F12);
        keyMap.put("F13", GLFW.GLFW_KEY_F13);
        keyMap.put("F14", GLFW.GLFW_KEY_F14);
        keyMap.put("F15", GLFW.GLFW_KEY_F15);
        keyMap.put("F16", GLFW.GLFW_KEY_F16);
        keyMap.put("F17", GLFW.GLFW_KEY_F17);
        keyMap.put("F18", GLFW.GLFW_KEY_F18);
        keyMap.put("F19", GLFW.GLFW_KEY_F19);
        keyMap.put("F20", GLFW.GLFW_KEY_F20);
        keyMap.put("F21", GLFW.GLFW_KEY_F21);
        keyMap.put("F22", GLFW.GLFW_KEY_F22);
        keyMap.put("F23", GLFW.GLFW_KEY_F23);
        keyMap.put("F24", GLFW.GLFW_KEY_F24);
        keyMap.put("F25", GLFW.GLFW_KEY_F25);

        for (Map.Entry<String, Integer> entry : keyMap.entrySet()) {
            keyCodeMap.put(entry.getValue(), entry.getKey());
        }
    }

    public static String getKeyName(int key) {
        if (keyCodeMap.containsKey(key)) {
            return keyCodeMap.get(key);
        }

        return "UNKNOWN";
    }

    public static int getKeyCode(String key) {
        Integer keyCode = keyMap.get(key.toUpperCase());

        if (keyCode != null) {
            return keyCode;
        }

        return -1;
    }
}
