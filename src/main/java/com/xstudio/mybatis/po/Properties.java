package com.xstudio.mybatis.po;

/**
 * @author xiaobiao
 * @version 2020/10/2
 */
public class Properties {
    private ConnectionProperty connectionProperty;

    private JavaClientProperty javaClientProperty;

    private JavaModelProperty javaModelProperty;

    private SqlMapProperty sqlMapProperty;

    private TableProperty tableProperty;

    private XstudioProperty xstudioProperty;

    public ConnectionProperty getConnectionProperty() {
        return connectionProperty;
    }

    public void setConnectionProperty(ConnectionProperty connectionProperty) {
        this.connectionProperty = connectionProperty;
    }

    public JavaClientProperty getJavaClientProperty() {
        return javaClientProperty;
    }

    public void setJavaClientProperty(JavaClientProperty javaClientProperty) {
        this.javaClientProperty = javaClientProperty;
    }

    public JavaModelProperty getJavaModelProperty() {
        return javaModelProperty;
    }

    public void setJavaModelProperty(JavaModelProperty javaModelProperty) {
        this.javaModelProperty = javaModelProperty;
    }

    public SqlMapProperty getSqlMapProperty() {
        return sqlMapProperty;
    }

    public void setSqlMapProperty(SqlMapProperty sqlMapProperty) {
        this.sqlMapProperty = sqlMapProperty;
    }

    public TableProperty getTableProperty() {
        return tableProperty;
    }

    public void setTableProperty(TableProperty tableProperty) {
        this.tableProperty = tableProperty;
    }

    public XstudioProperty getXstudioProperty() {
        return xstudioProperty;
    }

    public void setXstudioProperty(XstudioProperty xstudioProperty) {
        this.xstudioProperty = xstudioProperty;
    }
}
