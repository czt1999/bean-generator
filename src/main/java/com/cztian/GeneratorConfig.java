package com.cztian;

import java.util.HashMap;
import java.util.Map;

public class GeneratorConfig {

    //包名
    private String packageName = "";
    //驼峰命名
    private Boolean camelCase = true;
    //使用Lombok
    private Boolean useLombok = true;
    //链式调用
    private Boolean chainAccess = false;
    //是否生成全参构造器
    private Boolean allArgsConstructor = false;
    //输出路径
    private String output = "";
    //自定义类型映射
    private Map<String, String> typeMap;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Boolean getCamelCase() {
        return camelCase;
    }

    public void setCamelCase(Boolean camelCase) {
        this.camelCase = camelCase;
    }

    public Boolean useLombok() {
        return useLombok;
    }

    public void useLombok(Boolean useLombok) {
        this.useLombok = useLombok;
    }

    public Boolean getChainAccess() {
        return chainAccess;
    }

    public void setChainAccess(Boolean chainAccess) {
        this.chainAccess = chainAccess;
    }

    public Boolean getAllArgsConstructor() {
        return allArgsConstructor;
    }

    public void setAllArgsConstructor(Boolean allArgsConstructor) {
        this.allArgsConstructor = allArgsConstructor;
    }

    public void ofType(String type, String javaType) {
        if (null == type || null == javaType) {
            throw new IllegalArgumentException();
        }
        typeMap = new HashMap<>();
        typeMap.put(type.trim(), javaType.trim());
    }

    public String getType(String type) {
        return null == typeMap ? "" : typeMap.getOrDefault(type, "");
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
