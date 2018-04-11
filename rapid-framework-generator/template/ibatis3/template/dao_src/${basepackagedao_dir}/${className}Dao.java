<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackagedao};
import ${basepackage}.${className};
import com.abin.lee.rapid.framework.api.mapper.base.MyBatisBaseDao;
<#include "/java_imports.include">

public interface ${className}Dao extends MyBatisBaseDao<${className}, ${table.idColumn.javaType}> {}
