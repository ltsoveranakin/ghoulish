package me.ltsoveranakin.ghoulish.client.storage.config;

import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.storage.*;
import me.ltsoveranakin.ghoulish.client.storage.config.config.ConfigCategory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigFile extends SaveData {
    public static ConfigFile CURRENT_CONFIG;
    private Map<Category, ConfigCategory> configCategories;
    private String configName;

    /**
     * Loads a config, and handles invalid config by deleting file and creating a backup
     *
     * @param name
     */

    public ConfigFile(String name) {
        super(getFilePath(name));
        configName = name;
        System.out.println("CONSTRUCT CONFIG FILE!");
    }

    public static File getFilePath(String name) {
        return new File(StorageHandler.GHOULISH_CONFIG_PATH.getPath() + "/" + name + ".glsh");
    }

    @Override
    public void setDefaultDataImpl() {
        System.out.println("Set config default data");
        configName = "default";
    }

    @Override
    public void writeData(DataOutputStream dos) throws IOException {
        System.out.println("WRITE CONFIG, SIZE: " + configCategories.size());
        StorageHandler.writeHeader(dos, StorageType.CONFIG);
        dos.writeUTF(configName);
        dos.writeByte(configCategories.size());

        for (ConfigCategory configCategory : configCategories.values()) {
            configCategory.writeData(dos);
            System.out.println("WRITE " + configCategory.getCategory());
        }
    }

    @Override
    public void readData(DataInputStream dis) throws IOException {
        configCategories.clear();

        FileHeader header = StorageHandler.readHeader(dis);
        String tmpName = dis.readUTF();
        byte categoryAmount = dis.readByte();

        if (!tmpName.equals(configName)) {
            throw new IOException("Config name does not match");
        }

        for (int i = 0; i < categoryAmount; i++) {
            ConfigCategory configCategory = new ConfigCategory();
            configCategory.readData(dis);
            configCategories.put(configCategory.getCategory(), configCategory);
        }
    }

    public Map<Category, ConfigCategory> getConfigCategories() {
        return configCategories;
    }

    public static boolean doesExist(String name) {
        return getFilePath(name).exists();
    }

    @Override
    public void init() {
        configCategories = new HashMap<>();
        configName = "default";

        int x = 40;
        for (Category category : Category.values()) {
            ConfigCategory configCategory = new ConfigCategory(x, 40);
            configCategory.setCategory(category);
            System.out.println("CONFIG CATEGORIES: " + configCategories);
            configCategories.put(category, configCategory);
            configCategory.init();
            x += 50;
        }
    }
}
