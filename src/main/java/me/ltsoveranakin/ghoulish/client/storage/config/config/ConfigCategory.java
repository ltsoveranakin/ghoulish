package me.ltsoveranakin.ghoulish.client.storage.config.config;

import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.features.modules.module.Module;
import me.ltsoveranakin.ghoulish.client.interfaces.state.ManagedSerializableData;
import me.ltsoveranakin.ghoulish.client.misc.Pos;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Bytes: [...categoryOrdinal(byte), ...xPosition(short), ...yPosition(short), ...expanded(bool), ...moduleCount(byte), ...modules(ConfigModule[])]
 * [0, 500, 500, true, 10, [10]]
 */

public class ConfigCategory implements ManagedSerializableData {
    private Category category;
    private final Map<Module, ConfigModule> configModules = new HashMap<>();
    private int x;
    private int y;
    private boolean expanded = false;

    public ConfigCategory() {
        this(0, 0);
    }

    public ConfigCategory(int x, int y) {
        this.x = x;
        this.y = y;
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

    public void setCategory(Category category) {
        this.category = category;
    }

    public Map<Module, ConfigModule> getConfigModules() {
        return configModules;
    }

    @Override
    public String toString() {
        return "ConfigCategory{" +
                "category=" + category +
                ", configMods=" + configModules +
                ", x=" + x +
                ", y=" + y +
                ", expanded=" + expanded +
                '}';
    }

    @Override
    public void writeData(DataOutputStream dos) throws IOException {
        System.out.println("WRITE CATEGORY, MODULES SIZE: " + configModules.size());
        dos.writeByte(category.ordinal());
        dos.writeShort(x);
        dos.writeShort(y);
        dos.writeBoolean(expanded);
        dos.writeByte(configModules.size());

        for (ConfigModule cfgMod : configModules.values()) {
            cfgMod.writeData(dos);
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
            ConfigModule cfgMod = new ConfigModule();
            cfgMod.readData(dis);
            configModules.put(cfgMod.getModule(), cfgMod);
        }
    }

    @Override
    public void init() {
        System.out.println("INIT CATEGORY: " + category + " MODULE  AMOUNT: " + category.getModules().size());
        for (Module module : category.getModules()) {
            ConfigModule configModule = new ConfigModule();
            configModule.setModule(module);
            configModule.setDefaultDataImpl();
            configModules.put(module, configModule);
            configModule.init();
        }
    }

    @Override
    public void setDefaultDataImpl() {
        // no default data
    }
}
