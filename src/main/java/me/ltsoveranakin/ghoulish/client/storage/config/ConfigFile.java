package me.ltsoveranakin.ghoulish.client.storage.config;

import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.storage.*;
import me.ltsoveranakin.ghoulish.client.storage.config.config.ConfigCategory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigFile extends SaveData {
    public static ConfigFile CURRENT_CONFIG;
    private final Map<Category, ConfigCategory> cats = new HashMap<>();
    private String configName;

    private boolean errCreating = false;

    /**
     * Loads a config, and handles invalid config by deleting file and creating a backup
     *
     * @param name
     */

    public ConfigFile(String name) {
        super(getFile(name));
        configName = name;
    }

    /**
     * Boolean value of error creating config. If this returns true, the config file was deleted and this object should be freed.
     *
     * @return true if and only if the config was loaded with an error.
     */

    public boolean hasErrorCreating() {
        return errCreating;
    }

    public static File getFile(String name) {
        return new File(StorageHandler.GHOULISH_CONFIG_PATH.getPath() + "/" + name + ".glsh");
    }

    @Override
    protected void setDefaultData() {
        int x = 40;
        for (Category category : Category.values()) {
            cats.put(category, new ConfigCategory(x, 40, category));
            x += 50;
        }
    }

    @Override
    public void writeData(DataOutputStream dos) throws IOException {
        StorageHandler.writeHeader(dos, StorageType.CONFIG);
        dos.writeUTF(configName);
        dos.writeByte(cats.size());

        for (ConfigCategory configCategory : cats.values()) {
            configCategory.writeData(dos);
        }
    }

    @Override
    public void readData(DataInputStream dis) throws IOException {
        cats.clear();

        FileHeader header = StorageHandler.readHeader(dis);
        String tmpName = dis.readUTF();
        byte categoryAmount = dis.readByte();

        if (!tmpName.equals(configName)) {
            errCreating = true;
            throw new IOException("Config name does not match");
        }

        for (int i = 0; i < categoryAmount; i++) {
            ConfigCategory configCategory = new ConfigCategory();
            configCategory.readData(dis);
            cats.put(configCategory.getCategory(), configCategory);
        }
    }

    public Map<Category, ConfigCategory> getCats() {
        return cats;
    }

    public static boolean doesExist(String name) {
        return getFile(name).exists();
    }
}
