package com.xstudio.mybatis.extend.plugins;

import com.xstudio.mybatis.extend.PluginUtil;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;

import java.util.List;
import java.util.Set;

/**
 * @author xiaobiao
 * @version 2020/10/3
 */
public class JavaClientRootInterfaceKeyTypeArgumentPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        Set<FullyQualifiedJavaType> superInterfaceTypes = interfaze.getSuperInterfaceTypes();
        for (FullyQualifiedJavaType superInterfaceType : superInterfaceTypes) {
            if (null == superInterfaceType.getTypeArguments() || 0 == superInterfaceType.getTypeArguments().size()) {
                superInterfaceType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
                FullyQualifiedJavaType primaryKeyTypeFqjt = PluginUtil.getKeyFqjt(introspectedTable);
                superInterfaceType.addTypeArgument(primaryKeyTypeFqjt);
            }
        }

        return super.clientGenerated(interfaze, introspectedTable);
    }
}
