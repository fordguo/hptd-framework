package org.hptd.io;

import org.hptd.format.ChunkData;
import org.hptd.format.DataValue;
import org.hptd.format.ValueType;
import org.hptd.utils.HptdUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.testng.AssertJUnit.*;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

/**
 * Change me
 *
 * @author ford
 */
public class LevelDbIoTest {
    private LevelDbIo leveldbIo;
    Calendar calendar = Calendar.getInstance();

    @BeforeMethod
    public void setUp() throws Exception {
        calendar.setTimeInMillis(300000000000l);
        HptdUtil.setRootPathWithTmp();
        leveldbIo = LeveldbCache.getRawLeveldb("testdb");
    }

    @AfterMethod
    public void tearDown() throws Exception {
        LeveldbCache.destroy();
    }

    @Test
    public void testGetPut() throws Exception {
        leveldbIo.put(new HptdKey(2, calendar.getTimeInMillis() - 2000000), new ChunkData(getDataValues(20000)));
        leveldbIo.put(new HptdKey(2, calendar.getTimeInMillis() - 1000000), new ChunkData(getDataValues(10000)));
        leveldbIo.put(new HptdKey(1, calendar.getTimeInMillis() - 5000000), new ChunkData(getDataValues(50000)));
        leveldbIo.put(new HptdKey(1, calendar.getTimeInMillis() - 3000000), new ChunkData(getDataValues(30000)));
        leveldbIo.put(new HptdKey(1, calendar.getTimeInMillis() - 2000000), new ChunkData(getDataValues(20000)));
        leveldbIo.put(new HptdKey(1, calendar.getTimeInMillis() - 1000000), new ChunkData(getDataValues(10000)));

        ChunkData data = leveldbIo.get(1, calendar.getTimeInMillis() - 2000000);
        assertNotNull(data);
        assertEquals(calendar.getTimeInMillis() - 2000000, data.getDatetime());
        assertEquals(20000, data.getDatas().get(0).intValue());

        assertNull(leveldbIo.get(1, System.currentTimeMillis()));
        data = leveldbIo.get(2, calendar.getTimeInMillis() - 2000000);
        assertNotNull(data);
        assertEquals("abcdefg", data.getDatas().get(3).stringValue());
        assertEquals(19800, data.getDatas().get(1).intValue());
    }

    private ArrayList<DataValue> getDataValues(int value) {
        ArrayList<DataValue> dataValues = new ArrayList<DataValue>();
        dataValues.add(new DataValue(ValueType.INT, value));
        dataValues.add(new DataValue(ValueType.INT, value - 200));
        dataValues.add(new DataValue(ValueType.INT, value - 100));
        dataValues.add(new DataValue(ValueType.STRING, "abcdefg"));
        dataValues.add(new DataValue(ValueType.DOUBLE, value + 0.5));
        return dataValues;
    }

    @Test(dependsOnMethods = "testGetPut")
    public void testGetRange() throws Exception {
        List<ChunkData> dataValues = leveldbIo.getRange(1, calendar.getTimeInMillis() - 3000000, calendar.getTimeInMillis());
        assertTrue(dataValues.size() > 0);
        assertEquals(3,dataValues.size());
        assertEquals(calendar.getTimeInMillis() - 3000000,dataValues.get(0).getDatetime());
        assertEquals(10000,dataValues.get(2).getDatas().get(0).intValue());

        dataValues = leveldbIo.getRange(1, calendar.getTimeInMillis() - 300000000, calendar.getTimeInMillis());
        assertTrue(dataValues.size() > 0);
        assertEquals(4,dataValues.size());
    }
}
