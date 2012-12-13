package org.hptd.format;

import org.hptd.utils.ByteBufferUtil;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;

import static org.testng.Assert.assertEquals;

/**
 * Change me
 *
 * @author ford
 */
public class HptdHeaderTest {
    @Test
    public void testGetHdrBuffer() throws Exception {
        ByteBuffer buffer = ByteBufferUtil.bigEndianAllocate(HptdHeader.HDR_LENGTH);
        buffer.put(HptdHeader.SIGNATURE);
        buffer.put(new byte[]{1, 0, (byte) CompressionType.LZF.ordinal(), HptdBuffer.RESERVED, HptdBuffer.RESERVED,
                HptdBuffer.RESERVED, HptdBuffer.RESERVED, HptdBuffer.RESERVED});
        buffer.flip();
        HptdHeader hdr = new HptdHeader();
        assertEquals(buffer, hdr.toBuffer());
    }
}
