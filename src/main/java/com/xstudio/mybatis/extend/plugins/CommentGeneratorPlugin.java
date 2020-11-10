package com.xstudio.mybatis.extend.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Date;
import java.util.Properties;

/**
 * @author xiaobiao
 * @version 2020/10/3
 */
public class CommentGeneratorPlugin extends DefaultCommentGenerator {
    public CommentGeneratorPlugin() {
    }

    @Override
    public void addConfigurationProperties(Properties properties) {

    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        field.addJavaDocLine("/**");

        String remarks = introspectedColumn.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            field.addJavaDocLine(" * ");
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                field.addJavaDocLine(" * " + remarkLine);
            }
        } else {
            field.addJavaDocLine(" *");

            String sb = " * " + introspectedTable.getFullyQualifiedTable() +
                    '.' +
                    introspectedColumn.getActualColumnName();
            field.addJavaDocLine(sb);
        }
        field.addJavaDocLine(" */");
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        StringBuilder sb = new StringBuilder();
        String remarks = introspectedColumn.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            method.addJavaDocLine("/**");
            sb.append(" * get field ");
            sb.append(remarks);
            method.addJavaDocLine(sb.toString());

            sb.setLength(0);
            sb.append(" * @return ");
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" ");
            sb.append(remarks);
            method.addJavaDocLine(sb.toString());

            method.addJavaDocLine(" */");
        } else {
            method.addJavaDocLine("/**");
            sb.append(" * get field ");
            sb.append(introspectedTable.getFullyQualifiedTable());
            sb.append('.');
            sb.append(introspectedColumn.getActualColumnName());
            method.addJavaDocLine(sb.toString());

            sb.setLength(0);
            sb.append(" * @return ");
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" ");
            sb.append(introspectedTable.getFullyQualifiedTable());
            sb.append('.');
            sb.append(introspectedColumn.getActualColumnName());
            method.addJavaDocLine(sb.toString());

            method.addJavaDocLine(" */");
        }
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        StringBuilder sb = new StringBuilder();
        String remarks = introspectedColumn.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            method.addJavaDocLine("/**");
            sb.append(" * set filed ");
            sb.append(remarks);
            method.addJavaDocLine(sb.toString());

            Parameter parm = method.getParameters().get(0);
            sb.setLength(0);
            sb.append(" * @param ");
            sb.append(parm.getName());
            sb.append(" the value for ");
            sb.append(remarks);
            method.addJavaDocLine(sb.toString());

            method.addJavaDocLine(" */");
        } else {
            method.addJavaDocLine("/**");
            sb.append(introspectedTable.getFullyQualifiedTable());
            sb.append('.');
            sb.append(introspectedColumn.getActualColumnName());
            method.addJavaDocLine(sb.toString());

            method.addJavaDocLine(" *");

            Parameter parm = method.getParameters().get(0);
            sb.setLength(0);
            sb.append(" * @param ");
            sb.append(parm.getName());
            sb.append(" the value for ");
            sb.append(introspectedTable.getFullyQualifiedTable());
            sb.append('.');
            sb.append(introspectedColumn.getActualColumnName());
            method.addJavaDocLine(sb.toString());

            method.addJavaDocLine(" */");
        }

    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

        StringBuilder sb = new StringBuilder();

        innerClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(sb.toString());

        innerClass.addJavaDocLine(" */");
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * ");

        String remarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                topLevelClass.addJavaDocLine(" * " + remarkLine);
            }
        } else {
            topLevelClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable());
        }


        topLevelClass.addJavaDocLine(" */");
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        StringBuilder sb = new StringBuilder();
        if (compilationUnit instanceof Interface) {
            Interface anInterface = (Interface) compilationUnit;
            anInterface.addJavaDocLine("/**");
            anInterface.addJavaDocLine(" * @author mybatis code generator");
            sb.append(" * @version ");
            sb.append(new Date().toString());
            anInterface.addJavaDocLine(sb.toString());
            anInterface.addJavaDocLine(" */");
        }

    }
}
