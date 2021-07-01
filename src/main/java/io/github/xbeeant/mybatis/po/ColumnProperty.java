package io.github.xbeeant.mybatis.po;

/**
 *
 * @author xiaobiao
 */
public class ColumnProperty {
    private String column;

    private String typeHandler;

    private Boolean fuzzySearch;

    private String order;

    public ColumnProperty() {
    }

    public ColumnProperty(String column, String order) {
        this.column = column;
        this.order = order;
    }

    public ColumnProperty(String column, String order, Boolean fuzzySearch) {
        this.column = column;
        this.order = order;
        this.fuzzySearch = fuzzySearch;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getTypeHandler() {
        if (null == typeHandler || "".equals(typeHandler)) {
            return null;
        }
        return typeHandler;
    }

    public void setTypeHandler(String typeHandler) {
        this.typeHandler = typeHandler;
    }

    public Boolean getFuzzySearch() {
        return fuzzySearch;
    }

    public void setFuzzySearch(Boolean fuzzySearch) {
        this.fuzzySearch = fuzzySearch;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
