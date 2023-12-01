package me.ltsoveranakin.ghoulish.client.storage;

import me.ltsoveranakin.ghoulish.client.storage.config.ConfigFile;

import java.io.*;

public class StorageHandler {
    public static final File GHOULISH_PATH = new File("./ghoulish/");
    public static final File GHOULISH_CONFIG_PATH = new File(GHOULISH_PATH.getPath() + "/config/");
    public static final File GHOULISH_DATA_FILE = new File(GHOULISH_PATH.getPath() + "/ghoulish.glsh");

    public static final GhoulishSaveData STORAGE_DATA = new GhoulishSaveData();

    public static void init() {

    }

    public static void saveConfig() {
        if (ConfigFile.CURRENT_CONFIG == null) {
            loadNewConfig("default");
        }
        ConfigFile.CURRENT_CONFIG.write();
    }

    public static void saveStorageData() {
        try {
            STORAGE_DATA.write();
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

    public static void writeHeader(DataOutputStream dos, StorageType storageType) throws IOException {
        dos.writeByte(storageType.ordinal());
        dos.writeByte(storageType.getVers());
    }

    public static FileHeader readHeader(DataInputStream dis) throws IOException {
        return new FileHeader(StorageType.values()[dis.readByte()], dis.readByte());
    }
}
