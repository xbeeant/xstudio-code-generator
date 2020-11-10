package com.xstudio.mybatis.po;

import java.io.Serializable;

/**
 * 插件属性
 *
 * @author huangxiaobiao
 * @date 2020/11/06
 */
public class PluginProperty implements Serializable {

    private boolean enableMapperAnnotationPlugin = true;

    private boolean enableSerializablePlugin = true;

    private boolean enableUnmergeableXmlMappersPlugin = true;

    private boolean enableJavaClientRootInterfaceKeyTypeArgumentPlugin = true;

    private boolean enableJavaModelRootClassKeyTypeArgumentPlugin = true;

    private boolean enableJavaModelNoExamplePlugin = true;

    private boolean enableXstudioMapperPlugin = true;

    private boolean enableXstudioModelPlugin = true;

    private boolean enableXstudioServicePlugin = true;

    public boolean isEnableMapperAnnotationPlugin() {
        return enableMapperAnnotationPlugin;
    }

    public void setEnableMapperAnnotationPlugin(boolean enableMapperAnnotationPlugin) {
        this.enableMapperAnnotationPlugin = enableMapperAnnotationPlugin;
    }

    public boolean isEnableSerializablePlugin() {
        return enableSerializablePlugin;
    }

    public void setEnableSerializablePlugin(boolean enableSerializablePlugin) {
        this.enableSerializablePlugin = enableSerializablePlugin;
    }

    public boolean isEnableUnmergeableXmlMappersPlugin() {
        return enableUnmergeableXmlMappersPlugin;
    }

    public void setEnableUnmergeableXmlMappersPlugin(boolean enableUnmergeableXmlMappersPlugin) {
        this.enableUnmergeableXmlMappersPlugin = enableUnmergeableXmlMappersPlugin;
    }

    public boolean isEnableJavaClientRootInterfaceKeyTypeArgumentPlugin() {
        return enableJavaClientRootInterfaceKeyTypeArgumentPlugin;
    }

    public void setEnableJavaClientRootInterfaceKeyTypeArgumentPlugin(boolean enableJavaClientRootInterfaceKeyTypeArgumentPlugin) {
        this.enableJavaClientRootInterfaceKeyTypeArgumentPlugin = enableJavaClientRootInterfaceKeyTypeArgumentPlugin;
    }

    public boolean isEnableJavaModelRootClassKeyTypeArgumentPlugin() {
        return enableJavaModelRootClassKeyTypeArgumentPlugin;
    }

    public void setEnableJavaModelRootClassKeyTypeArgumentPlugin(boolean enableJavaModelRootClassKeyTypeArgumentPlugin) {
        this.enableJavaModelRootClassKeyTypeArgumentPlugin = enableJavaModelRootClassKeyTypeArgumentPlugin;
    }

    public boolean isEnableJavaModelNoExamplePlugin() {
        return enableJavaModelNoExamplePlugin;
    }

    public void setEnableJavaModelNoExamplePlugin(boolean enableJavaModelNoExamplePlugin) {
        this.enableJavaModelNoExamplePlugin = enableJavaModelNoExamplePlugin;
    }

    public boolean isEnableXstudioMapperPlugin() {
        return enableXstudioMapperPlugin;
    }

    public void setEnableXstudioMapperPlugin(boolean enableXstudioMapperPlugin) {
        this.enableXstudioMapperPlugin = enableXstudioMapperPlugin;
    }

    public boolean isEnableXstudioModelPlugin() {
        return enableXstudioModelPlugin;
    }

    public void setEnableXstudioModelPlugin(boolean enableXstudioModelPlugin) {
        this.enableXstudioModelPlugin = enableXstudioModelPlugin;
    }

    public boolean isEnableXstudioServicePlugin() {
        return enableXstudioServicePlugin;
    }

    public void setEnableXstudioServicePlugin(boolean enableXstudioServicePlugin) {
        this.enableXstudioServicePlugin = enableXstudioServicePlugin;
    }
}
