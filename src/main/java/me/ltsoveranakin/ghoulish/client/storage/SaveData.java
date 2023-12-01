package me.ltsoveranakin.ghoulish.client.storage;

import me.ltsoveranakin.ghoulish.client.interfaces.state.ManagedSerializableData;
import me.ltsoveranakin.ghoulish.client.interfaces.state.SerializableData;

import java.io.*;
import java.nio.file.Files;

public abstract class SaveData implements SerializableData, ManagedSerializableData {
    private final File saveFile;

    public SaveData(File saveFile) {
        this.saveFile = saveFile;
        init();
        if (!createIfAbsent()) {
            read();
        }
    }

    @Override
    public final void write() {
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
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

    /**
     * Returns true if the file was created, otherwise false
     *
     * @return
     */

    private boolean createIfAbsent() {
        System.out.println("Create if absent call");
        try {
            if (saveFile.getParentFile().mkdirs() || saveFile.createNewFile()) {
                System.out.println("it is absent");
                setDefaultData();
                write();
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public final void read() {
        try {
            createIfAbsent();

            FileInputStream fis = new FileInputStream(saveFile);
            DataInputStream dis = new DataInputStream(fis);

            try {
                readData(dis);
            } catch (Exception e2) {

                String newPath = saveFile.getPath() + "." + System.currentTimeMillis() + ".invalid";
                File invalidConfigFile = new File(newPath);
                invalidConfigFile.createNewFile();

                try {
                    Files.copy(saveFile.toPath(), invalidConfigFile.toPath());
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }

                setDefaultData();
                write();
            } finally {
                dis.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        postSetData();
    }

    public void setDefaultData() {
        setDefaultDataImpl();
        postSetData();
    }

    public File getSaveFile() {
        return saveFile;
    }

    protected void postSetData() {
    }
}
