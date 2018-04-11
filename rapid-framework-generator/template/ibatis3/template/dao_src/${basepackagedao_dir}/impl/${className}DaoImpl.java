<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackagedao}.impl;

import org.springframework.stereotype.Repository;

import com.abin.lee.rapid.framework.api.mapper.base.MyBatisBaseDaoImpl;
import ${basepackagedao}.${className}Dao;
import ${basepackage}.${className};

<#include "/java_imports.include">

@Repository
public class ${className}DaoImpl extends MyBatisBaseDaoImpl<${className},${table.idColumn.javaType}> implements  ${className}Dao{
	 

}
