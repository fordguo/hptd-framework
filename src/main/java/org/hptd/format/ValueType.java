package org.hptd.format;

/**
 * value content type,mostly it should be numbers
 *
 * @author ford
 * @since 1.0
 */
public enum ValueType {
    NULL('n'), BYTE('b'), SHORT('s'), INT('i'), LONG('l'), FLOAT('f'),
    DOUBLE('d'), CHAR('c'), STRING('t'), BYTE_ARRAY('a');
    private final char abbrType;

    private ValueType(char abbrType) {
        this.abbrType = abbrType;
    }

    /**
     * get the value type from byte.
     *
     * @param value a byte type
     * @return ValueType, if not match return the default INT
     */
    public static ValueType valueOf(byte value) {
        switch (value) {
            case 0:
                return NULL;
            case 1:
                return BYTE;
            case 2:
                return SHORT;
            case 3:
                return INT;
            case 4:
                return LONG;
            case 5:
                return FLOAT;
            case 6:
                return DOUBLE;
            case 7:
                return CHAR;
            case 8:
                return STRING;
            case 9:
                return BYTE_ARRAY;
            default:
                return INT;
        }
    }

    /**
     * get the value type from char.
     *
     * @param chValue a char type
     * @return ValueType, if not match return the default INT
     */
    public static ValueType valueOf(char chValue) {
        switch (chValue) {
            case 'n':
                return NULL;
            case 'b':
                return BYTE;
            case 's':
                return SHORT;
            case 'i':
                return INT;
            case 'l':
                return LONG;
            case 'f':
                return FLOAT;
            case 'd':
                return DOUBLE;
            case 'c':
                return CHAR;
            case 't':
                return STRING;
            case 'y':
                return BYTE_ARRAY;
            default:
                return INT;
        }
    }

    public byte byteValue() {
        return (byte) this.ordinal();
    }

    public char getAbbrType() {
        return abbrType;
    }

    public byte combine(ValueType type) {
        if (type == null)
            return (byte) ((byteValue() << 4) + 0x0F);
        else
            return (byte) ((byteValue() << 4) + type.byteValue());
    }
}
