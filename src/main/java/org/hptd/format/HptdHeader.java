package org.hptd.format;

import org.hptd.utils.ByteBufferUtil;

import java.nio.ByteBuffer;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * the HPTD header information,there are 16 bytes contents,its structure is :
 * <ul>
 * <li>signature(8 bytes)</li>
 * <li>major version(1 byte)</li>
 * <li>minor version(1 byte)</li>
 * <li>compression type(1 byte)</li>
 * <li>chunk records(4 bytes)</li>
 * <li>reserved(1 byte)</li>
 * </ul>
 *
 * @author Ford
 * @since 1.0
 */
public class HptdHeader implements HptdBuffer {
    private static final byte[] HDR_RESERVED = new byte[]{HptdBuffer.RESERVED, HptdBuffer.RESERVED, HptdBuffer.RESERVED,
            HptdBuffer.RESERVED, HptdBuffer.RESERVED};

    public static final byte[] SIGNATURE = new byte[]{(byte) 0x89, 0x48, 0x50, 0x54, 0x44, 0x0A, 0x1A, 0x0A};
    private static final int SIGNATURE_LENGTH = SIGNATURE.length;

    public static final int HDR_LENGTH = 8 + SIGNATURE_LENGTH;

    private final byte majorVersion;
    private final byte minorVersion;
    private final CompressionType compressionType;

    public HptdHeader() {
        this(CompressionType.LZF);
    }

    public HptdHeader(CompressionType compressionType) {
        this((byte) 1, (byte) 0, compressionType);
    }

    /**
     * constructor the header with major/minor version and compression type
     *
     * @param majorVersion    major version
     * @param minorVersion    minor version
     * @param compressionType compression type
     */
    public HptdHeader(byte majorVersion, byte minorVersion, CompressionType compressionType) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.compressionType = compressionType;
    }

    /**
     * @return major version
     */
    public byte getMajorVersion() {
        return majorVersion;
    }

    /**
     * @return minor version
     */
    public byte getMinorVersion() {
        return minorVersion;
    }

    /**
     * @return compression type
     */
    public CompressionType getCompressionType() {
        return compressionType;
    }

    @Override
    public final ByteBuffer toBuffer() {
        ByteBuffer all = ByteBufferUtil.bigEndianAllocate(HDR_LENGTH);
        all.put(SIGNATURE);
        all.put(getMajorVersion()).put(getMinorVersion());
        all.put((byte) getCompressionType().ordinal()).put(HDR_RESERVED);
        all.flip();
        return all;
    }

    public static HptdHeader valueOf(ByteBuffer buffer) {
        checkArgument(HDR_LENGTH != buffer.capacity(), "the header datas length should be " + HDR_LENGTH);
        buffer.getLong();
        return new HptdHeader(buffer.get(), buffer.get(), CompressionType.valueOf(buffer.get()));
    }
}
