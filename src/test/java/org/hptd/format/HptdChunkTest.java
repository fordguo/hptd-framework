package org.hptd.format;

import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;

/**
 * Change me
 *
 * @author ford
 */
public class HptdChunkTest {
    @Test
    public void testToBuffer() throws Exception {
        long cur = System.currentTimeMillis();
        ArrayList d = new ArrayList(1);
        d.add(new DataValue(ValueType.INT, 1999));
        ChunkData data = new ChunkData(d);
        HptdChunk chunk = new HptdChunk(cur, data);
        ByteBuffer buffer = chunk.toBuffer();
        assertEquals(7, buffer.getInt());//7=2(类型列长度)+1(类型列)+4(数据内容)
        assertEquals(cur, buffer.getLong());

    }
}
