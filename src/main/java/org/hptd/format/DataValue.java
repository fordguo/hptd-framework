package org.hptd.format;

import com.google.common.io.ByteArrayDataOutput;

/**
 * the each column data information
 *
 * @author ford
 * @since 1.0
 */
public class DataValue {
    private final ValueType type;
    private final Object value;

    public DataValue(ValueType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public ValueType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public void writeDataOut(ByteArrayDataOutput dataOutput) {
        switch (type) {
            case NULL:
                break;
            case BYTE:
                dataOutput.writeByte(byteValue());
                break;
            case SHORT:
                dataOutput.writeShort(shortValue());
                break;
            case CHAR:
                dataOutput.writeChar(charValue());
                break;
            case INT:
                dataOutput.writeInt(intValue());
                break;
            case LONG:
                dataOutput.writeLong(longValue());
                break;
            case FLOAT:
                dataOutput.writeFloat(floatValue());
                break;
            case DOUBLE:
                dataOutput.writeDouble(doubleValue());
                break;
            case STRING:
                byte[] strBytes = stringValue().getBytes();
                dataOutput.writeShort(strBytes.length);
                dataOutput.write(strBytes);
                break;
            case BYTE_ARRAY:
                byte[] arrBytes = arryValue();
                dataOutput.writeShort(arrBytes.length);
                dataOutput.write(arrBytes);
                break;
        }
    }

    public byte byteValue() {
        return ((Number) value).byteValue();
    }

    public short shortValue() {
        return ((Number) value).shortValue();
    }

    public int intValue() {
        return ((Number) value).intValue();
    }

    public char charValue() {
        return ((Character) value).charValue();
    }

    public long longValue() {
        return ((Number) value).longValue();
    }

    public float floatValue() {
        return ((Number) value).floatValue();
    }

    public double doubleValue() {
        return ((Number) value).doubleValue();
    }

    public String stringValue() {
        return (String) value;
    }

    public byte[] arryValue() {
        return (byte[]) value;
    }

    @Override
    public String toString() {
        return "DataValue{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }
}
