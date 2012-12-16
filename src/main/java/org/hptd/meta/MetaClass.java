package org.hptd.meta;

/**
 * the HPTD instance meta class information
 *
 * @author ford
 * @since 1.0
 */
public class MetaClass {
    private long id;
    private String name;
    private String label;
    private String columns;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }
}
