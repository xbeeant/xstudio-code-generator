package io.github.xbeeant.mybatis.po;

import java.io.Serializable;

/**
 * @author xiaobiao
 * @version 2020/10/2
 */
public class ConnectionProperty implements Serializable {

    private static final long serialVersionUID = 2737258169189898218L;
    /**
     * The JDBC connection URL used to access the database.
     */
    private String url;

    /**
     * The fully qualified class name for the JDBC driver used to access the database.
     */
    private String driverClass;

    /**
     * The password used to connect to the database.
     */
    private String password;

    /**
     * The user ID used to connect to the database.
     */
    private String user;


    /**
     * get field The JDBC connection URL used to access the database.
     *
     * @return url The JDBC connection URL used to access the database.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * set field The JDBC connection URL used to access the database.
     *
     * @param url The JDBC connection URL used to access the database.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * get field The fully qualified class name for the JDBC driver used to access the database.
     *
     * @return driverClass The fully qualified class name for the JDBC driver used to access the database.
     */
    public String getDriverClass() {
        return this.driverClass;
    }

    /**
     * set field The fully qualified class name for the JDBC driver used to access the database.
     *
     * @param driverClass The fully qualified class name for the JDBC driver used to access the database.
     */
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    /**
     * get field The password used to connect to the database.
     *
     * @return password The password used to connect to the database.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * set field The password used to connect to the database.
     *
     * @param password The password used to connect to the database.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get field The user ID used to connect to the database.
     *
     * @return user The user ID used to connect to the database.
     */
    public String getUser() {
        return this.user;
    }

    /**
     * set field The user ID used to connect to the database.
     *
     * @param user The user ID used to connect to the database.
     */
    public void setUser(String user) {
        this.user = user;
    }
}
