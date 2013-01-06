package org.hptd.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.hptd.meta.NameMapper;
import org.hptd.utils.H2DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

/**
 * the nameMapper cache.
 *
 * @author ford
 */
public class NameCache {
    private static final int DEFAULT_COUNT = 20480;
    private static Logger logger = LoggerFactory.getLogger(NameCache.class);
    private static int nameCacheCount = DEFAULT_COUNT;
    private static LoadingCache<String, Long> name2Id = null;


    public static int getNameCacheCount() {
        return nameCacheCount;
    }

    public static void init() {
        init(DEFAULT_COUNT);
    }

    public static void init(int nameCacheCount) {
        NameCache.nameCacheCount = nameCacheCount;
        H2DBUtil.init();
        name2Id = CacheBuilder.newBuilder().maximumSize(nameCacheCount).build(
                new CacheLoader<String, Long>() {
                    @Override
                    public Long load(String s) {
                        NameMapper mapper = H2DBUtil.getNameMapper(s);
                        if (mapper != null) {
                            return mapper.getInnerId();
                        } else {
                            logger.info("can not find id with name:" + s);
                            return -1l;
                        }
                    }
                });
    }

    public static void destroy() {
        H2DBUtil.destroy();
        name2Id.cleanUp();
    }

    public static long getId(String hptdName) throws ExecutionException {
        return name2Id.get(hptdName);
    }

    public static long getIdWithCreate(String hptdName) throws ExecutionException {
        long innerId = name2Id.get(hptdName);
        if (innerId == -1) {
            H2DBUtil.saveOrUpdateNameMapper(new NameMapper(hptdName, -1, -1));
            name2Id.invalidate(hptdName);
            innerId = name2Id.get(hptdName);
        }
        return innerId;
    }
}
