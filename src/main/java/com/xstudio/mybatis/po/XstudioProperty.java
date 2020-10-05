package com.xstudio.mybatis.po;

/**
 * @author xiaobiao
 * @version 2020/10/5
 */
public class XstudioProperty {
    private String serviceRootInterface;

    private String serviceTargetPackage;

    private String serviceImplementRootInterface;

    private String serviceImplementTargetPackage;

    private String rootClient;

    private String idGenerator;

    private String responseObject;

    public String getServiceRootInterface() {
        return serviceRootInterface;
    }

    public void setServiceRootInterface(String serviceRootInterface) {
        this.serviceRootInterface = serviceRootInterface;
    }

    public String getServiceTargetPackage() {
        return serviceTargetPackage;
    }

    public void setServiceTargetPackage(String serviceTargetPackage) {
        this.serviceTargetPackage = serviceTargetPackage;
    }

    public String getServiceImplementRootInterface() {
        return serviceImplementRootInterface;
    }

    public void setServiceImplementRootInterface(String serviceImplementRootInterface) {
        this.serviceImplementRootInterface = serviceImplementRootInterface;
    }

    public String getServiceImplementTargetPackage() {
        return serviceImplementTargetPackage;
    }

    public void setServiceImplementTargetPackage(String serviceImplementTargetPackage) {
        this.serviceImplementTargetPackage = serviceImplementTargetPackage;
    }

    public String getRootClient() {
        return rootClient;
    }

    public void setRootClient(String rootClient) {
        this.rootClient = rootClient;
    }

    public String getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(String idGenerator) {
        this.idGenerator = idGenerator;
    }

    public String getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(String responseObject) {
        this.responseObject = responseObject;
    }
}
