package me.ltsoveranakin.ghoulish.client.features.modules.group;

import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import me.ltsoveranakin.ghoulish.client.misc.named.NamedDesc;

import java.util.ArrayList;
import java.util.List;

public class SettingGroup extends NamedDesc {
    private final List<Setting<?>> settings = new ArrayList<>();

    public SettingGroup(String name, String desc, Module module) {
        super(name, desc);

        module.getGroups().put(name, this);
    }

    public <K extends Setting<?>> K register(K setting) {
        settings.add(setting);
        setting.setGroup(getName());
        return setting;
    }

    public List<Setting<?>> getSettings() {
        return settings;
    }
}
