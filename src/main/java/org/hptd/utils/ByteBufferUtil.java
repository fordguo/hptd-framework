package org.hptd.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * the utilities of ByteBuffer
 *
 * @author ford
 * @since 1.0
 */
public class ByteBufferUtil {
    /**
     * @param capacity is the size of buffer
     * @return a bigEndian ByteBuffer
     * @see java.nio.ByteBuffer#allocate(int)
     */
    public static ByteBuffer bigEndianAllocate(int capacity) {
        ByteBuffer buf = ByteBuffer.allocate(capacity);
        buf.order(ByteOrder.BIG_ENDIAN);
        return buf;
    }

    /**
     * @param array the ByteBuffer's content
     * @return a wrapped bigEndian ByteBuffer
     * @see ByteBuffer#wrap(byte[])
     */
    public static ByteBuffer bigEndianWrap(byte[] array) {
        ByteBuffer buf = ByteBuffer.wrap(array);
        buf.order(ByteOrder.BIG_ENDIAN);
        return buf;
    }
}
