package org.hptd.meta;

/**
 * HPTD name will mapper into an inner id
 *
 * @author ford
 */
public class NameMapper {
    private String hptdName;
    private long metaClassId = -1;
    private long innerId = -1;

    public NameMapper(String hptdName, long metaClassId, long innerId) {
        this.hptdName = hptdName;
        this.metaClassId = metaClassId;
        this.innerId = innerId;
    }

    public NameMapper() {
    }

    public String getHptdName() {
        return hptdName;
    }

    public void setHptdName(String hptdName) {
        this.hptdName = hptdName;
    }

    public long getMetaClassId() {
        return metaClassId;
    }

    public void setMetaClassId(long metaClassId) {
        this.metaClassId = metaClassId;
    }

    public long getInnerId() {
        return innerId;
    }

    public void setInnerId(long innerId) {
        this.innerId = innerId;
    }
}
