package org.hptd.format;

import com.google.common.io.ByteArrayDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * a date input wrapper with ByteBuffer
 *
 * @author ford
 * @since 1.0.2
 */
public class ByteBufferDataInput extends BaseByteArrayDataInput {
    private static Logger logger = LoggerFactory.getLogger(ByteBufferDataInput.class);
    private final ByteBuffer byteBuffer;

    public ByteBufferDataInput(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    @Override
    public void readFully(byte[] bytes, int off, int len) {
        byteBuffer.get(bytes, off, len);
    }

    @Override
    public int skipBytes(int i) {
        for (int k = 0; k < i; k++) {
            byteBuffer.get();
        }
        return 0;
    }

    @Override
    public boolean readBoolean() {
        return byteBuffer.get() == 0;
    }

    @Override
    public byte readByte() {
        return byteBuffer.get();
    }


    @Override
    public short readShort() {
        return byteBuffer.getShort();
    }

    @Override
    public char readChar() {
        return byteBuffer.getChar();
    }

    @Override
    public int readInt() {
        return byteBuffer.getInt();
    }

    @Override
    public long readLong() {
        return byteBuffer.getLong();
    }

    @Override
    public float readFloat() {
        return byteBuffer.getFloat();
    }

    @Override
    public double readDouble() {
        return byteBuffer.getDouble();
    }

    @Override
    public String readUTF() {
        short strLen = byteBuffer.getShort();
        byte[] strBytes = new byte[strLen];
        byteBuffer.get(strBytes);
        try {
            return new String(strBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(" wrong encoding with bytes:" + Arrays.asList(strBytes), e);
            return new String(strBytes);
        }
    }
}
