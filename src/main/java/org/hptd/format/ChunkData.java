package org.hptd.format;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.hptd.utils.ByteBufferUtil;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * the chunk data description of HPTD chunk,it is composed of two:column types,datas.
 * <ul>
 * <li>column type length(2 bytes)</li>
 * <li>column type list(each type is 4 bits,means 1 byte has 2 types)</li>
 * <li>data list(string/array will prefix the column data length(2 bytes)</li>
 * </ul>
 *
 * @author ford
 */
public class ChunkData implements HptdByteBuffer {
    private final List<DataValue> datas;

    public ChunkData(List<DataValue> datas) {
        this.datas = datas;
    }

    public List<DataValue> getDatas() {
        return datas;
    }

    @Override
    public final ByteBuffer toBuffer() {
        int dataSize = datas.size();
        short typeLen = (short) ((dataSize + 1) >> 1);
        byte[] types = new byte[typeLen];
        ByteArrayDataOutput dataOut = ByteStreams.newDataOutput();
        ByteArrayDataOutput all = ByteStreams.newDataOutput();
        byte tmpType = -1;
        for (int k = 0; k < dataSize; k++) {
            DataValue dataValue = datas.get(k);
            dataValue.writeDataOut(dataOut);
            byte bType = dataValue.getType().byteValue();
            int mod = k % 2;
            if (mod == 1) {
                types[k >> 1] = (byte) (tmpType << 4 + bType);
                tmpType = -1;
            } else if (mod == 0 && k == dataSize - 1) {
                types[k >> 1] = (byte) (bType << 4 + 0x0F);
            } else {
                tmpType = bType;
            }
        }
        all.writeShort(typeLen);
        all.write(types);
        all.write(dataOut.toByteArray());
        return ByteBufferUtil.bigEndianWrap(all.toByteArray());
    }

    static public ChunkData valueOf(ByteBuffer buffer) {
        return null;
    }
}
