package me.ltsoveranakin.ghoulish.client.storage;

public enum StorageType {
    STORAGE_DATA(0),
    CONFIG(0);

    private final int vers;

    StorageType(int vers) {
        this.vers = vers;
    }

    public int getVers() {
        return vers;
    }
}
