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
        assertEquals(new byte[]{(byte) (ValueType.INT.byteValue() << 4 + 0xF0)}, cols);
        assertEquals(1999, buffer.getInt());

        d.add(new DataValue(ValueType.LONG, 8888888l));
        d.add(new DataValue(ValueType.DOUBLE, 3.0));
        d.add(new DataValue(ValueType.STRING, "1234567890才"));
        data = new ChunkData(d);
        buffer = data.toBuffer();
        assertEquals(2, buffer.getShort());
        cols = new byte[2];
        buffer.get(cols);
        assertEquals(new byte[]{(byte) (ValueType.INT.byteValue() << 4 + ValueType.LONG.byteValue()),
                (byte) (ValueType.DOUBLE.byteValue() << 4 + ValueType.STRING.byteValue())}, cols);
        assertEquals(1999, buffer.getInt());
        assertEquals(8888888l, buffer.getLong());
        assertEquals(3.0, buffer.getDouble());
        assertEquals(13, buffer.getShort());
        byte[] tmpBytes = new byte[13];
        buffer.get(tmpBytes);
        assertEquals("1234567890才", new String(tmpBytes,"UTF-8"));//中文字符占3个字节

        d.add(new DataValue(ValueType.BYTE, 10));
        data = new ChunkData(d);
        buffer = data.toBuffer();
        assertEquals(3, buffer.getShort());
        cols = new byte[3];
        buffer.get(cols);
        assertEquals(new byte[]{(byte) (ValueType.INT.byteValue() << 4 + ValueType.LONG.byteValue()),
                (byte) (ValueType.DOUBLE.byteValue() << 4 + ValueType.STRING.byteValue()),
                (byte) (ValueType.BYTE.byteValue() << 4 + 0x0F)}, cols);
        //2+3  4+8+8+15
        assertEquals(10, buffer.get(40));
    }
}
