<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage};
<#include "/java_imports.include">
import java.util.HashMap;
import java.util.Map;

public class ${className}{
 
	  // 【in 查询使用，形式：1,2,3,4,5】
	 private java.lang.String ids;
	 // 【排序  使用】
	 private java.lang.String sortColumns;
	 // 【like  使用】
	 private Map<String, Integer> selectType = new HashMap<String, Integer>();
	 
	<#list table.columns as column>
 
	 /**
     * 描述:${column.columnAlias!}     
     * 字段: ${column.sqlName}  ${column.sqlTypeName}(${column.size})  
     */	
	private ${column.javaType} ${column.columnNameLower};

	<#if column.isDateTimeColumn>
	//【非数据库字段，查询时使用】
	private ${column.javaType} ${column.columnNameLower}Begin;
	//【非数据库字段，查询时使用】
	private ${column.javaType} ${column.columnNameLower}End;
	</#if> 
	</#list>
 
 
	<@generateConstructor className/>
	<@generateJavaColumns/>
	<@generateJavaOneToMany/>
	<@generateJavaManyToOne/>
 
}

<#macro generateJavaColumns>
	public java.lang.String getIds() {
	    return ids;
	}
	
	public void setIds(java.lang.String ids) {
	    this.ids = ids;
	}
	
	public java.lang.String getSortColumns() {
	    return sortColumns;
	}
	
	public void setSortColumns(java.lang.String sortColumns) {
	    this.sortColumns = sortColumns;
	}
	
	public Map<String, Integer> getSelectType() {
		return selectType;
	}

	public void setSelectType(Map<String, Integer> selectType) {
		this.selectType = selectType;
	}
	
	<#list table.columns as column>
	public void set${column.columnName}(${column.javaType} ${column.columnNameLower}) {
		this.${column.columnNameLower} = ${column.columnNameLower};
	}
	
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	
	 <#if column.isDateTimeColumn>
    public void set${column.columnName}Begin(${column.javaType} ${column.columnNameLower}Begin) {
		this.${column.columnNameLower}Begin = ${column.columnNameLower}Begin;
	}
	
	public ${column.javaType} get${column.columnName}Begin() {
		return this.${column.columnNameLower}Begin;
	}
	
	public void set${column.columnName}End(${column.javaType} ${column.columnNameLower}End) {
		this.${column.columnNameLower}End = ${column.columnNameLower}End;
	}
	
	public ${column.javaType} get${column.columnName}End() {
		return this.${column.columnNameLower}End;
	}	
	 </#if> 
    
    
	</#list>
</#macro>

<#macro generateJavaOneToMany>
	<#list table.exportedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private Set ${fkPojoClassVar}s = new HashSet(0);
	public void set${fkPojoClass}s(Set<${fkPojoClass}> ${fkPojoClassVar}){
		this.${fkPojoClassVar}s = ${fkPojoClassVar};
	}
	
	public Set<${fkPojoClass}> get${fkPojoClass}s() {
		return ${fkPojoClassVar}s;
	}
	</#list>
</#macro>

<#macro generateJavaManyToOne>
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private ${fkPojoClass} ${fkPojoClassVar};
	
	public void set${fkPojoClass}(${fkPojoClass} ${fkPojoClassVar}){
		this.${fkPojoClassVar} = ${fkPojoClassVar};
	}
	
	public ${fkPojoClass} get${fkPojoClass}() {
		return ${fkPojoClassVar};
	}
	</#list>
</#macro>
