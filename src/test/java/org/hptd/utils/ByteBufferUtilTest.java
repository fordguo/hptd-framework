package org.hptd.utils;

import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.testng.Assert.assertEquals;

/**
 * Change me
 *
 * @author ford
 */
public class ByteBufferUtilTest {
    @Test
    public void testBigEndianAllocate() throws Exception {
        ByteBuffer buf = ByteBufferUtil.bigEndianAllocate(10);
        assertEquals(10, buf.capacity());
        assertEquals(ByteOrder.BIG_ENDIAN, buf.order());

    }

    @Test
    public void testBigEndianWrap() throws Exception {
        ByteBuffer buf = ByteBufferUtil.bigEndianWrap(new byte[]{});
        assertEquals(0, buf.capacity());
        assertEquals(ByteOrder.BIG_ENDIAN, buf.order());
    }
}
