package me.ltsoveranakin.ghoulish.client.storage;

import me.ltsoveranakin.ghoulish.client.storage.config.ConfigFile;

import java.io.*;

public class StorageHandler {
    public static final File GHOULISH_PATH = new File("./ghoulish/");
    public static final File GHOULISH_CONFIG_PATH = new File(GHOULISH_PATH.getPath() + "/config/");
    public static final File GHOULISH_DATA_FILE = new File(GHOULISH_PATH.getPath() + "/ghoulish.glsh");

    public static final StorageData STORAGE_DATA = new StorageData();

    public static void saveConfig() {
        try {
            ConfigFile.CURRENT_CONFIG.save();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveStorageData() {
        try {
            STORAGE_DATA.save();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() {
        GHOULISH_CONFIG_PATH.mkdirs();
        try {
            STORAGE_DATA.load();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadNewConfig(String name) {
        StorageHandler.STORAGE_DATA.currentConfig = name;
        StorageHandler.saveStorageData();

        ConfigFile.CURRENT_CONFIG = new ConfigFile(name);
    }

    /**
     * [ordinal(byte), version(byte)]
     */

    public static void writeHeader(DataOutputStream dos, StorageType storageType) throws Exception {
        dos.writeByte(storageType.ordinal());
        dos.writeByte(storageType.getVers());
    }

    public static FileHeader readHeader(DataInputStream dis) throws Exception {
        return new FileHeader(StorageType.values()[dis.readByte()], dis.readByte());
    }
}
