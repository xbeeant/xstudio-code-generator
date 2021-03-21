package io.github.xbeeant.mybatis.extend;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaobiao
 * @version 2019/5/1
 */
public class PluginUtil {
    private static final Pattern HANDLER_PATTERN = Pattern.compile("#handler\\s*:\\s*([\\w\\W]*)#");

    /**
     * 添加If元素
     *
     * @param whereElement  where元素
     * @param column        列
     * @param prefix        前缀
     * @param usingBeginEnd 生成Begin End字段
     * @param usingDateTime 使用DateTime替换Date
     */
    public static void addIfElement(XmlElement whereElement, IntrospectedColumn column, String prefix, Boolean usingBeginEnd, Boolean usingDateTime) {
        XmlElement rootIfElement = new XmlElement("if");
        StringBuilder sb = new StringBuilder();
        if (null == prefix) {
            prefix = "";
        }
        sb.append(prefix);
        sb.append(column.getJavaProperty());
        sb.append(" != null");
        Attribute attribute = new Attribute("test", sb.toString());
        rootIfElement.addAttribute(attribute);


        if (column.getFullyQualifiedJavaType().equals(FullyQualifiedJavaType.getDateInstance())) {
            if (usingDateTime) {
                XmlElement ifElement = new XmlElement("if");

                sb = new StringBuilder();
                if (null == prefix) {
                    prefix = "";
                }
                sb.append(prefix);
                sb.append(column.getJavaProperty());
                sb.append(".start == null and ");
                sb.append(prefix);
                sb.append(column.getJavaProperty());
                sb.append(".end == null");
                attribute = new Attribute("test", sb.toString());
                ifElement.addAttribute(attribute);

                sb = new StringBuilder("and ");
                sb.append(column.getActualColumnName());
                sb.append(" = #{");
                sb.append(prefix);
                sb.append(column.getJavaProperty());
                sb.append(",jdbcType=");
                sb.append(column.getJdbcTypeName());
                sb.append("}");
                ifElement.addElement(new TextElement(sb.toString()));
                rootIfElement.addElement(ifElement);

                // start
                ifElement = new XmlElement("if");
                sb = new StringBuilder();
                if (null == prefix) {
                    prefix = "";
                }
                sb.append(prefix);
                sb.append(column.getJavaProperty());
                sb.append(".start != null");
                attribute = new Attribute("test", sb.toString());
                ifElement.addAttribute(attribute);

                sb = new StringBuilder("and ");
                sb.append(column.getActualColumnName());
                sb.append(" &gt;= #{");
                sb.append(prefix);
                sb.append(column.getJavaProperty());
                sb.append(",jdbcType=");
                sb.append(column.getJdbcTypeName());
                sb.append("}");
                ifElement.addElement(new TextElement(sb.toString()));
                rootIfElement.addElement(ifElement);

                // end
                ifElement = new XmlElement("if");
                sb = new StringBuilder();
                if (null == prefix) {
                    prefix = "";
                }
                sb.append(prefix);
                sb.append(column.getJavaProperty());
                sb.append(".end != null");
                attribute = new Attribute("test", sb.toString());
                ifElement.addAttribute(attribute);

                sb = new StringBuilder("and ");
                sb.append(column.getActualColumnName());
                sb.append(" &lt;= #{");
                sb.append(prefix);
                sb.append(column.getJavaProperty());
                sb.append(",jdbcType=");
                sb.append(column.getJdbcTypeName());
                sb.append("}");
                ifElement.addElement(new TextElement(sb.toString()));
                rootIfElement.addElement(ifElement);
            } else {
                sb = new StringBuilder("and ");
                sb.append(column.getActualColumnName());
                sb.append(" = #{");
                sb.append(prefix);
                sb.append(column.getJavaProperty());
                sb.append(",jdbcType=");
                sb.append(column.getJdbcTypeName());
                sb.append("}");
                rootIfElement.addElement(new TextElement(sb.toString()));
                if (usingBeginEnd) {
                    // **Begin
                    rootIfElement = new XmlElement("if");
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
                    rootIfElement.addElement(new TextElement(sb.toString()));
                    rootIfElement.addAttribute(attribute);
                    whereElement.addElement(rootIfElement);

                    // **End
                    rootIfElement = new XmlElement("if");
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
                    rootIfElement.addElement(new TextElement(sb.toString()));
                    rootIfElement.addAttribute(attribute);
                    whereElement.addElement(rootIfElement);
                }
            }
        } else {
            sb = new StringBuilder("and ");
            sb.append(column.getActualColumnName());
            sb.append(" = #{");
            sb.append(prefix);
            sb.append(column.getJavaProperty());
            sb.append(",jdbcType=");
            sb.append(column.getJdbcTypeName());
            sb.append("}");
            rootIfElement.addElement(new TextElement(sb.toString()));
        }

        whereElement.addElement(rootIfElement);
    }

    /**
     * 添加include元素
     *
     * @param xmlElement xml元素
     * @param include    包括
     */
    public static void addInclude(XmlElement xmlElement, String include) {
        XmlElement includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid", include));
        xmlElement.addElement(includeElement);
    }

    /**
     * 得到主键的FullyQualifiedJavaType
     *
     * @param introspectedTable 表
     * @return {@link FullyQualifiedJavaType}
     */
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

    public static String typeHandler(IntrospectedColumn column) {
        String remarks = column.getRemarks();
        Matcher matcher = HANDLER_PATTERN.matcher(remarks);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    public static Map<String, IntrospectedColumn> typeHandlersColumns(IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        Map<String, IntrospectedColumn> handlerColumns = new HashMap<>();
        for (IntrospectedColumn column : allColumns) {
            if (column.getRemarks().contains("#handler")) {
                handlerColumns.put(column.getActualColumnName(), column);
            }
        }
        return handlerColumns;
    }
}
