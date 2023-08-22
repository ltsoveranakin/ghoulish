package me.ltsoveranakin.ghoulish.client.storage;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes all the bytes to a resizeable buffer
 */
public class BufferOutputStream extends OutputStream {
    private final List<Integer> buffer = new ArrayList<>();

    @Override
    public void write(int b) throws IOException {
        buffer.add(b);
    }

    public byte[] getBytes() {
        byte[] bytes = new byte[buffer.size()];
        for (int i = 0; i < buffer.size(); i++) {
            bytes[i] = buffer.get(i).byteValue();
        }
        return bytes;
    }
}
