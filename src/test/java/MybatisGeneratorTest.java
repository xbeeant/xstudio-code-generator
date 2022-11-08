import io.github.xbeeant.mybatis.MybatisGenerator;
import io.github.xbeeant.mybatis.po.*;
import org.junit.jupiter.api.Test;
import org.mybatis.generator.config.DomainObjectRenamingRule;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.exception.InvalidConfigurationException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author xiaobiao
 * @version 2020/10/3
 */
class MybatisGeneratorTest {
    @Test
    void generate() throws InterruptedException, SQLException, InvalidConfigurationException, IOException {
        MybatisGenerator mybatisGenerator = new MybatisGenerator();
        Properties properties = new Properties();
        ConnectionProperty connectionProperty = new ConnectionProperty();
        connectionProperty.setDriverClass("com.mysql.cj.jdbc.Driver");
        connectionProperty.setPassword("123456");
        connectionProperty.setUrl("jdbc:mysql://localhost:3306/forum?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC&allowMultiQueries=true&autoReconnect=true");
        connectionProperty.setUser("app");
        properties.setConnectionProperty(connectionProperty);

        JavaClientProperty javaClientProperty = new JavaClientProperty();
        javaClientProperty.setTargetProject("G:\\xstudio\\forum\\forum-server\\src\\main\\java");
        javaClientProperty.setTargetPackage("com.changan.soft.forum.mapper");
        javaClientProperty.setRootInterface("io.github.xbeeant.spring.mybatis.pagehelper.IMybatisPageHelperDao");

        properties.setJavaClientProperty(javaClientProperty);

        JavaModelProperty javaModelProperty = new JavaModelProperty();
        javaModelProperty.setTargetProject("G:\\xstudio\\forum\\forum-server\\src\\main\\java");
        javaModelProperty.setTargetPackage("com.changan.soft.forum.model");
        javaModelProperty.setRootClass("io.github.xbeeant.core.BaseModelObject");
        javaModelProperty.setTrimStrings(true);
        properties.setJavaModelProperty(javaModelProperty);

        SqlMapProperty sqlMapProperty = new SqlMapProperty();
        sqlMapProperty.setTargetProject("G:\\xstudio\\forum\\forum-server\\src\\main\\resources");
        sqlMapProperty.setTargetPackage("mybatis.mysql.forum");
        properties.setSqlMapProperty(sqlMapProperty);

        TableProperty tableProperty = new TableProperty();
        tableProperty.setTableName("category");
        tableProperty.setSchema("forum");
        tableProperty.setModelType(ModelType.FLAT);

        DomainObjectRenamingRule domainObjectRenamingRule = new DomainObjectRenamingRule();
        domainObjectRenamingRule.setSearchString("^Pre");
        domainObjectRenamingRule.setReplaceString("");

        tableProperty.setRenamingRule(domainObjectRenamingRule);

        properties.setTableProperty(tableProperty);

        XstudioProperty xstudioProperty = new XstudioProperty();
        xstudioProperty.setServiceRootInterface("io.github.xbeeant.spring.mybatis.pagehelper.IMybatisPageHelperService");
        xstudioProperty.setServiceTargetPackage("com.changan.soft.forum.service");
        xstudioProperty.setServiceImplementRootInterface("com.changan.soft.forum.config.AbstractSecurityMybatisPageHelperServiceImpl");
        xstudioProperty.setServiceImplementTargetPackage("com.changan.soft.forum.service.impl");
        xstudioProperty.setRootClient("io.github.xbeeant.spring.mybatis.pagehelper.IMybatisPageHelperDao");
        xstudioProperty.setIdGenerator("io.github.xbeeant.core.IdWorker");
        xstudioProperty.setResponseObject("io.github.xbeeant.core.ApiResponse");
        xstudioProperty.setLombok("true");
        xstudioProperty.setBeginEnd("false");
        properties.setXstudioProperty(xstudioProperty);

        PluginProperty pluginProperty = new PluginProperty();

        pluginProperty.setEnableMapperAnnotationPlugin(true);
        pluginProperty.setEnableSerializablePlugin(true);
        pluginProperty.setEnableUnmergeableXmlMappersPlugin(true);
        pluginProperty.setEnableJavaClientRootInterfaceKeyTypeArgumentPlugin(true);
        pluginProperty.setEnableJavaModelRootClassKeyTypeArgumentPlugin(true);
        pluginProperty.setEnableJavaModelNoExamplePlugin(true);
        pluginProperty.setEnableXstudioMapperPlugin(true);
        pluginProperty.setEnableXstudioModelPlugin(true);
        pluginProperty.setEnableXstudioServicePlugin(true);

        properties.setPluginProperty(pluginProperty);


        mybatisGenerator.generate(properties);
    }
}
