package io.github.xbeeant.mybatis.extend.plugins;

import io.github.xbeeant.mybatis.extend.PluginUtil;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2020/10/5
 */
public class XstudioModelPlugin extends PluginAdapter {

    private static final List<String> IGNORE_FIELDS = Arrays.asList(
            "createBy", "updateBy", "createAt", "updateAt"
    );
    private static final List<String> IGNORE_METHODS = Arrays.asList(
            "getCreateBy", "getUpdateBy", "getCreateAt", "getUpdateAt"
            , "setCreateBy", "setUpdateBy", "setCreateAt", "setUpdateAt"
    );
    private Boolean usingBeginEnd = false;
    private Boolean lombok = false;

    @Override
    public boolean validate(List<String> warnings) {
        usingBeginEnd = Boolean.valueOf(properties.getProperty("usingBeginEnd"));
        lombok = Boolean.valueOf(properties.getProperty("lombok"));
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if (IGNORE_FIELDS.contains(field.getName())) {
            return false;
        }
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if (IGNORE_METHODS.contains(method.getName()) || lombok) {
            return false;
        }
        return super.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if (IGNORE_METHODS.contains(method.getName()) || lombok) {
            return false;
        }
        return super.modelSetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Method valueOfKey = new Method("valueOfKey");
        valueOfKey.setVisibility(JavaVisibility.PUBLIC);
        valueOfKey.setReturnType(PluginUtil.getKeyFqjt(introspectedTable));
        valueOfKey.addAnnotation("@Override");
        if (!introspectedTable.getPrimaryKeyColumns().isEmpty()) {
            if (introspectedTable.getPrimaryKeyColumns().size() == 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("return ");
                sb.append(introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty());
                sb.append(";");
                valueOfKey.addBodyLine(sb.toString());
            } else {
                // todo
            }
        }

        topLevelClass.addMethod(valueOfKey);

        Method assignKeyValue = new Method("assignKeyValue");
        assignKeyValue.setVisibility(JavaVisibility.PUBLIC);
        assignKeyValue.addAnnotation("@Override");

        assignKeyValue.addParameter(new Parameter(PluginUtil.getKeyFqjt(introspectedTable), "value"));
        if (!introspectedTable.getPrimaryKeyColumns().isEmpty()) {
            if (introspectedTable.getPrimaryKeyColumns().size() == 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("this.");
                sb.append(introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty());
                sb.append(" = value;");
                assignKeyValue.addBodyLine(sb.toString());
            } else {
                // todo
            }
        }

        topLevelClass.addMethod(assignKeyValue);

        if (Boolean.TRUE.equals(usingBeginEnd)) {
            List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
            for (IntrospectedColumn column : allColumns) {
                if (!IGNORE_FIELDS.contains(column.getJavaProperty()) && PluginUtil.time.contains(column.getJdbcTypeName())) {
                    String begin = column.getJavaProperty() + "Begin";
                    String end = column.getJavaProperty() + "End";
                    Field beginField = new Field(begin, column.getFullyQualifiedJavaType());
                    beginField.setVisibility(JavaVisibility.PRIVATE);
                    Field endField = new Field(end, column.getFullyQualifiedJavaType());
                    endField.setVisibility(JavaVisibility.PRIVATE);
                    topLevelClass.addField(beginField);
                    topLevelClass.addField(endField);

                    if (!lombok) {
                        topLevelClass.addMethod(setMethod(introspectedTable, column, "Begin"));
                        topLevelClass.addMethod(setMethod(introspectedTable, column, "End"));
                        topLevelClass.addMethod(getMethod(introspectedTable, column, "Begin"));
                        topLevelClass.addMethod(getMethod(introspectedTable, column, "End"));
                    }
                }
            }
        }

        if (lombok) {
            topLevelClass.addAnnotation("@Getter");
            topLevelClass.addAnnotation("@Setter");
            topLevelClass.addAnnotation("@Builder");
            topLevelClass.addAnnotation("@AllArgsConstructor");
            topLevelClass.addAnnotation("@NoArgsConstructor");
            topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Getter"));
            topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Setter"));
            topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Builder"));

        }

        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    private Method setMethod(IntrospectedTable introspectedTable, IntrospectedColumn column, String suffix) {
        String field = JavaBeansUtil.getCamelCaseString(column.getActualColumnName(), true) + suffix;
        Method method = new Method("set" + field);
        method.setVisibility(JavaVisibility.PUBLIC);

        method.addParameter(new Parameter(column.getFullyQualifiedJavaType(), "value"));


        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(column.getActualColumnName());
        method.addJavaDocLine(sb.toString());

        method.addJavaDocLine(" *");

        Parameter parm = method.getParameters().get(0);
        sb = new StringBuilder();
        sb.append(" * @param ");
        sb.append(parm.getName());
        sb.append(" the value for ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(column.getActualColumnName());
        method.addJavaDocLine(sb.toString());

        method.addJavaDocLine(" */");

        sb = new StringBuilder();
        sb.append("this.");
        sb.append(column.getJavaProperty());
        sb.append(suffix);
        sb.append(" = value;");
        method.addBodyLine(sb.toString());

        return method;
    }

    private Method getMethod(IntrospectedTable introspectedTable, IntrospectedColumn column, String suffix) {
        String field = JavaBeansUtil.getCamelCaseString(column.getActualColumnName(), true) + suffix;
        Method method = new Method("get" + field);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(column.getFullyQualifiedJavaType());

        method.addJavaDocLine("/**");
        method.addJavaDocLine(" *");
        StringBuilder sb = new StringBuilder();
        sb.append(" the value for ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(column.getActualColumnName());
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");


        sb = new StringBuilder();
        sb.append("return ");
        sb.append(column.getJavaProperty());
        sb.append(suffix);
        sb.append(";");
        method.addBodyLine(sb.toString());

        return method;
    }
}
