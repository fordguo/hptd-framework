package org.hptd.format;

import org.hptd.utils.ByteBufferUtil;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Change me
 *
 * @author ford
 */
public class ChunkDataTest {
    @Test
    public void testToBuffer() throws Exception {
        ArrayList<DataValue> d = new ArrayList<DataValue>(4);
        d.add(new DataValue(ValueType.INT, 1999));
        ChunkData data = new ChunkData(d);
        ByteBuffer buffer = data.toBuffer();
        assertEquals(1, buffer.getShort());
        byte[] cols = new byte[1];
        buffer.get(cols);
        assertEquals(new byte[]{ValueType.INT.combine(null)}, cols);
        assertEquals(1999, buffer.getInt());

        d.add(new DataValue(ValueType.LONG, 8888888l));
        d.add(new DataValue(ValueType.DOUBLE, 3.0));
        d.add(new DataValue(ValueType.STRING, "1234567890才"));
        data = new ChunkData(d);
        buffer = data.toBuffer();
        assertEquals(2, buffer.getShort());
        cols = new byte[2];
        buffer.get(cols);
        assertEquals(new byte[]{ValueType.INT.combine(ValueType.LONG),
                ValueType.DOUBLE.combine(ValueType.STRING)}, cols);
        assertEquals(1999, buffer.getInt());
        assertEquals(8888888l, buffer.getLong());
        assertEquals(3.0, buffer.getDouble());
        assertEquals(13, buffer.getShort());
        byte[] tmpBytes = new byte[13];
        buffer.get(tmpBytes);
        assertEquals("1234567890才", new String(tmpBytes, "UTF-8"));//中文字符占3个字节


        d.add(new DataValue(ValueType.BYTE, 10));
        data = new ChunkData(d);
        buffer = data.toBuffer();
        assertEquals(3, buffer.getShort());
        cols = new byte[3];
        buffer.get(cols);
        assertEquals(new byte[]{ValueType.INT.combine(ValueType.LONG),
                ValueType.DOUBLE.combine(ValueType.STRING), ValueType.BYTE.combine(null)}, cols);
        //2+3  4+8+8+15
        assertEquals(10, buffer.get(40));
        d.add(new DataValue(ValueType.TIMESTAMP, 654321));
        data = new ChunkData(d);
        buffer = data.toBuffer();
        assertEquals(3, buffer.getShort());
        cols = new byte[3];
        buffer.get(cols);
        assertEquals(new byte[]{ValueType.INT.combine(ValueType.LONG),
                ValueType.DOUBLE.combine(ValueType.STRING), ValueType.BYTE.combine(ValueType.TIMESTAMP)}, cols);
        //2+3  4+8+8+15+1
        assertEquals(654321, buffer.getLong(41));
    }

    @Test
    public void testValueOf() throws Exception {
        ByteBuffer buffer = ByteBufferUtil.bigEndianAllocate(100);
        buffer.putShort((short) 2);
        byte[] types = new byte[]{ValueType.INT.combine(ValueType.LONG),
                ValueType.DOUBLE.combine(null)};
        buffer.put(types);
        buffer.putInt(10).putLong(8888l).putDouble(3.0);
        buffer.flip();
        ChunkData data = ChunkData.valueOf(buffer);
        assertNotNull(data);
        assertEquals(3, data.getDatas().size());
        assertEquals(10, data.getDatas().get(0).intValue());
        assertEquals(ValueType.LONG, data.getDatas().get(1).getType());
        assertEquals(ValueType.DOUBLE, data.getDatas().get(2).getType());

        buffer.clear();
        buffer.putShort((short) 2);
        types = new byte[]{ValueType.INT.combine(ValueType.LONG),
                ValueType.DOUBLE.combine(ValueType.STRING)};
        buffer.put(types);
        buffer.putInt(10).putLong(8888l).putDouble(3.0);
        buffer.putShort((short) 4).put("abcd".getBytes());
        buffer.flip();
        data = ChunkData.valueOf(buffer);
        assertEquals(4, data.getDatas().size());
        assertEquals(ValueType.STRING, data.getDatas().get(3).getType());
        assertEquals("abcd", data.getDatas().get(3).stringValue());

    }
}
