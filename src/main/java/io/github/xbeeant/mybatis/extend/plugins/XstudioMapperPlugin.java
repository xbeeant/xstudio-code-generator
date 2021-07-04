package io.github.xbeeant.mybatis.extend.plugins;

import com.alibaba.fastjson.JSON;
import io.github.xbeeant.mybatis.extend.PluginUtil;
import io.github.xbeeant.mybatis.po.ColumnProperty;
import io.github.xbeeant.mybatis.po.IfElementProperty;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.*;

/**
 * @author xiaobiao
 * @version 2020/10/4
 */
public class XstudioMapperPlugin extends PluginAdapter {
    public static final String PREFIXED_EXAMPLE_WHERE_CLAUSE = "Prefixed_Example_Where_Clause";
    public static final String JAVA_UTIL_LIST = "java.util.List";
    public static final String WHERE = "where";
    public static final String FOREACH = "foreach";
    public static final String JDBC_TYPE = ",jdbcType=";
    public static final String INDEX = "index";
    public static final String COLLECTION = "collection";
    public static final String SEPARATOR = "separator";
    private final List<String> digit = new ArrayList<>();
    private final List<String> time = new ArrayList<>();
    private final List<String> nonFuzzySearchColumn = new ArrayList<>();
    private final String parameterType = "parameterType";
    private static final String EXAMPLE_PREFIX = "example.";
    private Boolean usingBeginEnd = false;
    private Boolean usingDateTime = false;
    private boolean generated = false;
    private XmlElement updateByPrimaryKeySelectiveElement;
    private List<ColumnProperty> columnProperties = new ArrayList<>();
    private Map<String, String> typeHandlers;
    private ColumnComparator columnComparator;

    @Override
    public boolean clientBasicCountMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicDeleteMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return super.clientBasicInsertMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientBasicInsertMultipleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicInsertMultipleHelperMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicSelectManyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicSelectOneMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicUpdateMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientGeneralCountMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientGeneralDeleteMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientGeneralSelectDistinctMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientGeneralSelectMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientGeneralUpdateMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMultipleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectListFieldGenerated(Field field, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectOneMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateAllColumnsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateSelectiveColumnsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapResultMapWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        if (!typeHandlers.isEmpty()) {
            for (VisitableElement elementElement : element.getElements()) {
                if (elementElement instanceof XmlElement) {
                    XmlElement xmlElement = (XmlElement) elementElement;
                    List<Attribute> attributes = xmlElement.getAttributes();
                    for (Attribute attribute : attributes) {
                        String handler = typeHandlers.get(attribute.getValue());
                        if (null != handler) {
                            xmlElement.addAttribute(new Attribute("typeHandler", handler));
                            break;
                        }
                    }
                }
            }
        }
        return super.sqlMapResultMapWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getElements().removeAll(element.getElements());
        context.getCommentGenerator().addComment(element);
        Context context = introspectedTable.getContext();
        StringBuilder insertClause = new StringBuilder();

        insertClause.append("insert into "); //$NON-NLS-1$
        insertClause.append(introspectedTable
                .getFullyQualifiedTableNameAtRuntime());

        insertClause.append(" ("); //$NON-NLS-1$
        StringBuilder valuesClause = new StringBuilder();
        valuesClause.append("values ("); //$NON-NLS-1$

        List<String> valuesClauses = new ArrayList<>();

        List<IntrospectedColumn> columns =
                ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns());
        for (int i = 0; i < columns.size(); i++) {
            IntrospectedColumn introspectedColumn = columns.get(i);
            introspectedColumn.setTypeHandler(typeHandlers.get(introspectedColumn.getActualColumnName()));
            insertClause.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            valuesClause.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn));
            if (i + 1 < columns.size()) {
                insertClause.append(", "); //$NON-NLS-1$
                valuesClause.append(", "); //$NON-NLS-1$
            }

            if (valuesClause.length() > 80) {
                element.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                OutputUtilities.xmlIndent(insertClause, 1);

                valuesClauses.add(valuesClause.toString());
                valuesClause.setLength(0);
                OutputUtilities.xmlIndent(valuesClause, 1);
            }
        }

        insertClause.append(')');
        element.addElement(new TextElement(insertClause.toString()));

        valuesClause.append(')');
        valuesClauses.add(valuesClause.toString());
        for (String clause : valuesClauses) {
            element.addElement(new TextElement(clause));
        }

        return super.sqlMapInsertElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<VisitableElement> elements = element.getElements();
        List<VisitableElement> ifElements = ((XmlElement) elements.get(elements.size() - 1)).getElements();
        ifElements.removeAll(ifElements);
        IfElementProperty ifElementProperty = new IfElementProperty(context);
        ifElementProperty.setColumn(false);
        ifElementProperty.setTextPrefix("");
        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            PluginUtil.addIfElement((XmlElement) elements.get(elements.size() - 1), column, ifElementProperty, typeHandlers.get(column.getActualColumnName()), true);
        }

        return super.sqlMapInsertSelectiveElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapResultMapWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        if (!typeHandlers.isEmpty()) {
            for (VisitableElement elementElement : element.getElements()) {
                if (elementElement instanceof XmlElement) {
                    XmlElement xmlElement = (XmlElement) elementElement;
                    List<Attribute> attributes = xmlElement.getAttributes();
                    for (Attribute attribute : attributes) {
                        String handler = typeHandlers.get(attribute.getValue());
                        if (null != handler) {
                            xmlElement.addAttribute(new Attribute("typeHandler", handler));
                        }
                    }
                }
            }
        }
        return super.sqlMapResultMapWithBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if (!topLevelClass.getSuperClass().isPresent() && !generated) {
            List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
            // set Key
            Method setKey = new Method("assignKeyValue");
            Method getPrimaryKey = new Method("getPrimaryKey");
            FullyQualifiedJavaType primaryKeyTypeFqjt = PluginUtil.getKeyFqjt(introspectedTable);
            if (!primaryKeyColumns.isEmpty()) {
                if (primaryKeyColumns.size() == 1) {
                    String keyProperty = primaryKeyColumns.get(0).getJavaProperty();
                    setKey.addBodyLine("this." + keyProperty + " = key;");
                    getPrimaryKey.addBodyLine("return " + keyProperty + ";");
                } else {
                    getPrimaryKey.addBodyLine(primaryKeyTypeFqjt.getShortNameWithoutTypeArguments() + " key = " +
                            "new " + primaryKeyTypeFqjt.getShortNameWithoutTypeArguments() + "();");
                    for (IntrospectedColumn primaryKeyColumn : primaryKeyColumns) {
                        String keyProperty = primaryKeyColumn.getJavaProperty();
                        String propertyMethod = JavaBeansUtil.getCamelCaseString(keyProperty, true);
                        setKey.addBodyLine("this." + keyProperty + " = key.get" + propertyMethod + "();");
                        getPrimaryKey.addBodyLine("key.set" + propertyMethod + "(this." + keyProperty + ");");
                    }
                    getPrimaryKey.addBodyLine("return key;");
                }
            } else {
                setKey.addBodyLine("");
                getPrimaryKey.addBodyLine("return null;");
            }

            setKey.addAnnotation("@Override");
            setKey.setVisibility(JavaVisibility.PUBLIC);
            setKey.addParameter(new Parameter(primaryKeyTypeFqjt, "key"));
            topLevelClass.addMethod(setKey);

            getPrimaryKey.addAnnotation("@Override");
            getPrimaryKey.setVisibility(JavaVisibility.PUBLIC);
            getPrimaryKey.setReturnType(primaryKeyTypeFqjt);
            topLevelClass.addMethod(getPrimaryKey);
            generated = true;
        }
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    @Override
    public boolean sqlMapCountByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getAttributes().removeIf(attribute -> parameterType.equals(attribute.getName()));
        element.getAttributes().add(new Attribute(parameterType, introspectedTable.getBaseRecordType()));

        replaceWithWhereExampleElement(element);
        return super.sqlMapCountByExampleElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getAttributes().removeIf(attribute -> parameterType.equals(attribute.getName()));
        element.getAttributes().add(new Attribute(parameterType, introspectedTable.getBaseRecordType()));

        replaceWithWhereExampleElement(element);
        return super.sqlMapDeleteByExampleElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        replaceKeyParam(element, introspectedTable);
        return super.sqlMapDeleteByPrimaryKeyElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        boolean b = super.sqlMapDocumentGenerated(document, introspectedTable);
        batchInsertSelective(document, introspectedTable);
        batchDeleteByPrimaryKey(document, introspectedTable);
        batchUpdateByPrimaryKeySelective(document, introspectedTable);

        fuzzySearch(introspectedTable, document.getRootElement(), false);
        fuzzySearchMap(introspectedTable, document.getRootElement(), false);
        fuzzySearch(introspectedTable, document.getRootElement(), true);
        fuzzySearchMap(introspectedTable, document.getRootElement(), true);

        return b;
    }

    @Override
    public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getElements().removeAll(element.getElements());
        context.getCommentGenerator().addComment(element);

        IfElementProperty ifElementProperty = new IfElementProperty(context);
        ifElementProperty.setColumn(true);
        ifElementProperty.setTextPrefix("and ");
        ifElementProperty.setUsingDateTime(usingDateTime);
        ifElementProperty.setUsingBeginEnd(usingBeginEnd);
        if (!"Example_Where_Clause".equalsIgnoreCase(element.getAttributes().get(0).getValue())) {
            element.getAttributes().removeIf(attribute -> "id".equals(attribute.getName()));
            element.addAttribute(new Attribute("id", PREFIXED_EXAMPLE_WHERE_CLAUSE));
            ifElementProperty.setJavaPropertyPrefix("example.");
        }

        // column 排序
        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        allColumns.sort(columnComparator);


        for (IntrospectedColumn column : allColumns) {
            PluginUtil.addIfElement(element, column, ifElementProperty, typeHandlers.get(column.getActualColumnName()), false);
        }
        return super.sqlMapExampleWhereClauseElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getAttributes().removeIf(attribute -> parameterType.equals(attribute.getName()));
        element.getAttributes().add(new Attribute(parameterType, introspectedTable.getBaseRecordType()));

        List<VisitableElement> elements = element.getElements();
        Iterator<VisitableElement> iterator = elements.iterator();
        while (iterator.hasNext()) {
            VisitableElement visitableElement = iterator.next();
            if (visitableElement instanceof XmlElement) {
                if ("if".equals(((XmlElement) visitableElement).getName())) {
                    iterator.remove();
                }
            }
        }


        XmlElement whereElement = new XmlElement(WHERE);
        PluginUtil.addInclude(whereElement, PREFIXED_EXAMPLE_WHERE_CLAUSE);
        element.getElements().add(whereElement);

        return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getAttributes().removeIf(attribute -> parameterType.equals(attribute.getName()));
        element.getAttributes().add(new Attribute(parameterType, introspectedTable.getBaseRecordType()));

        List<VisitableElement> elements = element.getElements();
        Iterator<VisitableElement> iterator = elements.iterator();
        while (iterator.hasNext()) {
            VisitableElement visitableElement = iterator.next();
            if (visitableElement instanceof XmlElement) {
                if ("if".equals(((XmlElement) visitableElement).getName())) {
                    iterator.remove();
                }
            }
        }

        XmlElement whereElement = new XmlElement(WHERE);
        PluginUtil.addInclude(whereElement, PREFIXED_EXAMPLE_WHERE_CLAUSE);
        element.getElements().add(whereElement);

        return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        replaceKeyParam(element, introspectedTable);
        return super.sqlMapSelectByPrimaryKeyElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        // remove
        // parameterType="map"
        element.getAttributes().removeIf(attribute -> parameterType.equals(attribute.getName()));

        replaceWithWhereExampleElement(element);
        return super.sqlMapUpdateByExampleSelectiveElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getAttributes().removeIf(attribute -> parameterType.equals(attribute.getName()));
        List<VisitableElement> elements = element.getElements();
        XmlElement condition = (XmlElement) elements.get(elements.size() - 1);
        elements.removeAll(elements);
        context.getCommentGenerator().addComment(element);
        element.getElements().add(new TextElement("update " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " set "));

        StringBuilder sb;
        int columnCount = introspectedTable.getAllColumns().size();
        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            sb = new StringBuilder();
            sb.append(context.getBeginningDelimiter());
            sb.append(column.getActualColumnName());
            sb.append(context.getEndingDelimiter());
            sb.append(" = ");
            sb.append(PluginUtil.columnValue(column, "record.", typeHandlers.get(column.getActualColumnName()), false));
            if (columnCount > 1) {
                sb.append(",");
            }
            columnCount -= 1;
            element.getElements().add(new TextElement(sb.toString()));
        }
        element.getElements().add(condition);
        replaceWithWhereExampleElement(element);
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        updateByPrimaryKeySelectiveElement = element;
        List<VisitableElement> elements = element.getElements();
        XmlElement setXmlElement = (XmlElement) elements.get(elements.size() - 2);
        setXmlElement.getElements().removeAll(setXmlElement.getElements());
        IfElementProperty ifElementProperty = new IfElementProperty(context);
        ifElementProperty.setColumn(true);
        ifElementProperty.setTextPrefix("");
        for (IntrospectedColumn nonPrimaryKeyColumn : introspectedTable.getNonPrimaryKeyColumns()) {
            PluginUtil.addIfElement(setXmlElement, nonPrimaryKeyColumn, ifElementProperty, typeHandlers.get(nonPrimaryKeyColumn.getActualColumnName()), true);
        }

        replaceKeyParam(element, introspectedTable);
        return super.sqlMapUpdateByPrimaryKeySelectiveElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        replaceKeyParam(element, introspectedTable);

        element.getAttributes().removeIf(attribute -> parameterType.equals(attribute.getName()));
        List<VisitableElement> elements = element.getElements();
        elements.removeAll(elements);
        context.getCommentGenerator().addComment(element);
        element.getElements().add(new TextElement("update " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " set "));

        StringBuilder sb;
        int columnCount = introspectedTable.getNonPrimaryKeyColumns().size();
        for (IntrospectedColumn column : introspectedTable.getNonPrimaryKeyColumns()) {
            sb = new StringBuilder();
            sb.append(context.getBeginningDelimiter());
            sb.append(column.getActualColumnName());
            sb.append(context.getEndingDelimiter());
            sb.append(" = ");
            sb.append(PluginUtil.columnValue(column, "", typeHandlers.get(column.getActualColumnName()), false));
            if (columnCount > 1) {
                sb.append(",");
            }
            columnCount -= 1;
            element.getElements().add(new TextElement(sb.toString()));
        }

        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        element.getElements().add(new TextElement("where"));
        for (IntrospectedColumn column : primaryKeyColumns) {
            sb = new StringBuilder();
            sb.append(context.getBeginningDelimiter());
            sb.append(column.getActualColumnName());
            sb.append(context.getEndingDelimiter());
            sb.append(" = ");
            sb.append(PluginUtil.columnValue(column, "record.", typeHandlers.get(column.getActualColumnName()), false));
            if (columnCount > 1) {
                sb.append("and");
            }
            columnCount -= 1;
            element.getElements().add(new TextElement(sb.toString()));
        }

        return super.sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        replaceKeyParam(element, introspectedTable);


        element.getAttributes().removeIf(attribute -> parameterType.equals(attribute.getName()));
        List<VisitableElement> elements = element.getElements();
        elements.removeAll(elements);
        context.getCommentGenerator().addComment(element);
        element.getElements().add(new TextElement("update " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " set "));

        StringBuilder sb;
        int columnCount = introspectedTable.getNonPrimaryKeyColumns().size();
        for (IntrospectedColumn column : introspectedTable.getNonPrimaryKeyColumns()) {
            if (column.isBLOBColumn()) {
                continue;
            }
            sb = new StringBuilder();
            sb.append(context.getBeginningDelimiter());
            sb.append(column.getActualColumnName());
            sb.append(context.getEndingDelimiter());
            sb.append(" = ");
            sb.append(PluginUtil.columnValue(column, "", typeHandlers.get(column.getActualColumnName()), false));
            if (columnCount > 1) {
                sb.append(",");
            }
            columnCount -= 1;
            element.getElements().add(new TextElement(sb.toString()));
        }

        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        element.getElements().add(new TextElement("where"));
        for (IntrospectedColumn column : primaryKeyColumns) {
            sb = new StringBuilder();
            sb.append(context.getBeginningDelimiter());
            sb.append(column.getActualColumnName());
            sb.append(context.getEndingDelimiter());
            sb.append(" = ");
            sb.append(PluginUtil.columnValue(column, "", typeHandlers.get(column.getActualColumnName()), false));
            if (columnCount > 1) {
                sb.append("and");
            }
            columnCount -= 1;
            element.getElements().add(new TextElement(sb.toString()));
        }

        return super.sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        digit.add("BYTE");
        // float
        digit.add("FLOAT");
        digit.add("DOUBLE");
        // long
        digit.add("BIGINT");
        // float
        digit.add("REAL");
        // int
        digit.add("INTEGER");
        // byte
        digit.add("TINYINT");
        // short
        digit.add("SMALLINT");
        // boolean
        digit.add("BIT");
        digit.add("BOOLEAN");
        // BigDecimal
        digit.add("NUMERIC");
        digit.add("DECIMAL");

        time.add("TIMESTAMP");
        time.add("DATE");
        time.add("TIME");

        typeHandlers = PluginUtil.typeHandlersColumns(introspectedTable);
        for (ColumnProperty columnProperty : columnProperties) {
            if (null != columnProperty.getTypeHandler()) {
                typeHandlers.put(columnProperty.getColumn(), columnProperty.getTypeHandler());
            }
        }

        super.initialized(introspectedTable);
    }

    @Override
    public boolean clientSelectAllMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    private void batchInsertSelective(Document document, IntrospectedTable introspectedTable) {
        XmlElement element = new XmlElement("insert");
        element.addAttribute(new Attribute("id", "batchInsertSelective"));
        element.addAttribute(new Attribute(parameterType, JAVA_UTIL_LIST));
        XmlElement foreachElement = new XmlElement(FOREACH);
        foreachElement.addAttribute(new Attribute(COLLECTION, "list"));
        foreachElement.addAttribute(new Attribute(INDEX, INDEX));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute(SEPARATOR, ";"));
        context.getCommentGenerator().addComment(element);

        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        XmlElement set = new XmlElement("set");
        XmlElement ifXml;
        TextElement text;
        foreachElement.addElement(new TextElement("insert into " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));

        for (IntrospectedColumn column : columns) {
            ifXml = new XmlElement("if");
            ifXml.addAttribute(new Attribute("test", "item." + column.getJavaProperty() + " != null "));
            String handler = typeHandlers.get(column.getActualColumnName());
            if (null != handler) {
                text = new TextElement(column.getActualColumnName() + " = #{item." + column.getJavaProperty() + JDBC_TYPE + column.getJdbcTypeName() + ",typeHandler=" + handler + "},");
            } else {
                text = new TextElement(column.getActualColumnName() + " = #{item." + column.getJavaProperty() + JDBC_TYPE + column.getJdbcTypeName() + "},");
            }
            ifXml.addElement(text);
            set.addElement(ifXml);
        }
        foreachElement.addElement(set);
        element.addElement(foreachElement);

        document.getRootElement().addElement(element);
    }

    private void batchDeleteByPrimaryKey(Document document, IntrospectedTable introspectedTable) {
        if (isNoPrimaryKey(introspectedTable)) {
            return;
        }
        XmlElement element = new XmlElement("delete");
        element.addAttribute(new Attribute("id", "batchDeleteByPrimaryKey"));
        element.addAttribute(new Attribute(parameterType, JAVA_UTIL_LIST));
        XmlElement foreachElement = new XmlElement(FOREACH);
        foreachElement.addAttribute(new Attribute(COLLECTION, "list"));
        foreachElement.addAttribute(new Attribute(INDEX, INDEX));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute(SEPARATOR, ";"));
        context.getCommentGenerator().addComment(element);

        foreachElement.addElement(new TextElement("delete from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        XmlElement where = new XmlElement(WHERE);
        String and = "";
        for (IntrospectedColumn primaryKeyColumn : introspectedTable.getPrimaryKeyColumns()) {
            where.addElement(new TextElement(and + primaryKeyColumn.getActualColumnName() + " = #{item." + primaryKeyColumn.getJavaProperty() + JDBC_TYPE + primaryKeyColumn.getJdbcTypeName() + "}"));
            and = "and ";
        }
        foreachElement.addElement(where);
        element.addElement(foreachElement);

        document.getRootElement().addElement(element);
    }

    private boolean isNoPrimaryKey(IntrospectedTable introspectedTable) {
        return null == introspectedTable.getPrimaryKeyColumns() || introspectedTable.getPrimaryKeyColumns().isEmpty();
    }

    private void batchUpdateByPrimaryKeySelective(Document document, IntrospectedTable introspectedTable) {
        if (isNoPrimaryKey(introspectedTable)) {
            return;
        }
        XmlElement element = new XmlElement("update");
        element.addAttribute(new Attribute("id", "batchUpdateByPrimaryKeySelective"));
        element.addAttribute(new Attribute(parameterType, JAVA_UTIL_LIST));

        XmlElement foreachElement = new XmlElement(FOREACH);
        foreachElement.addAttribute(new Attribute(COLLECTION, "list"));
        foreachElement.addAttribute(new Attribute(INDEX, INDEX));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute(SEPARATOR, ";"));
        context.getCommentGenerator().addComment(element);
        XmlElement setXmlElement = new XmlElement("set");
        for (VisitableElement visitableElement : updateByPrimaryKeySelectiveElement.getElements()) {
            if (visitableElement instanceof TextElement) {
                TextElement textElement = (TextElement) visitableElement;
                if (textElement.getContent().contains("#{")) {
                    String text = textElement.getContent().replace("#{", "#{item.");
                    foreachElement.addElement(new TextElement(text));
                } else {
                    foreachElement.addElement(visitableElement);
                }
            } else {
                // set xml element
                XmlElement set = (XmlElement) visitableElement;
                for (VisitableElement ifEl : set.getElements()) {
                    XmlElement sourceIfElement = (XmlElement) ifEl;
                    String value = sourceIfElement.getAttributes().get(0).getValue();
                    String[] split = value.split(" ");
                    XmlElement targetIfElement = new XmlElement("if");
                    String s = value.replaceAll(split[0], "item." + split[0]);
                    targetIfElement.addAttribute(new Attribute("test", s));
                    TextElement tx = (TextElement) ((XmlElement) ifEl).getElements().get(0);
                    String s1 = tx.getContent().replaceAll("#\\{" + split[0], "#{item." + split[0]);
                    targetIfElement.addElement(new TextElement(s1));
                    setXmlElement.addElement(targetIfElement);
                }
                foreachElement.addElement(setXmlElement);
            }
        }
        element.addElement(foreachElement);
        document.getRootElement().addElement(element);
    }

    private void fuzzySearch(IntrospectedTable introspectedTable, XmlElement mapper, boolean pager) {
        XmlElement rootXmlElement;
        Attribute attribute;

        rootXmlElement = new XmlElement("select");
        context.getCommentGenerator().addComment(rootXmlElement);
        if (pager) {
            attribute = new Attribute("id", "fuzzySearchByPager");
        } else {
            attribute = new Attribute("id", "fuzzySearch");
        }
        rootXmlElement.addAttribute(attribute);
        attribute = new Attribute("resultMap", "BaseResultMap");
        rootXmlElement.addAttribute(attribute);
        XmlElement whereElement = new XmlElement(WHERE);
        fromCondition(introspectedTable, rootXmlElement);

        if (pager) {
            PluginUtil.addInclude(whereElement, "Prefixed_Fuzzy_Search_Where_Clause");
        } else {
            PluginUtil.addInclude(whereElement, "Fuzzy_Search_Where_Clause");
        }
        rootXmlElement.addElement(whereElement);
        mapper.addElement(rootXmlElement);
    }

    private void fuzzySearchMap(IntrospectedTable introspectedTable, XmlElement mapper, boolean pager) {
        XmlElement rootXmlElement;
        Attribute attribute;
        XmlElement ifElement;

        rootXmlElement = new XmlElement("sql");
        context.getCommentGenerator().addComment(rootXmlElement);
        if (pager) {
            attribute = new Attribute("id", "Prefixed_Fuzzy_Search_Where_Clause");
        } else {
            attribute = new Attribute("id", "Fuzzy_Search_Where_Clause");
        }
        rootXmlElement.addAttribute(attribute);

        // column 排序
        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        allColumns.sort(columnComparator);

        for (IntrospectedColumn column : allColumns) {
            if (time.contains(column.getJdbcTypeName())) {
                if (usingBeginEnd) {
                    useBeginEnd(introspectedTable, pager, rootXmlElement, column);
                } else if (usingDateTime) {
                    useDateTime(introspectedTable, pager, rootXmlElement, column);
                } else {
                    ifElement = new XmlElement("if");
                    String testClause = getTestClause(column, pager);
                    ifElement.addAttribute(new Attribute("test", testClause));
                    String sqlClause = getSqlClause(column, pager, introspectedTable);
                    ifElement.addElement(new TextElement(sqlClause));
                    rootXmlElement.addElement(ifElement);
                }
            } else {
                ifElement = new XmlElement("if");
                String testClause = getTestClause(column, pager);
                ifElement.addAttribute(new Attribute("test", testClause));
                String sqlClause = getSqlClause(column, pager, introspectedTable);
                ifElement.addElement(new TextElement(sqlClause));
                rootXmlElement.addElement(ifElement);
            }
        }

        mapper.addElement(rootXmlElement);
    }

    private void useBeginEnd(IntrospectedTable introspectedTable, boolean pager, XmlElement rootXmlElement, IntrospectedColumn column) {
        XmlElement ifElement = new XmlElement("if");
        String testClause = getTestClause(column, pager);
        ifElement.addAttribute(new Attribute("test", testClause));
        String sqlClause = getSqlClause(column, pager, introspectedTable);
        ifElement.addElement(new TextElement(sqlClause));
        rootXmlElement.addElement(ifElement);

        // ***Begin
        ifElement = new XmlElement("if");
        testClause = getTestClause(column, pager);
        testClause = testClause.replaceAll(column.getJavaProperty(), column.getJavaProperty() + "Begin");
        ifElement.addAttribute(new Attribute("test", testClause));
        sqlClause = getSqlClause(column, pager, introspectedTable);
        sqlClause = sqlClause.replaceAll(column.getJavaProperty(), column.getJavaProperty() + "Begin");
        sqlClause = sqlClause.replaceAll(" = #", " &gt;= #");
        ifElement.addElement(new TextElement(sqlClause));
        rootXmlElement.addElement(ifElement);

        // ***End
        ifElement = new XmlElement("if");
        testClause = getTestClause(column, pager);
        testClause = testClause.replaceAll(column.getJavaProperty(), column.getJavaProperty() + "End");
        ifElement.addAttribute(new Attribute("test", testClause));
        sqlClause = getSqlClause(column, pager, introspectedTable);
        sqlClause = sqlClause.replaceAll(column.getJavaProperty(), column.getJavaProperty() + "End");
        sqlClause = sqlClause.replaceAll(" = #", " &lt;= #");
        ifElement.addElement(new TextElement(sqlClause));
        rootXmlElement.addElement(ifElement);
    }

    private void useDateTime(IntrospectedTable introspectedTable, boolean pager, XmlElement rootXmlElement, IntrospectedColumn column) {
        XmlElement rootIfElement;
        XmlElement ifElement;
        rootIfElement = new XmlElement("if");

        String testClause;
        String name = column.getJavaProperty();
        String prefix = "";
        if (pager) {
            prefix = EXAMPLE_PREFIX;
        }

        testClause = prefix + name + " != null";
        rootIfElement.addAttribute(new Attribute("test", testClause));
        rootXmlElement.addElement(rootIfElement);

        // ***
        ifElement = new XmlElement("if");
        testClause = prefix + name + ".start == null and " + prefix + name + ".end == null";
        ifElement.addAttribute(new Attribute("test", testClause));
        String sqlClause = getSqlClause(column, pager, introspectedTable);
        ifElement.addElement(new TextElement(sqlClause));
        rootIfElement.addElement(ifElement);

        // ***Begin
        ifElement = new XmlElement("if");
        testClause = getTestClause(column, pager);
        testClause = testClause.replaceAll(column.getJavaProperty(), column.getJavaProperty() + ".start");
        ifElement.addAttribute(new Attribute("test", testClause));
        sqlClause = getSqlClause(column, pager, introspectedTable);
        sqlClause = sqlClause.replaceAll(column.getJavaProperty(), column.getJavaProperty() + ".start");
        sqlClause = sqlClause.replaceAll(" = #", " &gt;= #");
        ifElement.addElement(new TextElement(sqlClause));
        rootIfElement.addElement(ifElement);

        // ***End
        ifElement = new XmlElement("if");
        testClause = getTestClause(column, pager);
        testClause = testClause.replaceAll(column.getJavaProperty(), column.getJavaProperty() + ".end");
        ifElement.addAttribute(new Attribute("test", testClause));
        sqlClause = getSqlClause(column, pager, introspectedTable);
        sqlClause = sqlClause.replaceAll(column.getJavaProperty(), column.getJavaProperty() + ".end");
        sqlClause = sqlClause.replaceAll(" = #", " &lt;= #");
        ifElement.addElement(new TextElement(sqlClause));
        rootIfElement.addElement(ifElement);
    }

    private void fromCondition(IntrospectedTable introspectedTable, XmlElement rootXmlElement) {
        StringBuilder sb;
        TextElement textElement;

        textElement = new TextElement("SELECT");
        rootXmlElement.addElement(textElement);
        rootXmlElement.addElement(new TextElement("<include refid=\"Base_Column_List\" />"));
        if (introspectedTable.hasBLOBColumns()) {
            rootXmlElement.addElement(new TextElement(", <include refid=\"Blob_Column_List\" />"));
        }

        sb = new StringBuilder();
        sb.append("FROM ");
        sb.append(introspectedTable.getFullyQualifiedTable().getIntrospectedTableName());
        textElement = new TextElement(sb.toString());
        rootXmlElement.addElement(textElement);
    }

    private String getTestClause(IntrospectedColumn column, boolean pager) {
        String name = column.getJavaProperty();
        String prefix = "";
        if (pager) {
            prefix = EXAMPLE_PREFIX;
        }
        if (digit.contains(column.getJdbcTypeName())
                || time.contains(column.getJdbcTypeName())) {
            String suffix = "";
            if (digit.contains(column.getJdbcTypeName())) {
                suffix = " or " + prefix + name + " == 0";
            }
            return prefix + name + " != null" + suffix;
        }
        return prefix + name + " != null and " + prefix + name + " != '' ";
    }

    private String getSqlClause(IntrospectedColumn column, boolean pager, IntrospectedTable introspectedTable) {
        String name = column.getJavaProperty();
        String columName = column.getActualColumnName();
        String prefix = "";
        if (pager) {
            prefix = EXAMPLE_PREFIX;
        }
        String remarks = column.getRemarks();
        remarks = remarks.replaceAll(" ", "");
        if (digit.contains(column.getJdbcTypeName().toUpperCase())
                || time.contains(column.getJdbcTypeName().toUpperCase())
                || nonFuzzySearchColumn.contains(columName)
                || isKeyColumn(column, introspectedTable)
                || remarks.contains("fuzzy:false")
                || column.isBLOBColumn()
        ) {
            return "AND " + columName + " = #{" + prefix + name + JDBC_TYPE + column.getJdbcTypeName() + "}";
        }
        if (remarks.contains("fuzzy:startWidth")) {
            return "AND " + columName + " LIKE CONCAT(#{" + prefix + name + JDBC_TYPE + column.getJdbcTypeName() + "}, '%')";
        }
        if (remarks.contains("fuzzy:endWidth")) {
            return "AND " + columName + " LIKE CONCAT('%', #{" + prefix + name + JDBC_TYPE + column.getJdbcTypeName() + "})";
        }

        return "AND " + columName + " LIKE CONCAT('%', #{" + prefix + name + JDBC_TYPE + column.getJdbcTypeName() + "}, '%')";
    }

    private boolean isKeyColumn(IntrospectedColumn column, IntrospectedTable introspectedTable) {
        for (IntrospectedColumn key : introspectedTable.getPrimaryKeyColumns()) {
            if (key.getActualColumnName().equals(column.getActualColumnName())) {
                return true;
            }
        }

        return false;
    }

    private void replaceKeyParam(XmlElement element, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if (primaryKeyColumns.size() == 1) {
            List<VisitableElement> elements = element.getElements();
            // get where text element
            VisitableElement visitableElement = elements.get(elements.size() - 1);
            String whereContent = ((TextElement) visitableElement).getContent();
            int start = whereContent.indexOf("#{");
            int end = whereContent.indexOf(",");
            // replace with `key`
            elements.remove(visitableElement);

            String sb = whereContent.substring(0, start + 2) +
                    "key" +
                    whereContent.substring(end);
            elements.add(new TextElement(sb));
        }
    }

    private void replaceWithWhereExampleElement(XmlElement element) {
        List<VisitableElement> elements = element.getElements();
        VisitableElement visitableElement = elements.get(elements.size() - 1);
        // remove
        // <if test="_parameter != null">
        //      <include refid="Example_Where_Clause" />
        //    </if>
        elements.remove(visitableElement);
        XmlElement whereElement = new XmlElement(WHERE);
        PluginUtil.addInclude(whereElement, PREFIXED_EXAMPLE_WHERE_CLAUSE);
        elements.add(whereElement);
    }

    @Override
    public boolean validate(List<String> warnings) {
        String nonFuzzySearchColumnStr = properties.getProperty("nonFuzzySearchColumn");
        String columnPropertiesStr = properties.getProperty("columns");
        columnProperties = JSON.parseArray(columnPropertiesStr, ColumnProperty.class);

        for (ColumnProperty columnProperty : columnProperties) {
            if (!columnProperty.getFuzzySearch()) {
                nonFuzzySearchColumn.add(columnProperty.getColumn());
            }
        }

        columnComparator = new ColumnComparator();
        columnComparator.setColumnProperties(columnProperties);
        usingBeginEnd = Boolean.valueOf(properties.getProperty("usingBeginEnd"));
        usingDateTime = Boolean.valueOf(properties.getProperty("usingDateTime"));
        if (null != nonFuzzySearchColumnStr) {
            String[] split = nonFuzzySearchColumnStr.replaceAll(" ", "").split(",");
            if (split.length > 0) {
                nonFuzzySearchColumn.addAll(Arrays.asList(split));
            }
        }
        return true;
    }
}
