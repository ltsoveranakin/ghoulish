package me.ltsoveranakin.ghoulish.client.storage;

import me.ltsoveranakin.ghoulish.client.storage.config.ConfigFile;

import java.io.*;

/**
 * Helper class that holds data for the client such as the current config being used
 */

public class GhoulishSaveData extends SaveData {
    public String currentConfig = "default";

    public GhoulishSaveData() {
        super(StorageHandler.GHOULISH_DATA_FILE);
    }

    @Override
    public void writeData(DataOutputStream dos) throws IOException {
        System.out.println("WRITE SAVE DATA");
        StorageHandler.writeHeader(dos, StorageType.STORAGE_DATA);
        dos.writeUTF(currentConfig);
    }

    @Override
    public void readData(DataInputStream dis) throws IOException {
        StorageHandler.readHeader(dis);
        currentConfig = dis.readUTF();
    }

    @Override
    public void setDefaultDataImpl() {
        System.out.println("set default save");
        currentConfig = "default";
    }

    @Override
    public void init() {
        
    }

    @Override
    protected void postSetData() {
        ConfigFile.CURRENT_CONFIG = new ConfigFile(currentConfig);
    }
}
