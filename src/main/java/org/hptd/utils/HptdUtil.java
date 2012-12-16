package org.hptd.utils;

import java.io.File;

/**
 * HPTD utilization.
 *
 * @author ford
 */
public class HptdUtil {
    private static String rootPath = ".";

    public static String getRootPath() {
        return rootPath;
    }

    public static void setRootPath(String rootPath) {
        HptdUtil.rootPath = rootPath + "/hptdData";
        File f = new File(HptdUtil.rootPath);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    public static void setRootPathWithTmp() {
        setRootPath(System.getProperties().getProperty("java.io.tmpdir"));
    }

    public static void setRootPathWithUserHome() {
        setRootPath(System.getProperties().getProperty("user.home"));
    }
}
