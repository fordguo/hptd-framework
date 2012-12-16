package org.hptd.format;

import java.nio.ByteBuffer;

/**
 * the hptd use the ByteBuffer to operate the data files.
 *
 * @author ford
 */
public interface HptdByteBuffer {

    public static final byte RESERVED = -1;

    /**
     * get the HPTD buffer block
     *
     * @return ByteBuffer
     */
    ByteBuffer toBuffer();

}
