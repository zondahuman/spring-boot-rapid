package ${modues}.basedao;
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
import ${basepackage}.common.codegen.genClass.CrudDao;
import ${basepackage}.entity.${className};
import ${basepackage}.common.codegen.genClass.Sql2oHelper;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import ${basepackage}.viewModel.Page;

<#include "/java_imports.include">
public class ${className}BaseDao implements CrudDao<${className}> {
private final  String SQL_QUERY_BY_ID = "select <#list table.columns as column><#if column.isNotIdOrVersionField>${column.sqlName} as ${column.columnNameLower},</#if></#list> ${table.pkColumn.sqlName} from ${table.sqlName} where ${table.pkColumn.sqlName}=:${table.pkColumn.sqlName}";
private  String SQL_QUERYLIST = "select <#list table.columns as column><#if column.isNotIdOrVersionField>${column.sqlName} as ${column.columnNameLower},</#if></#list> ${table.pkColumn.sqlName} from ${table.sqlName} where del_flag=0 ";
private String SQL_INSERT = "insert  into ${table.sqlName}" +
        "(<#list table.columns as column><#if column.isNotIdOrVersionField>${column.sqlName},</#if></#list> ${table.pkColumn.sqlName}) " +
        "values (<#list table.columns as column><#if column.isNotIdOrVersionField>:${column.columnNameLower},</#if></#list> :${table.pkColumn.columnNameLower})";
private  final  String SQL_DELETE_BY_ID="UPDATE ${table.sqlName} SET del_flag=1 WHERE id =:id";
private String SQL_QUERYLIST_TOTAL = "select count(1) from ${table.sqlName} where del_flag=0 ";
/**
 * 根据编号获取实体
 * @param id 编号
 * @return
 */
@Override
public ${className} get(String id) {
        return Sql2oHelper.get(id, ${className}.class,SQL_QUERY_BY_ID);
        }
/**
 * 根据实体条件获取列表
 */
@Override
public List<${className}> findList(${className} entity) {
        return  getPage(1,Integer.MAX_VALUE,entity).getList();

        }
/**
 * 根据指定实体分页获取所有列表数据
 */
public Page<${className}> getPage(int pageIndex, int pageSize,${className} entity) {
        Page<${className}> result = new Page<>();
        StringBuffer sqlStr=new StringBuffer();
        StringBuffer sqlStr_Total=new StringBuffer();
        sqlStr.append(SQL_QUERYLIST);
        sqlStr_Total.append(SQL_QUERYLIST_TOTAL);
<#list table.columns as column>
        if(entity.get${column.columnNameLower?cap_first}()!=null)
        {
        sqlStr.append(" and ${column.sqlName}=:${column.columnNameLower}");
        sqlStr_Total.append(" and ${column.sqlName}=:${column.columnNameLower}");
        }
</#list>
        sqlStr.append(" order by create_date desc");
        result.setTotal(Integer.parseInt(Sql2oHelper.getScalar(entity,sqlStr_Total.toString()).toString()));
        sqlStr.append(" LIMIT " + ((pageIndex-1) * pageSize) + "," + pageSize);
        result.setList(Sql2oHelper.list(entity,sqlStr.toString()));
        return result;
        }
/**
 *  获取所有列表数据
 */
@Override
public List<${className}> findAllList() {
        return  Sql2oHelper.allList(${className}.class,SQL_QUERYLIST);
        }


/**
 * 分页获取所有列表数据
 */
public Page<${className}> getPage(int pageIndex, int pageSize) {
        Page<${className}> result = new Page<>();
        result.setTotal(Integer.parseInt(Sql2oHelper.getScalar(null,SQL_QUERYLIST_TOTAL).toString()));
        String sql= SQL_QUERYLIST + " order by create_date desc LIMIT " + ((pageIndex-1) * pageSize) + "," + pageSize;
        result.setList(Sql2oHelper.allList(${className}.class, sql));
        return result;
        }


/**
 * 插入数据
 */
@Override
public int insert(${className} entity) {
        if(entity==null)
        {
        return  0;
        }
        entity.preInsert();
        return  Sql2oHelper.insert(entity,SQL_INSERT);
        }
/**
 * 插入数据返回该数据实体
 */
public int insert(${className} entity, Function<${className}, String> function) {
        if (entity == null) {
        return 0;
        }
        entity.preInsert();
        function.apply(entity);
        return Sql2oHelper.insert(entity, SQL_INSERT);
        }
/**
 * 更新数据
 */
@Override
public int update(${className} entity) {
        int flag=0;
        if(entity==null && org.springframework.util.StringUtils.isEmpty(entity.getId()))
        {
        return  flag;
        }
        StringBuffer sqlStr=new StringBuffer();
        sqlStr.append("update ${table.sqlName} set id=:id ");
<#list table.columns as column>
        if(entity.get${column.columnNameLower?cap_first}()!=null)
        {
        sqlStr.append(",${column.sqlName}=:${column.columnNameLower}");
        }
</#list>
        sqlStr.append(" where id=:${table.pkColumn.columnNameLower}");
        flag=Sql2oHelper.update(entity,sqlStr.toString());
        return flag;
        }
/**
 * 根据编号删除数据
 */
@Override
public int delete(String id) {
        return Sql2oHelper.delete(id,SQL_DELETE_BY_ID);
        }

@Override
public int delete(${className} entity) {

        return 0;
        }

        }