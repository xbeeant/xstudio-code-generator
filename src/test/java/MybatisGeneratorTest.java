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
        connectionProperty.setPassword("");
        connectionProperty.setUrl("jdbc:mysql://localhost:3306/eoffice?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC&allowMultiQueries=true&autoReconnect=true");
        connectionProperty.setUser("root");
        properties.setConnectionProperty(connectionProperty);

        JavaClientProperty javaClientProperty = new JavaClientProperty();
        javaClientProperty.setTargetProject("G:\\codes\\springboot-demo\\src\\main\\java");
        javaClientProperty.setTargetPackage("com.xstudio.easy.admin.mapper");
        javaClientProperty.setRootInterface("com.xstudio.spring.mybatis.pagehelper.IMybatisPageHelperDao");

        properties.setJavaClientProperty(javaClientProperty);

        JavaModelProperty javaModelProperty = new JavaModelProperty();
        javaModelProperty.setTargetProject("G:\\codes\\springboot-demo\\src\\main\\java");
        javaModelProperty.setTargetPackage("com.xstudio.easy.admin.model");
        javaModelProperty.setRootClass("com.xstudio.core.BaseModelObject");
        javaModelProperty.setTrimStrings(true);
        properties.setJavaModelProperty(javaModelProperty);

        SqlMapProperty sqlMapProperty = new SqlMapProperty();
        sqlMapProperty.setTargetProject("G:\\codes\\springboot-demo\\src\\main\\resources");
        sqlMapProperty.setTargetPackage("mybatis.mysql.easy_admin");
        properties.setSqlMapProperty(sqlMapProperty);

        TableProperty tableProperty = new TableProperty();
        tableProperty.setTableName("eoffice_doc_template_cat");
        tableProperty.setSchema("");
        tableProperty.setCatalog("eoffice");
        tableProperty.setModelType(ModelType.FLAT);

        DomainObjectRenamingRule domainObjectRenamingRule = new DomainObjectRenamingRule();
        domainObjectRenamingRule.setSearchString("^Pre");
        domainObjectRenamingRule.setReplaceString("");

        tableProperty.setRenamingRule(domainObjectRenamingRule);

        properties.setTableProperty(tableProperty);

        XstudioProperty xstudioProperty = new XstudioProperty();
        xstudioProperty.setServiceRootInterface("com.xstudio.spring.mybatis.pagehelper.IMybatisPageHelperService");
        xstudioProperty.setServiceTargetPackage("com.xstudio.easy.admin.service");
        xstudioProperty.setServiceImplementRootInterface("com.xstudio.easy.admin.config.AbstractSecurityMybatisPageHelperServiceImpl");
        xstudioProperty.setServiceImplementTargetPackage("com.xstudio.easy.admin.service.impl");
        xstudioProperty.setRootClient("com.xstudio.spring.mybatis.pagehelper.IMybatisPageHelperDao");
        xstudioProperty.setIdGenerator("com.xstudio.core.IdWorker");
        xstudioProperty.setResponseObject("com.xstudio.core.ApiResponse");
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
