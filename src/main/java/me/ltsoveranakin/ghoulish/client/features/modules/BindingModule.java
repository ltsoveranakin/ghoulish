package me.ltsoveranakin.ghoulish.client.features.modules;

import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.BindSetting;
import org.jetbrains.annotations.NotNull;

public record BindingModule(@NotNull Module module, @NotNull BindSetting setting) {

}
