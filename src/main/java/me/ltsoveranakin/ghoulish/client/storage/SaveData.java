package me.ltsoveranakin.ghoulish.client.storage;

import me.ltsoveranakin.ghoulish.client.interfaces.state.ManagedSerializableData;
import me.ltsoveranakin.ghoulish.client.interfaces.state.SerializableData;

import java.io.*;
import java.nio.file.Files;

public abstract class SaveData implements SerializableData, ManagedSerializableData {
    private final File saveFile;

    public SaveData(File saveFile) {
        this.saveFile = saveFile;
    }

    public void write() {
        try {
            BufferOutputStream fos = new BufferOutputStream();
            DataOutputStream dos = new DataOutputStream(fos);

            try {
                writeData(dos);
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            } finally {
                dos.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void read() {
        try {
            if (saveFile.createNewFile()) {
                setDefaultData();
                write();
                return;
            }

            FileInputStream fis = new FileInputStream(saveFile);
            DataInputStream dis = new DataInputStream(fis);

            try {
                readData(dis);
            } catch (IOException e2) {
                String newPath = saveFile.getPath() + "." + System.currentTimeMillis() + ".invalid";
                File invalidConfigFile = new File(newPath);

                try {
                    Files.copy(saveFile.toPath(), invalidConfigFile.toPath());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                setDefaultData();
                write();
            } finally {
                dis.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void setDefaultData();


    public File getSaveFile() {
        return saveFile;
    }
}
