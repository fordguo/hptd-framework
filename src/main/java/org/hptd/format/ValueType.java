package org.hptd.format;

/**
 * value content type,mostly it should be numbers
 *
 * @author ford
 * @since 1.0
 */
public enum ValueType {
    NULL, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, CHAR, STRING, BYTE_ARRAY;

    /**
     * get the value type from byte.
     *
     * @param value a byte type
     * @return ValueType, if not match return the default INT
     */
    public static ValueType valueOf(byte value) {
        for (ValueType type : ValueType.values()) {
            if (value == type.ordinal()) {
                return type;
            }
        }
        return INT;
    }

    public byte byteValue() {
        return (byte) this.ordinal();
    }
}
