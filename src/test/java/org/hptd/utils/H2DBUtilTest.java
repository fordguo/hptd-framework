package org.hptd.utils;

import org.hptd.meta.NameMapper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Change me
 *
 * @author ford
 */
public class H2DBUtilTest {
    @BeforeMethod
    public void setUp() throws Exception {
        HptdUtil.setRootPathWithTmp();
        H2DBUtil.init();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        H2DBUtil.destroy();
    }

    @Test
    public void testNameMapper() throws Exception {
        H2DBUtil.saveOrUpdateNameMapper(new NameMapper("test", -1, -1));
        NameMapper nameMapper = H2DBUtil.getNameMapper("test");
        assertNotNull(nameMapper);
        assertEquals(1, nameMapper.getInnerId());
        assertEquals(-1, nameMapper.getMetaClassId());

        H2DBUtil.saveOrUpdateNameMapper(new NameMapper("test", 12, 1));

        nameMapper = H2DBUtil.getNameMapper("test");
        assertNotNull(nameMapper);
        assertEquals(1, nameMapper.getInnerId());
        assertEquals(12, nameMapper.getMetaClassId());
        H2DBUtil.saveOrUpdateNameMapper(new NameMapper("test1", -1, -1));
        nameMapper = H2DBUtil.getNameMapper("test1");
        assertNotNull(nameMapper);
        assertEquals(2, nameMapper.getInnerId());
    }

}
