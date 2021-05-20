package io.github.xbeeant.mybatis.po;

import org.mybatis.generator.config.Context;

/**
 * @author xiaobiao
 * @version 2021/5/20
 */
public class IfElementProperty {
    private String javaPropertyPrefix = "";

    private String textPrefix = "and";

    private String beginningDelimiter;

    private String endingDelimiter;

    private String endSuffix = ",";

    private Boolean column = true;

    private Boolean usingBeginEnd = false;

    private Boolean usingDateTime = false;

    public IfElementProperty(String textPrefix, String endSuffix, Boolean column, Context context) {
        this.textPrefix = textPrefix;
        this.endSuffix = endSuffix;
        this.column = column;
        this.beginningDelimiter = context.getBeginningDelimiter();
        this.endingDelimiter = context.getEndingDelimiter();
    }

    public IfElementProperty(Context context) {
        this.beginningDelimiter = context.getBeginningDelimiter();
        this.endingDelimiter = context.getEndingDelimiter();
    }

    public String getJavaPropertyPrefix() {
        return javaPropertyPrefix;
    }

    public void setJavaPropertyPrefix(String javaPropertyPrefix) {
        this.javaPropertyPrefix = javaPropertyPrefix;
    }

    public String getTextPrefix() {
        return textPrefix;
    }

    public void setTextPrefix(String textPrefix) {
        this.textPrefix = textPrefix;
    }

    public String getBeginningDelimiter() {
        return beginningDelimiter;
    }

    public void setBeginningDelimiter(String beginningDelimiter) {
        this.beginningDelimiter = beginningDelimiter;
    }

    public String getEndingDelimiter() {
        return endingDelimiter;
    }

    public void setEndingDelimiter(String endingDelimiter) {
        this.endingDelimiter = endingDelimiter;
    }

    public String getEndSuffix() {
        return endSuffix;
    }

    public void setEndSuffix(String endSuffix) {
        this.endSuffix = endSuffix;
    }

    public Boolean getColumn() {
        return column;
    }

    public void setColumn(Boolean column) {
        this.column = column;
    }

    public Boolean getUsingBeginEnd() {
        return usingBeginEnd;
    }

    public void setUsingBeginEnd(Boolean usingBeginEnd) {
        this.usingBeginEnd = usingBeginEnd;
    }

    public Boolean getUsingDateTime() {
        return usingDateTime;
    }

    public void setUsingDateTime(Boolean usingDateTime) {
        this.usingDateTime = usingDateTime;
    }
}
