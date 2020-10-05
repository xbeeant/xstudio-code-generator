package com.xstudio.mybatis.extend;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @author xiaobiao
 * @version 2019/5/1
 */
public class PluginUtil {
    public static FullyQualifiedJavaType getKeyFqjt(IntrospectedTable introspectedTable) {
        String primaryKeyType = introspectedTable.getPrimaryKeyType();
        FullyQualifiedJavaType primaryKeyTypeFqjt = new FullyQualifiedJavaType(primaryKeyType);
        if (introspectedTable.getPrimaryKeyColumns().isEmpty()) {
            // default key
            primaryKeyTypeFqjt = new FullyQualifiedJavaType("java.lang.Long");
        }
        if (null != introspectedTable.getPrimaryKeyColumns() && introspectedTable.getPrimaryKeyColumns().size() == 1) {
            primaryKeyTypeFqjt = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType();
        }

        return primaryKeyTypeFqjt;
    }

    public static void addIfElement(XmlElement whereElement, IntrospectedColumn column, String prefix) {
        XmlElement ifElement = new XmlElement("if");
        StringBuilder sb = new StringBuilder();
        if (null == prefix) {
            prefix = "";
        }
        sb.append(prefix);
        sb.append(column.getJavaProperty());
        sb.append(" != null");
        Attribute attribute = new Attribute("test", sb.toString());

        sb = new StringBuilder("and ");
        sb.append(column.getActualColumnName());
        sb.append(" = #{");
        sb.append(prefix);
        sb.append(column.getJavaProperty());
        sb.append(",jdbcType=");
        sb.append(column.getJdbcTypeName());
        sb.append("}");
        ifElement.addElement(new TextElement(sb.toString()));
        ifElement.addAttribute(attribute);

        whereElement.addElement(ifElement);

        if (column.getFullyQualifiedJavaType().equals(FullyQualifiedJavaType.getDateInstance())) {
            // **Begin
            ifElement = new XmlElement("if");
            sb = new StringBuilder(prefix);
            sb.append(column.getJavaProperty());
            sb.append("Begin != null");
            attribute = new Attribute("test", sb.toString());

            sb = new StringBuilder("and ");
            sb.append(column.getActualColumnName());
            sb.append(" &gt;= #{");
            sb.append(prefix);
            sb.append(column.getJavaProperty());
            sb.append("Begin,jdbcType=");
            sb.append(column.getJdbcTypeName());
            sb.append("}");
            ifElement.addElement(new TextElement(sb.toString()));
            ifElement.addAttribute(attribute);
            whereElement.addElement(ifElement);

            // **End
            ifElement = new XmlElement("if");
            sb = new StringBuilder(prefix);
            sb.append(column.getJavaProperty());
            sb.append("End != null");
            attribute = new Attribute("test", sb.toString());

            sb = new StringBuilder("and ");
            sb.append(column.getActualColumnName());
            sb.append(" &lt;= #{");
            sb.append(prefix);
            sb.append(column.getJavaProperty());
            sb.append("End,jdbcType=");
            sb.append(column.getJdbcTypeName());
            sb.append("}");
            ifElement.addElement(new TextElement(sb.toString()));
            ifElement.addAttribute(attribute);
            whereElement.addElement(ifElement);
        }
    }

    public static void addInclude(XmlElement xmlElement, String include) {
        XmlElement includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid", include));
        xmlElement.addElement(includeElement);
    }
}
