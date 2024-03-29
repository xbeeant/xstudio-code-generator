package io.github.xbeeant.mybatis;

import com.alibaba.fastjson.JSON;
import io.github.xbeeant.mybatis.po.*;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.util.StringUtility;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MybatisGenerator Executor
 *
 * @author xiaobiao
 * @version 2020/10/2
 */
public class MybatisGenerator {
    private static final String FALSE = "false";
    /**
     * 生成
     *
     * @param properties 属性
     * @return {@link MyBatisGenerator}
     * @throws InterruptedException          中断异常
     * @throws SQLException                  sqlexception异常
     * @throws IOException                   ioexception
     * @throws InvalidConfigurationException 无效的配置异常
     */
    public MyBatisGenerator generate(Properties properties) throws InterruptedException, SQLException, IOException, InvalidConfigurationException {
        List<String> warnings = new ArrayList<>();

        Configuration config = new Configuration();
        // context configuration
        Context context = new Context(ModelType.CONDITIONAL);
        // 	This property is used to specify the runtime target for generated code.
        context.setTargetRuntime(IntrospectedTable.TargetRuntime.MYBATIS3.name());

        context.setId("simple");
        context.addProperty("autoDelimitKeywords", "true");
        context.addProperty("beginningDelimiter", "`");
        context.addProperty("endingDelimiter", "`");
        context.addProperty("javaFileEncoding", "UTF-8");
        context.addProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING, "UTF-8");

        // <commentGenerator>
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration());

        // <jdbcConnection>
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration(properties.getConnectionProperty()));

        // <javaTypeResolver>
        context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration());

        // <sqlMapGenerator>
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration(properties.getSqlMapProperty()));

        // <javaModelGenerator>
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration(properties.getJavaModelProperty()));

        // <javaClientGenerator>
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration(properties.getJavaClientProperty()));

        // <table>
        context.addTableConfiguration(tableConfiguration(properties.getTableProperty(), context));
        // plugins
        plugins(context, properties);

        config.addContext(context);

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, properties.getCallback(), warnings);
        myBatisGenerator.generate(null);

        return myBatisGenerator;
    }

    /**
     * CommentGeneratorConfiguration
     *
     * @return {@link CommentGeneratorConfiguration}
     */
    private CommentGeneratorConfiguration commentGeneratorConfiguration() {
        CommentGeneratorConfiguration configuration = new CommentGeneratorConfiguration();
        configuration.setConfigurationType("io.github.xbeeant.mybatis.extend.plugins.CommentGeneratorPlugin");

        // This property is used to specify whether MBG will include any comments in the generated code
        //
        // false: This is the default value
        // When the property is false or unspecified, all generated elelments will include comments indicating that the
        // element is a generated element.
        //
        // true: When the property is true, no comments will be added to any generated element.
        // Warning: if you set this value to true, then all code merging will be disabled.
        //
        // If you disable all comments, you might find the UnmergeableXmlMappersPlugin useful. It will cause the generator
        // to respect the overwrite flag for XML files.
        configuration.addProperty("suppressAllComments", FALSE);
        // This property is used to specify whether MBG will include the generation timestamp in the generated comments.
        // The property supports these values:
        //
        // false: This is the default value
        // When the property is false or unspecified, all generated comments will include the timestamp when the element
        // was generated.
        //
        // true: When the property is true, no timestamp will be added to the generated comments.
        configuration.addProperty("suppressDate", FALSE);

        // This property is used to specify whether MBG will include table and column remarks from db table in the generated
        // comments. The property supports these values:
        //
        // false: This is the default value
        // When the property is false or unspecified, all generated comments will not include table and column remarks
        // from db table when the element was generated.
        //
        // true: When the property is true, table and columns remarks from db table will be added to the generated comments.
        // Warning: If suppressAllComments option is true, this option will be ignored.
        configuration.addProperty("addRemarkComments", "true");

        return configuration;
    }

    /**
     * JDBCConnectionConfiguration
     *
     * @param property 属性
     * @return {@link JDBCConnectionConfiguration}
     */
    private JDBCConnectionConfiguration jdbcConnectionConfiguration(ConnectionProperty property) {
        JDBCConnectionConfiguration configuration = new JDBCConnectionConfiguration();
        configuration.setConnectionURL(property.getUrl());
        configuration.setDriverClass(property.getDriverClass());
        configuration.setPassword(property.getPassword());
        configuration.setUserId(property.getUser());
        configuration.addProperty("nullCatalogMeansCurrent","true");
        return configuration;
    }

    /**
     * JavaTypeResolverConfiguration
     *
     * @return {@link JavaTypeResolverConfiguration}
     */
    private JavaTypeResolverConfiguration javaTypeResolverConfiguration() {
        JavaTypeResolverConfiguration configuration = new JavaTypeResolverConfiguration();
        // This property is used to specify whether MyBatis Generator should force the use of java.math.BigDecimal for
        // DECIMAL and NUMERIC fields, rather than substituting integral types when possible. The property supports these
        // values:
        //
        // false: This is the default value
        // When the property is false or unspecified, the default Java type resolver will attempt to make JDBC DECIMAL
        // and NUMERIC types easier to work with by substituting Integral types if possible. The substitution rules are
        // as follows:
        //  If the scale is greater then zero, or the length is greater than 18, then the java.math.BigDecimal type will be used
        //  If the scale is zero, and the length is 10 through 18, then the Java type resolver will substitute a java.lang.Long.
        //  If the scale is zero, and the length is 5 through 9, then the Java type resolver will substitute a java.lang.Integer.
        //  If the scale is zero, and the length is less than 5, then the Java type resolver will substitute a java.lang.Short.
        //
        // true: When the property is true, the Java type resolver will always use java.math.BigDecimal if the database
        // column is of type DECIMAL or NUMERIC.
        configuration.addProperty("forceBigDecimals", "true");

        // This property is used to specify whether MyBatis Generator should force the use of JSR-310 data types for DATE,
        // TIME, and TIMESTAMP fields, rather than using java.util.Date. If true, the types will be resolved as follows:
        //
        // JDBC Type: Resolved Java Type
        // DATE: java.time.LocalDate
        // TIME: java.time.LocalTime
        // TIMESTAMP: java.time.LocalDateTime
        // Note: the type resolver will always resolve the following JSR-310 types regardless of the value of this property:
        //
        // JDBC Type: Resolved Java Type
        // TIME_WITH_TIMEZONE: java.time.OffsetTime
        // TIMESTAMP_WITH_TIMEZONE: java.time.OffsetDateTime
        configuration.addProperty("useJSR310Types", FALSE);

        configuration.setConfigurationType("io.github.xbeeant.mybatis.extend.JavaTypeResolver");
        return configuration;
    }

    /**
     * SqlMapGeneratorConfiguration
     *
     * @param property 属性
     * @return {@link SqlMapGeneratorConfiguration}
     */
    private SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration(SqlMapProperty property) {
        SqlMapGeneratorConfiguration configuration = new SqlMapGeneratorConfiguration();
        configuration.setTargetPackage(property.getTargetPackage());
        configuration.setTargetProject(property.getTargetProject());
        return configuration;
    }

    /**
     * JavaModelGeneratorConfiguration
     *
     * @param property 属性
     * @return {@link JavaModelGeneratorConfiguration}
     */
    private JavaModelGeneratorConfiguration javaModelGeneratorConfiguration(JavaModelProperty property) {
        JavaModelGeneratorConfiguration configuration = new JavaModelGeneratorConfiguration();
        configuration.setTargetPackage(property.getTargetPackage());
        configuration.setTargetProject(property.getTargetProject());

        // This property can be used to specify a root class for all generated Java model objects. MyBatis Generator will
        // specify this value as the super class of the primary key object, if the table has a primary key, or the record
        // object otherwise. This value may be overridden by specifying the rootClass property on a Table configuration.
        // Important: If MyBatis Generator is able to load the root class, then it will not override a property in the
        // root class that exactly matches a property that would normally be generated. An exact match of a property is
        // defined as follows
        //
        // Property name matches exactly
        // Property is of the same type
        // Property has a "getter" method
        // Property has a "setter" method
        // If specified, the value of this property should be a fully qualified class name (like com.mycompany.MyRootClass).
        //
        // This property is ignored if the target runtime is "MyBatis3Kotlin"
        if (StringUtility.stringHasValue(property.getRootClass())) {
            configuration.addProperty("rootClass", property.getRootClass());
        }

        // This property is used to select whether MyBatis Generator adds code to trim the white space from character
        // fields returned from the database. This can be useful if your database stores data in CHAR fields rather than
        // VARCHAR fields. When true, MyBatis Generator will insert code to trim character fields. Can be overridden with
        // the trimStrings property in a <table> or <columnOverride> element.
        // The default value is false.
        //
        // This property is ignored if the target runtime is "MyBatis3Kotlin"
        configuration.addProperty("trimStrings", Boolean.toString(property.getTrimStrings()));

        return configuration;
    }

    /**
     * JavaClientGeneratorConfiguration
     *
     * @param property 属性
     * @return {@link JavaClientGeneratorConfiguration}
     */
    private JavaClientGeneratorConfiguration javaClientGeneratorConfiguration(JavaClientProperty property) {
        JavaClientGeneratorConfiguration configuration = new JavaClientGeneratorConfiguration();
        configuration.setTargetPackage(property.getTargetPackage());
        configuration.setTargetProject(property.getTargetProject());

        if (StringUtility.stringHasValue(property.getRootInterface())) {
            // This property can be used to specify a super interface for all generated interface objects. This value may
            // be overridden by specifying the rootInterface property on a Table configuration.
            // Important: MBG does not verify that the interface exists, or is a valid Java interface.
            //
            // If specified, the value of this property should be a fully qualified interface name (like com.mycompany.MyRootInterface).
            configuration.addProperty("rootInterface", property.getRootInterface());
        }

        configuration.setConfigurationType(property.getType());
        return configuration;
    }

    /**
     * 表配置
     *
     * @param property 属性
     * @param context  上下文
     * @return {@link TableConfiguration}
     */
    private TableConfiguration tableConfiguration(TableProperty property, Context context) {
        TableConfiguration configuration = new TableConfiguration(context);
        configuration.setTableName(property.getTableName());
        configuration.setDomainObjectName(property.getDomainObjectName());
        configuration.setCatalog(property.getCatalog());
        configuration.setSqlProviderName(property.getSqlProviderName());
        configuration.setDomainObjectRenamingRule(property.getRenamingRule());
        if (null != property.getIgnoredColumns() && !property.getIgnoredColumns().isEmpty()) {
            for (IgnoredColumn ignoredColumn : property.getIgnoredColumns()) {
                configuration.addIgnoredColumn(ignoredColumn);
            }
        }
        configuration.setCountByExampleStatementEnabled(property.getEnableCountByExample());
        configuration.setInsertStatementEnabled(property.getEnableInsert());
        configuration.setAllColumnDelimitingEnabled(true);
        configuration.setDeleteByExampleStatementEnabled(property.getEnableDeleteByExample());
        configuration.setDeleteByPrimaryKeyStatementEnabled(property.getEnableDeleteByPrimaryKey());
        configuration.setUpdateByExampleStatementEnabled(property.getEnableUpdateByExample());
        configuration.setUpdateByPrimaryKeyStatementEnabled(property.getEnableUpdateByPrimaryKey());
        configuration.setSelectByExampleStatementEnabled(property.getEnableSelectByExample());
        configuration.setSelectByPrimaryKeyStatementEnabled(property.getEnableSelectByPrimaryKey());
        if (property.isUseActualColumnNames()) {
            configuration.addProperty("useActualColumnNames", "true");
        }
        configuration.setConfiguredModelType(property.getModelType().name());
        configuration.setAllColumnDelimitingEnabled(true);
        configuration.setSelectByPrimaryKeyQueryId(property.getSelectByPrimaryKeyQueryId());

        return configuration;
    }

    /**
     * 插件
     *
     * @param context    上下文
     * @param properties 属性
     */
    private void plugins(Context context, Properties properties) {

        // ==============================
        // Mybatis Supplied Plugins
        // ==============================
        PluginConfiguration pluginConfiguration = new PluginConfiguration();

        if (properties.getPluginProperty().isEnableMapperAnnotationPlugin()) {
            // This plugin has no impact and is not needed when the target runtime in use is based on MyBatis Dynamic SQL.
            // This plugin adds the @Mapper annotation to generated mapper interfaces. This plugin should only be used in
            // MyBatis3 environments.
            pluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.MapperAnnotationPlugin");
            context.addPluginConfiguration(pluginConfiguration);
        }

        if (properties.getPluginProperty().isEnableSerializablePlugin()) {
            // This plugin has no impact and is not needed when the target runtime in use is MyBatis3Kotlin.
            //
            // This plugin adds the marker interface java.io.Serializable to the Java model objects generated by MBG. This
            // plugin also adds the serialVersionUID field to the model classes.
            //
            // Important: This is a simplistic implementation of java.io.Serializable and does not attempt to do any versioning
            // of classes.
            //
            // This plugin accepts two properties:
            //
            // addGWTInterface (optional) True/False. If true, the plugin will add the Google Web Toolkit (GWT) IsSerializable
            // interface to the model objects. The default is false.
            // suppressJavaInterface (required) True/False. If true, the plugin will NOT add the java.io.Serializable interface.
            // This is for the case where the objects should be serializable for GWT, but not in the strict Java sense. The
            // default is false.
            //
            pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
            context.addPluginConfiguration(pluginConfiguration);
        }

        if (properties.getPluginProperty().isEnableUnmergeableXmlMappersPlugin()) {
            // This plugin has no impact and is not needed when the target runtime in use is based on MyBatis Dynamic SQL.
            //
            // This plugin will disable the XML merge function for generated mapper XML files. This will cause the generator
            // to respect the overwrite flag for XML files in the same way it does for Java files - if overwrite is true,
            // then an existing file will be overwritten, else a new file will be written with a unique name.
            //
            // This plugin can be helpful if you disable all comments.
            pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.UnmergeableXmlMappersPlugin");
            context.addPluginConfiguration(pluginConfiguration);
        }

        // ==============================
        // Custom Plugins
        // ==============================
        if (properties.getPluginProperty().isEnableJavaClientRootInterfaceKeyTypeArgumentPlugin()) {
            pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType("io.github.xbeeant.mybatis.extend.plugins.JavaClientRootInterfaceKeyTypeArgumentPlugin");
            context.addPluginConfiguration(pluginConfiguration);
        }

        if (properties.getPluginProperty().isEnableJavaModelRootClassKeyTypeArgumentPlugin()) {
            pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType("io.github.xbeeant.mybatis.extend.plugins.JavaModelRootClassKeyTypeArgumentPlugin");
            context.addPluginConfiguration(pluginConfiguration);
        }

        if (properties.getPluginProperty().isEnableJavaModelNoExamplePlugin()) {
            pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType("io.github.xbeeant.mybatis.extend.plugins.JavaModelNoExamplePlugin");
            context.addPluginConfiguration(pluginConfiguration);
        }

        if (properties.getPluginProperty().isEnableXstudioMapperPlugin()) {
            pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType("io.github.xbeeant.mybatis.extend.plugins.XstudioMapperPlugin");
            pluginConfiguration.addProperty("nonFuzzySearchColumn", properties.getXstudioProperty().getNonFuzzySearchColumn());
            pluginConfiguration.addProperty("usingBeginEnd", properties.getXstudioProperty().getBeginEnd());
            pluginConfiguration.addProperty("usingDateTime", properties.getXstudioProperty().getDateTime());
            pluginConfiguration.addProperty("columns", JSON.toJSONString(properties.getXstudioProperty().getColumns()));
            context.addPluginConfiguration(pluginConfiguration);
        }

        if (properties.getPluginProperty().isEnableXstudioModelPlugin()) {
            pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType("io.github.xbeeant.mybatis.extend.plugins.XstudioModelPlugin");
            pluginConfiguration.addProperty("usingBeginEnd", properties.getXstudioProperty().getBeginEnd());
            pluginConfiguration.addProperty("lombok", properties.getXstudioProperty().getLombok());
            context.addPluginConfiguration(pluginConfiguration);
        }

        if (properties.getPluginProperty().isEnableXstudioServicePlugin()) {
            pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType("io.github.xbeeant.mybatis.extend.plugins.XstudioServicePlugin");
            pluginConfiguration.addProperty("serviceRootInterface", properties.getXstudioProperty().getServiceRootInterface());
            pluginConfiguration.addProperty("serviceTargetPackage", properties.getXstudioProperty().getServiceTargetPackage());
            pluginConfiguration.addProperty("serviceImplementRootInterface", properties.getXstudioProperty().getServiceImplementRootInterface());
            pluginConfiguration.addProperty("serviceImplementTargetPackage", properties.getXstudioProperty().getServiceImplementTargetPackage());
            pluginConfiguration.addProperty("rootClient", properties.getXstudioProperty().getRootClient());
            pluginConfiguration.addProperty("idGenerator", properties.getXstudioProperty().getIdGenerator());
            pluginConfiguration.addProperty("responseObject", properties.getXstudioProperty().getResponseObject());
        }

        context.addPluginConfiguration(pluginConfiguration);
    }
}
