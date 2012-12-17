package org.hptd.format;

import com.google.common.io.ByteArrayDataOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * the each column data information
 *
 * @author ford
 * @since 1.0
 */
public class DataValue {
    private static Logger logger = LoggerFactory.getLogger(DataValue.class);
    private final ValueType type;
    private Object value;

    public DataValue(ValueType type) {
        this(type, null);
    }

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

    public void setValue(Object value) {
        this.value = value;
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
                byte[] strBytes = null;
                try {
                    strBytes = stringValue().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.error(" wrong encoding with value:" + stringValue(), e);
                    strBytes = new byte[0];
                }
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

    public void readBuffer(ByteBuffer buffer) {
        switch (type) {
            case NULL:
                break;
            case BYTE:
                this.value = buffer.get();
                break;
            case SHORT:
                this.value = buffer.getShort();
                break;
            case CHAR:
                this.value = buffer.getChar();
                break;
            case INT:
                this.value = buffer.getInt();
                break;
            case LONG:
                this.value = buffer.getLong();
                break;
            case FLOAT:
                this.value = buffer.getFloat();
                break;
            case DOUBLE:
                this.value = buffer.getDouble();
                break;
            case STRING:
                short strLen = buffer.getShort();
                byte[] strBytes = new byte[strLen];
                buffer.get(strBytes);
                try {
                    this.value = new String(strBytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.error(" wrong encoding with bytes:" + Arrays.asList(strBytes), e);
                    this.value = null;
                }
                break;
            case BYTE_ARRAY:
                short arrLen = buffer.getShort();
                byte[] arrBytes = new byte[arrLen];
                buffer.get(arrBytes);
                this.value = arrBytes;
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
