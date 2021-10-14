package ${packageName};

import com.alibaba.fastjson.annotation.JSONField;
<#if useLombok!true>
import lombok.Data;
<#if chain!false>
import lombok.experimental.Accessors;
</#if>
<#if allArgs!false>
import lombok.AllArgsConstructor;
</#if>
</#if>

import java.io.Serializable;
<#if hasDate!false>
import java.util.Date;
</#if>
<#if hasDecimal!false>
import java.math.BigDecimal;
</#if>

<#if useLombok!true>
@Data
<#if chain!false>
@Accessors(chain = true)
</#if>
<#if allArgs!false>
@AllArgsConstructor
</#if>
</#if>
public class ${className} implements Serializable {

    <#-- 输出字段 -->
    <#assign ordinal = 0>
    <#list fields as f>
    <#assign ordinal++>
    @JSONField(ordinal = ${ordinal})
    private ${f.type} ${f.name};

    </#list>
    <#-- 输出get/set方法 -->
    <#if !useLombok>
    <#-- 构造器（若有） -->
    <#if allArgs!false>
    ${className}(<#list fields as f>${f.type} ${f.name}<#if !f?is_last>, </#if></#list>) {
        <#list fields as f>
        this.${f.name} = ${f.name};
        </#list>
    }
    </#if>

    <#list fields as f>
    public <#if chain>${className}<#else>void</#if> set${f.name?cap_first}(${f.type} ${f.name}) {
        this.${f.name} = ${f.name};
        <#if chain>return this;</#if>
    }

    public ${f.type} get${f.name?cap_first}() {
        return ${f.name};
    }

    </#list>
    </#if>
}