package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;

public class SettingParseException extends Exception {
    public SettingParseException(Setting<?> setting, String invalidValue) {
        super("Failed to parse setting " + setting.getName() + ", with an invalid value of " + invalidValue);
    }
}
