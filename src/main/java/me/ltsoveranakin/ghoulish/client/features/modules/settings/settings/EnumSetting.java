package me.ltsoveranakin.ghoulish.client.features.modules.settings.settings;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.EnumSettingType;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.features.modules.settings.Setting;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class EnumSetting<T extends Enum<?>> extends Setting<T> {
    private int enumOn = 0;

    public EnumSetting(String name, String desc, T defVal, Module mod) {
        super(name, desc, defVal, mod, EnumSettingType.ENUM);
    }

    public void next() {
        enumOn++; // this might b issue
        T[] consts = getEnums();
        if (enumOn >= consts.length) {
            enumOn = 0;
        }

        set(consts[enumOn]);
        get();
    }

    @SuppressWarnings("unchecked")
    private T[] getEnums() {
        return (T[]) get().getClass().getEnumConstants();
    }

    @Override
    public boolean set(@NotNull T val) {
        for (int i = 0; i < getEnums().length; i++) {
            if (getEnums()[i].equals(val)) {
                enumOn = i;
                break;
            }
        }
        return super.set(val);
    }

    @Override
    public T fromBytesImpl(DataInputStream dis) throws IOException {
        int ordinal = dis.readByte();
        return getEnums()[ordinal];
    }

    @Override
    public void toBytesImpl(DataOutputStream dos) throws IOException {
        dos.writeByte(get().ordinal());
    }

    @Override
    public @NotNull T parse(String str) throws Exception {
        for (T e : getEnums()) {
            if (e.name().equalsIgnoreCase(str)) {
                return e;
            }
        }
        throw new SettingParseException(this, str);
    }

    @Override
    public <K> EnumSetting<T> requiresSetting(Setting<K> setting, K toBe) {
        return (EnumSetting<T>) super.requiresSetting(setting, toBe);
    }

    @Override
    public EnumSetting<T> onSet(OnSet o) {
        return (EnumSetting<T>) super.onSet(o);
    }

    public void prev() {
        enumOn--;
        T[] consts = getEnums();
        if (enumOn < 0) {
            enumOn = consts.length - 1;
        }

        set(consts[enumOn]);
        get();
    }
}
