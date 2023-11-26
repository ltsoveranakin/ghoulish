package me.ltsoveranakin.ghoulish.client.storage.config.config;

import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.interfaces.state.ManagedSerializableData;
import me.ltsoveranakin.ghoulish.client.interfaces.state.SerializableData;
import me.ltsoveranakin.ghoulish.client.misc.Pos;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Bytes: [...categoryOrdinal(byte), ...xPosition(short), ...yPosition(short), ...expanded(bool), ...moduleCount(byte), ...modules(ConfigModule[])]
 */

public class ConfigCategory implements ManagedSerializableData {
    private Category category;
    private final Map<Module, ConfigModule> configMods = new HashMap<>();
    private int x;
    private int y;
    private boolean expanded = false;

    public ConfigCategory() {
    }

    public ConfigCategory(int x, int y, Category category) {
        this();

        this.x = x;
        this.y = y;
        this.category = category;
    }

    public void setPos(Pos pos) {
        setX(pos.getX());
        setY(pos.getY());
    }

    public String getName() {
        return category.name();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Category getCategory() {
        return category;
    }

    public Map<Module, ConfigModule> getConfigMods() {
        return configMods;
    }

    @Override
    public String toString() {
        return "ConfigCategory{" +
                "category=" + category +
                ", configMods=" + configMods +
                ", x=" + x +
                ", y=" + y +
                ", expanded=" + expanded +
                '}';
    }

    @Override
    public void writeData(DataOutputStream dos) throws IOException {
        dos.writeByte(category.ordinal());
        dos.writeShort(x);
        dos.writeShort(y);
        dos.writeBoolean(expanded);
        dos.writeByte(configMods.size());

        for (ConfigModule cfgMod : configMods.values()) {
            cfgMod.write(dos);
        }
    }

    @Override
    public void readData(DataInputStream dis) throws IOException {
        byte categoryOrdinal = dis.readByte();
        x = dis.readShort();
        y = dis.readShort();
        expanded = dis.readBoolean();
        byte moduleCount = dis.readByte();

        category = Category.values()[categoryOrdinal];

        for (int i = 0; i < moduleCount; i++) {
            ConfigModule cfgMod = new ConfigModule(dis);
            configMods.put(cfgMod.getModule(), cfgMod);
        }
    }
}
