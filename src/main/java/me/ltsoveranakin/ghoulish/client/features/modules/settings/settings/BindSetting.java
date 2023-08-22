package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BindSetting extends Setting<Integer> {
    public BindSetting(String name, String desc, Integer defBind, Module mod) {
        super(name, desc, defBind, mod, EnumSettingType.BIND);
    }

    @Override
    public Integer fromBytesImpl(DataInputStream dis) throws IOException {
        return dis.readInt();
    }

    @Override
    public void toBytesImpl(DataOutputStream dos) throws IOException {
        dos.writeInt(get());
    }

    @Override
    public @NotNull Integer parse(String str) throws Exception {
        return Integer.parseInt(str);
    }
}
