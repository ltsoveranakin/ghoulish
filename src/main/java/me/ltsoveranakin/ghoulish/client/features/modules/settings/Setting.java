package me.ltsoveranakin.ghoulish.client.features.modules.settings;

import me.ltsoveranakin.ghoulish.client.features.modules.settings.settings.SettingParseException;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.misc.named.NamedDesc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;

public abstract class Setting<T> extends NamedDesc {
    private final Module mod;
    private final EnumSettingType enumSettingType;
    private T val;

    @Nullable
    private List<ReqCB> requirements;
    private List<OnSet> onSetRuns;

    public Setting(String name, String desc, T defVal, Module mod, EnumSettingType es) {
        super(name, desc);
        val = defVal;
        this.mod = mod;

        enumSettingType = es;

        if (name != null && name.contains("-")) {
            throw new RuntimeException("Illegal character '-' in setting " + name);
        }
    }

    public final void fromBytes(DataInputStream dis) throws IOException, SettingParseException {

        System.out.println("MODULE -> " + getMod() + "; SETTING -> " + getName() + "; TYPE -> " + getSettingType());

        T beforeVal = get();

        set(fromBytesImpl(dis));
    }

    public Module getMod() {
        return mod;
    }

    public EnumSettingType getSettingType() {
        return enumSettingType;
    }

    public T get() {
        return val;
    }

    public boolean set(@NotNull T val) {
        if (val == null) {
            return false;
        }

        forceSet(val);
        return true;
    }

    /**
     * Return the setting value from the DataInputStream. This is not to set the value.
     *
     * @param dis DataInputStream to read from
     * @return Value that is represented by the bytes in DataInoutStream
     * @throws IOException Throws IOException for reading of the DataInputStream
     */
    protected abstract T fromBytesImpl(DataInputStream dis) throws IOException;

    public void forceSet(T val) {
        T tmpVal = this.val;
        this.val = val;

        if (onSetRuns != null) {
            for (OnSet o : onSetRuns) {
                if (!o.onSet()) {
                    this.val = tmpVal;
                    return;
                }
            }
        }
    }

    public final void toBytes(DataOutputStream dos) throws IOException {
        dos.writeByte(enumSettingType.ordinal());
        toBytesImpl(dos);
    }

    protected abstract void toBytesImpl(DataOutputStream dos) throws IOException;

    public boolean set(String str) throws Exception {
        return set(parse(str));
    }

    public abstract @NotNull T parse(String str) throws Exception;

    public <K> Setting<T> requiresSetting(Setting<K> setting, K toBe) {
        return requires(() -> setting.equals(toBe));
    }

    public Setting<T> requires(ReqCB rcb) {
        if (requirements == null) requirements = new ArrayList<>();
        requirements.add(rcb);
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return get().equals(obj);
    }

    @Override
    public String toString() {
        return get().toString();
    }

    public @Nullable List<ReqCB> getRequirements() {
        return requirements;
    }

    public Setting<T> onSet(OnSet o) {
        if (onSetRuns == null) {
            onSetRuns = new ArrayList<>();
        }

        onSetRuns.add(o);
        return this;
    }

    public interface ReqCB {
        boolean test();
    }

    @FunctionalInterface
    public interface OnSet {
        /**
         * Called after the value has been set, return false to revert to the old value, true to remain constant
         *
         * @return false if the value should be reverted, otherwise true
         */
        boolean onSet();
    }
}
