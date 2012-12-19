package org.hptd.utils;

import org.joda.time.DateTime;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.assertEquals;

/**
 * Change me
 *
 * @author ford
 */
public class TimedbUtilTest {
    @Test
    public void testGetRawdbName() throws Exception {
        DateTime dateTime = new DateTime(2012,12,12,11,11);
        assertEquals("2012-12", TimedbUtil.getRawdbName(dateTime.toDate()));
        assertEquals("2012-12", TimedbUtil.getRawdbName(dateTime));
        assertEquals("2012-12", TimedbUtil.getRawdbName(dateTime.getMillis()));
    }

    @Test
    public void testGetHourStatdbName() throws Exception {
        DateTime dateTime = new DateTime(2012,12,12,11,11);
        assertEquals("2012", TimedbUtil.getHourStatdbName(dateTime.toDate()));
        assertEquals("2012", TimedbUtil.getHourStatdbName(dateTime));
        assertEquals("2012", TimedbUtil.getHourStatdbName(dateTime.getMillis()));
    }
}
