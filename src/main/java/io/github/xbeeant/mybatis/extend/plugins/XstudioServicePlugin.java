package io.github.xbeeant.mybatis.extend.plugins;

import io.github.xbeeant.mybatis.extend.PluginUtil;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2020/10/5
 */
public class XstudioServicePlugin extends PluginAdapter {
    private static final String OVERRIDE = "@Override";
    private final FullyQualifiedJavaType autowiredAnnotationFqjt = new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired");
    private String serviceRootInterface;
    private String serviceTargetPackage;
    private String serviceImplementRootInterface;
    private String serviceImplementTargetPackage;
    private String rootClient;
    private String idGenerator;
    private String responseObject;
    private String paramRecord;

    @Override
    public boolean validate(List<String> warnings) {
        serviceRootInterface = properties.getProperty("serviceRootInterface");
        serviceTargetPackage = properties.getProperty("serviceTargetPackage");
        serviceImplementRootInterface = properties.getProperty("serviceImplementRootInterface");
        serviceImplementTargetPackage = properties.getProperty("serviceImplementTargetPackage");
        rootClient = properties.getProperty("rootClient");
        idGenerator = properties.getProperty("idGenerator");
        responseObject = properties.getProperty("responseObject");
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType baseRecordTypeFqjt = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        paramRecord = JavaBeansUtil.getCamelCaseString(baseRecordTypeFqjt.getShortNameWithoutTypeArguments(), false);
        super.initialized(introspectedTable);
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> files = new ArrayList<>();
        // service interface
        files.add(generateServiceInterfaceFile(introspectedTable));
        // service implement
        files.add(generateServiceImplement(introspectedTable));

        return files;
    }

    private GeneratedJavaFile generateServiceImplement(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType baseRecordTypeFqjt = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        /*
         * service 继承实现
         */
        FullyQualifiedJavaType rootClassFqjt = new FullyQualifiedJavaType(serviceImplementRootInterface);
        rootClassFqjt.addTypeArgument(baseRecordTypeFqjt);
        rootClassFqjt.addTypeArgument(PluginUtil.getKeyFqjt(introspectedTable));

        String serviceImplString = serviceImplementTargetPackage + "." + baseRecordTypeFqjt.getShortNameWithoutTypeArguments() + "ServiceImpl";
        FullyQualifiedJavaType serviceImpl = new FullyQualifiedJavaType(serviceImplString);
        TopLevelClass topLevelClass = new TopLevelClass(serviceImpl);
        topLevelClass.setSuperClass(rootClassFqjt);
        topLevelClass.addImportedType(rootClassFqjt);

        topLevelClass.addAnnotation("@Service");
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));

        topLevelClass.addImportedType(rootClassFqjt);

        topLevelClass.setVisibility(JavaVisibility.PUBLIC);

        context.getCommentGenerator().addModelClassComment(topLevelClass, introspectedTable);

        // 设置父类
        String serviceInterfaceString = serviceTargetPackage + ".I" + baseRecordTypeFqjt.getShortNameWithoutTypeArguments() + "Service";
        FullyQualifiedJavaType serviceInterfaceFqjt = new FullyQualifiedJavaType(serviceInterfaceString);
        topLevelClass.addSuperInterface(serviceInterfaceFqjt);
        topLevelClass.addImportedType(serviceInterfaceFqjt);

        // with blobs
        List<IntrospectedColumn> blobColumns = introspectedTable.getBLOBColumns();
        if (!blobColumns.isEmpty()) {
            Method isWithBlobs = new Method("isWithBlobs");
            isWithBlobs.addAnnotation(OVERRIDE);
            isWithBlobs.setVisibility(JavaVisibility.PUBLIC);
            isWithBlobs.addBodyLine("return true;");
            isWithBlobs.setReturnType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
            topLevelClass.addMethod(isWithBlobs);
        }

        // dao repositoryAnnotation
        FullyQualifiedJavaType mybatis3JavaMapperType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());

        String shortName = mybatis3JavaMapperType.getShortName();

        String daoInterfaceAlias = shortName.substring(0, 1).toLowerCase() + shortName.substring(1);
        Field dao = new Field(daoInterfaceAlias, mybatis3JavaMapperType);
        dao.addAnnotation("@Autowired");
        dao.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(dao);
        topLevelClass.addImportedType(autowiredAnnotationFqjt);

        // getDao Method
        FullyQualifiedJavaType rootClientFqjt = new FullyQualifiedJavaType(rootClient);
        rootClientFqjt.addTypeArgument(baseRecordTypeFqjt);
        rootClientFqjt.addTypeArgument(PluginUtil.getKeyFqjt(introspectedTable));

        Method getDao = new Method("getRepositoryDao");
        getDao.addAnnotation(OVERRIDE);
        getDao.setVisibility(JavaVisibility.PUBLIC);
        getDao.setReturnType(rootClientFqjt);
        topLevelClass.addImportedType(mybatis3JavaMapperType);

        getDao.addBodyLine("return this.".concat(daoInterfaceAlias).concat(";"));
        topLevelClass.addMethod(getDao);
        topLevelClass.addImportedType(rootClient);

        // setKey Method
        Method setDefaults = new Method("setDefaults");
        setDefaults.addAnnotation(OVERRIDE);
        setDefaults.setVisibility(JavaVisibility.PUBLIC);
        setDefaults.addParameter(new Parameter(baseRecordTypeFqjt, paramRecord));
        topLevelClass.addImportedType(baseRecordTypeFqjt);


        // getKeyValue Method
        topLevelClass.addImportedType(baseRecordTypeFqjt);

        boolean noTransferFlag = false;

        StringBuilder annotationString = new StringBuilder();
        annotationString.append("@UnAspectEscapeSpecialString(fields = {\"");
        List<IntrospectedColumn> baseColumns = introspectedTable.getBaseColumns();
        ArrayList<String> strings = new ArrayList<>();
        for (IntrospectedColumn introspectedColumn : baseColumns) {
            // 不转义
            if (introspectedColumn.getRemarks().contains("noTransfer")) {
                noTransferFlag = true;
                strings.add(introspectedColumn.getActualColumnName());
            }
        }
        int index = 0;
        for (String columName : strings) {
            index += 1;
            if (index != strings.size()) {
                annotationString.append(columName);
                annotationString.append("\",");
            } else {
                annotationString.append(columName);
                annotationString.append("\"");
            }
        }
        annotationString.append("})");
        if (noTransferFlag) {
            //重写insertSelective方法
            Method insertSelective = new Method("insertSelective");
            insertSelective.setVisibility(JavaVisibility.PUBLIC);
            FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(responseObject);
            FullyQualifiedJavaType model = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
            returnType.addTypeArgument(model);
            insertSelective.setReturnType(returnType);
            insertSelective.addParameter(new Parameter(baseRecordTypeFqjt, paramRecord, annotationString.toString()));
            topLevelClass.addImportedType(baseRecordTypeFqjt);
            insertSelective.addAnnotation(OVERRIDE);
            StringBuilder stringBuilder = new StringBuilder("");
            stringBuilder.append("return super.insertSelective(record)");
            topLevelClass.addMethod(insertSelective);
            topLevelClass.addImportedType(new FullyQualifiedJavaType(responseObject));
            insertSelective.addBodyLine(stringBuilder.toString());
            //重写updateByPrimaryKeySelective方法
            Method updateByPrimaryKeySelective = new Method("updateByPrimaryKeySelective");
            updateByPrimaryKeySelective.setVisibility(JavaVisibility.PUBLIC);
            FullyQualifiedJavaType returnType2 = new FullyQualifiedJavaType(responseObject);
            returnType2.addTypeArgument(model);
            updateByPrimaryKeySelective.setReturnType(returnType);
            updateByPrimaryKeySelective.addParameter(new Parameter(baseRecordTypeFqjt, paramRecord, annotationString.toString()));
            updateByPrimaryKeySelective.addAnnotation(OVERRIDE);
            StringBuilder stringBuilder2 = new StringBuilder("");
            stringBuilder2.append("return super.updateByPrimaryKeySelective(record)");
            topLevelClass.addMethod(updateByPrimaryKeySelective);
            updateByPrimaryKeySelective.addBodyLine(stringBuilder2.toString());
        }
        List<IntrospectedColumn> keyColumns = introspectedTable.getPrimaryKeyColumns();
        if (!keyColumns.isEmpty()) {

            for (IntrospectedColumn keyColumn : keyColumns) {
                String key = JavaBeansUtil.getCamelCaseString(keyColumn.getActualColumnName(), true);
                if ("String".equals(keyColumn.getFullyQualifiedJavaType().getShortName())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("if (" + paramRecord + ".get");
                    sb.append(key);
                    sb.append("() == null || \"\".equals(" + paramRecord + ".get");
                    sb.append(key);
                    sb.append("())) {");
                    setDefaults.addBodyLine(sb.toString());
                    sb = new StringBuilder();
                    sb.append(paramRecord + ".set");
                    sb.append(key);
                    sb.append("(");
                    sb.append("IdWorker.getIdString());");
                    setDefaults.addBodyLine(sb.toString());
                    setDefaults.addBodyLine("}");
                } else if ("Long".equals(keyColumn.getFullyQualifiedJavaType().getShortName())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("if (" + paramRecord + ".get");
                    sb.append(key);
                    sb.append("() == null) {");
                    setDefaults.addBodyLine(sb.toString());
                    sb = new StringBuilder();
                    sb.append(paramRecord + ".set");
                    sb.append(key);
                    sb.append("(");
                    sb.append("IdWorker.getId());");
                    setDefaults.addBodyLine(sb.toString());
                    setDefaults.addBodyLine("}");
                    topLevelClass.addImportedType(idGenerator);
                } else {
                    setDefaults.addBodyLine("");
                }
            }
        }

        topLevelClass.addMethod(setDefaults);

        return new GeneratedJavaFile(topLevelClass, context.getJavaModelGeneratorConfiguration()
                .getTargetProject(),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
    }

    private GeneratedJavaFile generateServiceInterfaceFile(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType baseRecordTypeFqjt = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        String serviceInterfaceString = serviceTargetPackage + ".I" + baseRecordTypeFqjt.getShortNameWithoutTypeArguments() + "Service";
        FullyQualifiedJavaType serviceInterface = new FullyQualifiedJavaType(serviceInterfaceString);
        Interface interfaze = new Interface(serviceInterface);
        interfaze.setVisibility(JavaVisibility.PUBLIC);

        context.getCommentGenerator().addJavaFileComment(interfaze);

        FullyQualifiedJavaType primaryKeyTypeFqjt = PluginUtil.getKeyFqjt(introspectedTable);
        interfaze.addImportedType(primaryKeyTypeFqjt);

        FullyQualifiedJavaType serviceFqjt = new FullyQualifiedJavaType(serviceRootInterface);
        serviceFqjt.addTypeArgument(baseRecordTypeFqjt);
        serviceFqjt.addTypeArgument(PluginUtil.getKeyFqjt(introspectedTable));
        // 设置父类
        interfaze.addSuperInterface(serviceFqjt);


        interfaze.addImportedType(serviceFqjt);
        interfaze.addImportedType(baseRecordTypeFqjt);


        return new GeneratedJavaFile(interfaze, context.getJavaModelGeneratorConfiguration().getTargetProject(),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
    }
}
