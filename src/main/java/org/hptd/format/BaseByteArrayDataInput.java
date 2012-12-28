package org.hptd.format;

import com.google.common.io.ByteArrayDataInput;

/**
 * base byte array data input
 *
 * @author ford
 */
public abstract class BaseByteArrayDataInput implements ByteArrayDataInput {
    public static final int MAX = 0xFFFF;

    @Override
    public void readFully(byte[] bytes) {
        readFully(bytes, 0, bytes.length);
    }

    @Override
    public int readUnsignedByte() {
        return readByte() & MAX;
    }

    @Override
    public int readUnsignedShort() {
        return readShort() & MAX;
    }

    @Override
    public String readLine() {
        throw new RuntimeException("NotImplemented!");
    }

    @Override
    public String readUTF() {
        throw new RuntimeException("NotImplemented!");
    }
}
