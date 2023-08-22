package me.ltsoveranakin.ghoulish.client.storage.config.config;

import java.io.DataOutputStream;

public interface StorageCategory {
    void write(DataOutputStream dos) throws Exception;
}
