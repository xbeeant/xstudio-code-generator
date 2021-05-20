package io.github.xbeeant.mybatis.extend.plugins;

import io.github.xbeeant.mybatis.po.ColumnProperty;
import org.mybatis.generator.api.IntrospectedColumn;

import java.util.Comparator;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2021/5/20
 */
public class ColumnComparator implements Comparator<IntrospectedColumn> {
    private List<ColumnProperty> columnProperties;

    public void setColumnProperties(List<ColumnProperty> columnProperties) {
        this.columnProperties = columnProperties;
    }

    @Override
    public int compare(IntrospectedColumn o1, IntrospectedColumn o2) {
        String o1Order = "0";
        String o2Order = "0";
        for (ColumnProperty columnProperty : columnProperties) {
            if (columnProperty.getColumn().equals(o1.getActualColumnName())) {
                o1Order = columnProperty.getOrder();
            }
            if (columnProperty.getColumn().equals(o2.getActualColumnName())) {
                o2Order = columnProperty.getOrder();
            }
        }

        return o1Order.compareTo(o2Order);
    }
}
