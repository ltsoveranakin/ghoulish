package me.ltsoveranakin.ghoulish.client.interfaces.state;

import java.io.*;

public interface ManagedSerializableData {
    void writeData(DataOutputStream dos) throws IOException;

    void readData(DataInputStream dis) throws IOException;

}
