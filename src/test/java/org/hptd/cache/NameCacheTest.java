package org.hptd.cache;

import org.hptd.meta.NameMapper;
import org.hptd.utils.H2DBUtil;
import org.hptd.utils.HptdUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Change me
 *
 * @author ford
 */
public class NameCacheTest {
    @BeforeMethod
    public void setUp() throws Exception {
        HptdUtil.setRootPathWithTmp();
        H2DBUtil.init();
        H2DBUtil.saveOrUpdateNameMapper(new NameMapper("test", -1, -1));
        NameCache.init();
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testGetIdWithNoId() throws Exception {
        Long id = NameCache.getId("testtest");
    }
    @Test()
    public void testGetId() throws Exception {
        Long id = NameCache.getId("test");
        assertEquals(1,id.longValue());
    }
}
