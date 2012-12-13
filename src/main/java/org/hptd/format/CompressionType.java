package org.hptd.format;

/**
 * the string or byte array contents compression type.
 *
 * @author Ford
 * @since 1.0
 */
public enum CompressionType {
    NONE, LZF, ZLIB, GZIP;

    /**
     * get the compression type from byte.
     *
     * @param value a byte type
     * @return CompressionType, if not match return the default NONE
     */
    public static CompressionType valueOf(byte value) {
        for (CompressionType type : CompressionType.values()) {
            if (value == type.ordinal()) {
                return type;
            }
        }
        return NONE;
    }
}
