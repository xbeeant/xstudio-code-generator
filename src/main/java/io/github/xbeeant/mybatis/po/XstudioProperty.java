package io.github.xbeeant.mybatis.po;

import java.io.Serializable;

/**
 * @author xiaobiao
 * @version 2020/10/5
 */
public class XstudioProperty implements Serializable {
    private static final long serialVersionUID = -6965607450900553487L;
    private String serviceRootInterface;

    private String serviceTargetPackage;

    private String serviceImplementRootInterface;

    private String serviceImplementTargetPackage;

    private String rootClient;

    private String idGenerator;

    private String responseObject;

    private String nonFuzzySearchColumn = "create_by, update_by";

    private String ignoreColumns = "";
    private String beginEnd = "";

    private String dateTime = "";

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

    public String getNonFuzzySearchColumn() {
        return nonFuzzySearchColumn;
    }

    public void setNonFuzzySearchColumn(String nonFuzzySearchColumn) {
        this.nonFuzzySearchColumn = nonFuzzySearchColumn;
    }

    public String getIgnoreColumns() {
        return ignoreColumns;
    }

    public void setIgnoreColumns(String ignoreColumns) {
        this.ignoreColumns = ignoreColumns;
    }

    public String getBeginEnd() {
        return beginEnd;
    }

    public void setBeginEnd(String beginEnd) {
        this.beginEnd = beginEnd;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
