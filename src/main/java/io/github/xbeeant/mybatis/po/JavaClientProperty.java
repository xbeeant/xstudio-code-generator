package io.github.xbeeant.mybatis.po;

import java.io.Serializable;

/**
 * @author xiaobiao
 * @version 2020/10/2
 */
public class JavaClientProperty implements Serializable {
    private static final long serialVersionUID = -681368979067143782L;
    /**
     * This is the package where the generated classes will be placed. In the default generator, the property
     * "enableSubPackages" controls how the actual package is calculated. If true, then the calculated package will be the
     * targetPackage plus sub packages for the table's catalog and schema if they exist. If false (the default) then the
     * calculated package will be exactly what is specified in the targetPackage attribute. MyBatis Generator will create
     * folders as required for the generated packages.
     */
    private String targetPackage;
    /**
     * This is used to specify a target project for the generated objects. When running in the Eclipse environment, this
     * specifies the project and source folder where the objects will be saved. In other environments, this value should
     * be an existing directory on the local file system. MyBatis Generator will not create this directory if it does not
     * exist.
     */
    private String targetProject;

    private String type = "XMLMAPPER";

    private String rootInterface;

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRootInterface() {
        return rootInterface;
    }

    public void setRootInterface(String rootInterface) {
        this.rootInterface = rootInterface;
    }
}
