package io.github.xbeeant.mybatis.extend;

import io.github.xbeeant.mybatis.po.IfElementProperty;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaobiao
 * @version 2019/5/1
 */
public class PluginUtil {

    private PluginUtil() {

    }

    public static final List<String> digit = Arrays.asList("BYTE",
            "FLOAT",
            "DOUBLE",
            "BIGINT",
            "REAL",
            "INTEGER",
            "TINYINT",
            "SMALLINT",
            "BIT",
            "BOOLEAN",
            "NUMERIC",
            "DECIMAL");

    public static   final List<String> time = Arrays.asList(
            "TIMESTAMP",
            "DATE",
            "TIME");

    private static final Pattern HANDLER_PATTERN = Pattern.compile("#handler\\s*:\\s*([\\w\\W]*)#");

    public static String columnValue(IntrospectedColumn column, String javaPropertyPrefix, String handler, boolean endOfComman) {
        column.setTypeHandler(handler);
        String parameterClause = MyBatis3FormattingUtilities.getParameterClause(column, javaPropertyPrefix);
        if (endOfComman) {
            parameterClause += ",";
        }
        return parameterClause;
    }

    /**
     * 添加If元素
     *
     * @param xmlElement        where元素
     * @param column            列
     * @param ifElementProperty 配置
     * @param handler           handler
     * @param endOfComma        is end of comma
     */
    public static void addIfElement(XmlElement xmlElement, IntrospectedColumn column, IfElementProperty ifElementProperty, String handler, boolean endOfComma) {
        XmlElement rootIfElement = new XmlElement("if");
        StringBuilder sb = new StringBuilder();
        sb.append(ifElementProperty.getJavaPropertyPrefix());
        sb.append(column.getJavaProperty());
        sb.append(" != null");
        Attribute attribute = new Attribute("test", sb.toString());
        rootIfElement.addAttribute(attribute);

        if (column.getFullyQualifiedJavaType().equals(FullyQualifiedJavaType.getDateInstance())) {
            if (ifElementProperty.getUsingDateTime()) {
                XmlElement ifElement = new XmlElement("if");

                sb = new StringBuilder();
                sb.append(ifElementProperty.getJavaPropertyPrefix());
                sb.append(column.getJavaProperty());
                sb.append(".start == null and ");
                sb.append(ifElementProperty.getJavaPropertyPrefix());
                sb.append(column.getJavaProperty());
                sb.append(".end == null");
                attribute = new Attribute("test", sb.toString());
                ifElement.addAttribute(attribute);

                sb = new StringBuilder();

                ifElementCondition(sb, ifElementProperty, column, "=");

                sb.append(columnValue(column, ifElementProperty.getJavaPropertyPrefix(), handler, endOfComma));

                ifElement.addElement(new TextElement(sb.toString()));
                rootIfElement.addElement(ifElement);

                // start
                ifElement = new XmlElement("if");
                sb = new StringBuilder();

                sb.append(ifElementProperty.getJavaPropertyPrefix());
                sb.append(column.getJavaProperty());
                sb.append(".start != null");
                attribute = new Attribute("test", sb.toString());
                ifElement.addAttribute(attribute);

                sb = new StringBuilder();
                ifElementCondition(sb, ifElementProperty, column, "&gt;=");

                sb.append(columnValue(column, ifElementProperty.getJavaPropertyPrefix(), handler, endOfComma));

                ifElement.addElement(new TextElement(sb.toString()));
                rootIfElement.addElement(ifElement);

                // end
                ifElement = new XmlElement("if");
                sb = new StringBuilder();
                sb.append(ifElementProperty.getJavaPropertyPrefix());
                sb.append(column.getJavaProperty());
                sb.append(".end != null");
                attribute = new Attribute("test", sb.toString());
                ifElement.addAttribute(attribute);

                sb = new StringBuilder();
                ifElementCondition(sb, ifElementProperty, column, "&lt;=");
                sb.append(columnValue(column, ifElementProperty.getJavaPropertyPrefix(), handler, endOfComma));
                ifElement.addElement(new TextElement(sb.toString()));
                rootIfElement.addElement(ifElement);
            } else {
                sb = new StringBuilder();
                ifElementCondition(sb, ifElementProperty, column, "=");
                sb.append(columnValue(column, ifElementProperty.getJavaPropertyPrefix(), handler, endOfComma));
                rootIfElement.addElement(new TextElement(sb.toString()));
                if (ifElementProperty.getUsingBeginEnd()) {
                    // **Begin
                    rootIfElement = new XmlElement("if");
                    sb = new StringBuilder(ifElementProperty.getJavaPropertyPrefix());
                    sb.append(column.getJavaProperty());
                    sb.append("Begin != null");
                    attribute = new Attribute("test", sb.toString());


                    sb = new StringBuilder();
                    ifElementCondition(sb, ifElementProperty, column, "&gt;=");

                    sb.append(columnValue(column, ifElementProperty.getJavaPropertyPrefix(), handler, endOfComma));
                    rootIfElement.addElement(new TextElement(sb.toString()));
                    rootIfElement.addAttribute(attribute);
                    xmlElement.addElement(rootIfElement);

                    // **End
                    rootIfElement = new XmlElement("if");
                    sb = new StringBuilder(ifElementProperty.getJavaPropertyPrefix());
                    sb.append(column.getJavaProperty());
                    sb.append("End != null");
                    attribute = new Attribute("test", sb.toString());

                    sb = new StringBuilder();
                    ifElementCondition(sb, ifElementProperty, column, "&lt;=");
                    sb.append(columnValue(column, ifElementProperty.getJavaPropertyPrefix(), handler, endOfComma));
                    rootIfElement.addElement(new TextElement(sb.toString()));
                    rootIfElement.addAttribute(attribute);
                    xmlElement.addElement(rootIfElement);
                }
            }
        } else {
            sb = new StringBuilder();
            ifElementCondition(sb, ifElementProperty, column, "=");
            sb.append(columnValue(column, ifElementProperty.getJavaPropertyPrefix(), handler, endOfComma));
            rootIfElement.addElement(new TextElement(sb.toString()));
        }

        xmlElement.addElement(rootIfElement);
    }

    private static void ifElementCondition(StringBuilder sb, IfElementProperty ifElementProperty, IntrospectedColumn column, String condition) {
        sb.append(ifElementProperty.getTextPrefix());
        if (ifElementProperty.getColumn()) {
            sb.append(ifElementProperty.getBeginningDelimiter());
            sb.append(column.getActualColumnName());
            sb.append(ifElementProperty.getEndingDelimiter());
            sb.append(" " + condition + " ");
        }
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

    public static Map<String, String> typeHandlersColumns(IntrospectedTable introspectedTable, Boolean usingDateTime) {
        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        Map<String, String> handlerColumns = new HashMap<>();
        for (IntrospectedColumn column : allColumns) {
            if (column.getRemarks().contains("#handler")) {
                handlerColumns.put(column.getActualColumnName(), typeHandler(column));
            }
            if (usingDateTime && time.contains(column.getJdbcTypeName())) {
                handlerColumns.put(column.getActualColumnName(), "io.github.xbeeant.spring.mybatis.typehandler.DateTimeResultHandler");
            }
        }
        return handlerColumns;
    }
}
