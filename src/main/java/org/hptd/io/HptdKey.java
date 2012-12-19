package org.hptd.io;

import org.hptd.utils.ByteBufferUtil;

import java.nio.ByteBuffer;

/**
 * the key store in the storage (leveldb)
 *
 * @author ford
 */
public class HptdKey {
    private final long innerId;
    private final long datetime;

    /**
     * @param innerId  hptd inner id
     * @param datetime datetime
     */
    public HptdKey(long innerId, long datetime) {
        this.innerId = innerId;
        this.datetime = datetime;
    }

    public long getInnerId() {
        return innerId;
    }

    public long getDatetime() {
        return datetime;
    }

    public byte[] toBytes() {
        return getBytes(innerId, datetime);
    }

    public static byte[] getBytes(long innerId, long datetime) {
        ByteBuffer buffer = ByteBufferUtil.bigEndianAllocate(16);
        buffer.putLong(innerId).putLong(datetime);
        return buffer.array();
    }
}
