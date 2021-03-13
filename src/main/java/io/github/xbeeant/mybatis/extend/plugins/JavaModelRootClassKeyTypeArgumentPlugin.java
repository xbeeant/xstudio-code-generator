package io.github.xbeeant.mybatis.extend.plugins;

import io.github.xbeeant.mybatis.extend.PluginUtil;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;
import java.util.Optional;

/**
 * @author xiaobiao
 * @version 2020/10/3
 */
public class JavaModelRootClassKeyTypeArgumentPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Optional<FullyQualifiedJavaType> superClass = topLevelClass.getSuperClass();
        if (superClass.isPresent()) {
            FullyQualifiedJavaType fullyQualifiedJavaType = superClass.get();
            fullyQualifiedJavaType.addTypeArgument(PluginUtil.getKeyFqjt(introspectedTable));
        }
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }
}
