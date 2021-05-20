package io.github.xbeeant.mybatis.extend.plugins;

import io.github.xbeeant.mybatis.extend.PluginUtil;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2020/10/5
 */
public class XstudioModelPlugin extends PluginAdapter {

    private static final List<String> ignoreFields = Arrays.asList(
            "createBy", "updateBy", "createAt", "updateAt"
    );

    private static final List<String> ignoreMethods = Arrays.asList(
            "getCreateBy", "getUpdateBy", "getCreateAt", "getUpdateAt"
            , "setCreateBy", "setUpdateBy", "setCreateAt", "setUpdateAt"
    );

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if (ignoreFields.contains(field.getName())) {
            return false;
        }
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if (ignoreMethods.contains(method.getName())) {
            return false;
        }
        return super.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if (ignoreMethods.contains(method.getName())) {
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
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }
}
