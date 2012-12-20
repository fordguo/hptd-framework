package org.hptd.cache;

import org.hptd.meta.NameMapper;
import org.hptd.utils.H2DBUtil;
import org.hptd.utils.HptdUtil;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Change me
 *
 * @author ford
 */
public class NameCacheTest {
    @BeforeClass
    public static void setUp() throws Exception {
        HptdUtil.setRootPathWithTmp();
        NameCache.init();
        H2DBUtil.saveOrUpdateNameMapper(new NameMapper("test", -1, -1));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        NameCache.destroy();
    }

    @Test()
    public void testGetIdWithNoId() throws Exception {
        assertEquals(-1l, NameCache.getId("testtest"));
    }

    @Test()
    public void testGetId() throws Exception {
        Long id = NameCache.getId("test");
        assertEquals(1, id.longValue());
    }

    @Test(dependsOnMethods = "testGetId")
    public void testGetIdWithCreate() throws Exception {
        Long id = NameCache.getIdWithCreate("test321");
        assertTrue(id.longValue() > 1);
    }
}
