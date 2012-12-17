package org.hptd.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.hptd.meta.NameMapper;
import org.hptd.utils.H2DBUtil;

import java.util.concurrent.ExecutionException;

/**
 * the nameMapper cache.
 *
 * @author ford
 */
public class NameCache {
    private static int nameCacheCount = 20480;
    private static LoadingCache<String, Long> name2Id = null;

    public static void setNameCacheCount(int nameCacheCount) {
        NameCache.nameCacheCount = nameCacheCount;
    }

    public static void init() {
        name2Id = CacheBuilder.newBuilder().maximumSize(nameCacheCount).build(
                new CacheLoader<String, Long>() {
                    @Override
                    public Long load(String s) throws Exception {
                        NameMapper mapper = H2DBUtil.getNameMapper(s);
                        if (mapper != null) {
                            return mapper.getInnerId();
                        } else {
                            throw new RuntimeException("can not find id with name:" + s);
                        }
                    }
                });
    }

    public static void destroy() {
    }

    public static long getId(String hptdName) throws ExecutionException {
        return name2Id.get(hptdName);
    }
}
