package me.ltsoveranakin.ghoulish.client.storage.config;

import me.ltsoveranakin.ghoulish.client.features.modules.Category;
import me.ltsoveranakin.ghoulish.client.interfaces.Loadable;
import me.ltsoveranakin.ghoulish.client.interfaces.Saveable;
import me.ltsoveranakin.ghoulish.client.storage.*;
import me.ltsoveranakin.ghoulish.client.storage.config.config.ConfigCategory;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class ConfigFile implements Saveable, Loadable {
    public static ConfigFile CURRENT_CONFIG;
    private final File file;
    private final Map<Category, ConfigCategory> cats = new HashMap<>();
    private String configName;

    private boolean errCreating = false;

    /**
     * Loads a config, and handles invalid config by deleting file and creating a backup
     * @param name
     */

    public ConfigFile(String name) {
        this.file = getFile(name);
        configName = name;


        try {
            int x = 40;
            if (file.createNewFile()) {
                for (Category category : Category.values()) {
                    cats.put(category, new ConfigCategory(x, 40, category));
                    x += 50;
                }
                System.out.println("SAVING CONFIG");
                save();
            } else {
                System.out.println("LOADING CONFIG");
                load();
            }

        } catch (Exception e) {
            errCreating = true;
            File invalidConfigFile = getFile(name, true);
            while (invalidConfigFile.exists()) {
                invalidConfigFile = getFile(name, true);
            }

            try {
                Files.copy(file.toPath(), invalidConfigFile.toPath());
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            file.delete();
        }
    }

    /**
     * Boolean value of error creating config. If this returns true, the config file was deleted and this object should be freed.
     * @return true if and only if the config was loaded with an error.
     */

    public boolean hasErrorCreating() {
        return errCreating;
    }

    public static File getFile(String name, boolean invalid) {
        String invalidExtension = "." + new Date().hashCode() + ".invalid";
        String extension = ".glsh" + (invalid ? invalidExtension : "");
        return new File(StorageHandler.GHOULISH_CONFIG_PATH.getPath() + "/" + name + extension);
    }

    public static File getFile(String name) {
        return getFile(name, false);
    }

    @Override
    public void save() throws Exception {
        BufferOutputStream bos = new BufferOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        StorageHandler.writeHeader(dos, StorageType.CONFIG);
        dos.writeUTF(configName);
        dos.writeByte(cats.size());

        for (ConfigCategory configCategory : cats.values()) {
            configCategory.write(dos);
        }

        dos.flush();
        dos.close();

        byte[] buf = bos.getBytes();
        Files.write(file.toPath(), buf);
    }

    /**
     * Loads the current config
     */

    @Override
    public void load() throws Exception {
        cats.clear();

        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);


        FileHeader header = StorageHandler.readHeader(dis);
        configName = dis.readUTF();
        byte categoryAmount = dis.readByte();

        for (int i = 0; i < categoryAmount; i++) {
            try {
                ConfigCategory configCategory = new ConfigCategory(dis);
                cats.put(configCategory.getCategory(), configCategory);
            } catch (Exception e) {
                System.out.println("ERROR LOADING CONFIG ... ABORTING");
                dis.close();
                throw new Exception(e);
            }
        }

        dis.close(); // auto closes the FileInputStream
    }

    public boolean delete() {
        return file.delete();
    }

    public static boolean doesExist(String name) {
        return getFile(name).exists();
    }

    public Map<Category, ConfigCategory> getCats() {
        return cats;
    }
}
