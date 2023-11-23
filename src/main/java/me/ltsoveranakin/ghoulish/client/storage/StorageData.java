package me.ltsoveranakin.ghoulish.client.storage;

import me.ltsoveranakin.ghoulish.client.interfaces.state.Loadable;
import me.ltsoveranakin.ghoulish.client.interfaces.state.Saveable;
import me.ltsoveranakin.ghoulish.client.storage.config.ConfigFile;

import java.io.*;

/**
 * Helper class that holds data for the client such as the current config being used
 */

public class StorageData implements Saveable, Loadable {
    public String currentConfig = "default";

    /**
     * Loads the current data
     *
     * @throws Exception
     */

    @Override
    public void load() throws Exception {
        if (StorageHandler.GHOULISH_DATA_FILE.createNewFile()) {
            save();
        }

        FileInputStream fis = new FileInputStream(StorageHandler.GHOULISH_DATA_FILE);
        DataInputStream dis = new DataInputStream(fis);

        StorageHandler.readHeader(dis);
        String curConf = dis.readUTF();
        System.out.println("CURRENT CONFIG: " + curConf);

        ConfigFile.CURRENT_CONFIG = new ConfigFile(curConf);
        if (ConfigFile.CURRENT_CONFIG.hasErrorCreating()) {
            ConfigFile.CURRENT_CONFIG = new ConfigFile(curConf);
            ConfigFile.CURRENT_CONFIG.delete();
        }

        dis.close(); // auto closes the FileInputStream
        System.out.println("CLOSED DIS");
    }

    @Override
    public void save() throws Exception {
        FileOutputStream fos = new FileOutputStream(StorageHandler.GHOULISH_DATA_FILE);
        DataOutputStream dos = new DataOutputStream(fos);

        StorageHandler.writeHeader(dos, StorageType.STORAGE_DATA);
        dos.writeUTF(currentConfig);

        dos.flush();
        dos.close();
        System.out.println("DONE SAVE STORAGE DAT");
    }
}
