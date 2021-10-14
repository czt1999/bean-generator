package com.cztian;

import java.util.List;
import java.util.Map;

public class DataModel {

    private String packageName;

    private String className;

    private List<Map<String, String>> fields;

    private boolean useLombok;

    private boolean chain;

    private boolean allArgs;

    private boolean hasDateType;

    private boolean hasDecimalType;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Map<String, String>> getFields() {
        return fields;
    }

    public void setFields(List<Map<String, String>> fields) {
        this.fields = fields;
    }

    public boolean isUseLombok() {
        return useLombok;
    }

    public void setUseLombok(boolean useLombok) {
        this.useLombok = useLombok;
    }

    public boolean isChain() {
        return chain;
    }

    public void setChain(boolean chain) {
        this.chain = chain;
    }

    public boolean isAllArgs() {
        return allArgs;
    }

    public void setAllArgs(boolean allArgs) {
        this.allArgs = allArgs;
    }

    public boolean isHasDateType() {
        return hasDateType;
    }

    public void setHasDateType(boolean hasDateType) {
        this.hasDateType = hasDateType;
    }

    public boolean isHasDecimalType() {
        return hasDecimalType;
    }

    public void setHasDecimalType(boolean hasDecimalType) {
        this.hasDecimalType = hasDecimalType;
    }
}
