package org.hptd.format;

import org.hptd.utils.ByteBufferUtil;

import java.nio.ByteBuffer;

/**
 * the HPTD chunk information,there are 3 components:
 * <ul>
 * <li>datas length(4 bytes)</li>
 * <li>datetime(8 bytes)</li>
 * <li>chunk datas (variable length)</li>
 * </ul>
 *
 * @author ford
 * @since 1.0
 */
public class HptdChunk implements HptdBuffer {
    public static final int FIX_LENGTH = 12;
    private final long datetime;
    private final ChunkData datas;

    public HptdChunk(long datetime, ChunkData datas) {
        this.datetime = datetime;
        this.datas = datas;
    }

    public long getDatetime() {
        return datetime;
    }

    public ChunkData getDatas() {
        return datas;
    }

    @Override
    public final ByteBuffer toBuffer() {
        byte[] byteDatas = datas.toBuffer().array();
        ByteBuffer all = ByteBufferUtil.bigEndianAllocate(FIX_LENGTH + byteDatas.length);
        all.putInt(byteDatas.length).putLong(datetime);
        all.put(byteDatas);
        all.flip();
        return all;
    }

}
