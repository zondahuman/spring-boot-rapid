package com.abin.lee.rapid.framework.api.mapper.base;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//@Component("myBatisBaseDao")
public class MyBatisBaseDaoImpl<T, PK extends Serializable>
        implements MyBatisBaseDao<T, PK> {
    private static final int BATCH_SIZE = 1000;
    private static Logger logger = Logger.getLogger(MyBatisBaseDaoImpl.class);
    public String INSERT = ".insert";
    public String INSERT_BATCH = ".insertBatch";
    public String UPDATE = ".update";
    public String UPDATE_BATCH = ".updateBatch";
    public String DELETE = ".delete";
    public String DELETE_BATCH = ".deleteBatch";
    public String GETBYID = ".getById";
    public String COUNT = ".findPage_count";
    public String PAGESELECT = ".findPage";

    @Autowired
    @Qualifier("sqlSessionFactory")
    protected SqlSessionFactory sqlSessionFactory;

    @Autowired
    @Qualifier("sqlSessionTemplate")
    protected SqlSessionTemplate sqlSessionTemplate;

//    @Autowired
//    @Qualifier("batchSqlSessionTemplate")
//    protected SqlSessionTemplate batchSqlSessionTemplate;

    private Object target;
    private Method invokingMethod;

    public int insert(T object) {
        if (object == null) {
            throw new IllegalArgumentException(" object can't null!");
        }
        return this.sqlSessionTemplate.insert(object.getClass().getName() + this.INSERT, object);
    }

    @Override
    public int insertBatch(List<T> object) {
        if (object == null) {
            throw new IllegalArgumentException(" object can't null!");
        }
        return this.sqlSessionTemplate.insert(object.get(0).getClass().getName() + this.INSERT_BATCH, object);
    }

    public List<T> findByCondition(T obj) {
        if (obj == null) {
            throw new IllegalArgumentException(" condition can't null!");
        }
        return this.sqlSessionTemplate.selectList(obj.getClass().getName() + this.PAGESELECT, obj);
    }

    public List<T> findByCondition(T obj, int offset, int limit) {
        if (obj == null) {
            throw new IllegalArgumentException(" condition can't null!");
        }
        RowBounds rb = new RowBounds(offset, limit);
        return this.sqlSessionTemplate.selectList(obj.getClass().getName() + this.PAGESELECT, obj, rb);
    }

    public T findByPK(PK pk, Class<T> cls) {
        if (pk == null) {
            throw new IllegalArgumentException(" pk can't null!");
        }
        return (T) this.sqlSessionTemplate.selectOne(cls.getName() + this.GETBYID, pk);
    }

    public void update(T object) {
        if (object == null) {
            throw new IllegalArgumentException(" object can't null!");
        }
        this.sqlSessionTemplate.update(object.getClass().getName() + this.UPDATE, object);
    }

    public List<BatchResult> updateBatch(List<T> list) throws SQLException {
        return updateBatch(list, BATCH_SIZE);
    }

    public List<BatchResult> updateBatch(List<T> list, Integer batchFlushSize) throws SQLException {
        if (list == null) {
            throw new IllegalArgumentException(" object can't null!");
        }
        //TODO
/*      Configuration conf =  this.batchSqlSessionTemplate.getConfiguration();
      ManagedTransactionFactory managedTransactionFactory = new ManagedTransactionFactory();
      //执行失败，连接已关闭，可能是因为spring管理导致
//      BatchExecutor batchExecutor=new BatchExecutor(conf,managedTransactionFactory.newTransaction(batchSqlSessionTemplate.getConnection()));
      //可以run，但每次都会新建立连接，不受spring控制
      BatchExecutor batchExecutor=new BatchExecutor(conf,managedTransactionFactory.newTransaction(sqlSessionFactory.openSession(ExecutorType.BATCH,true).getConnection()));
      int i = 0;
      for (T entity : list) {
          batchExecutor.doUpdate(conf.getMappedStatement(entity.getClass().getName() + this.UPDATE_BATCH), entity);
          if (i++ > 0 && i % batchFlushSize == 0) {
              batchExecutor.flushStatements();
          }
      }
      List<BatchResult> ret =  batchExecutor.flushStatements();
      batchExecutor.clearLocalCache();
      batchExecutor.close(false);
      return ret;*/
/*      for (T entity : list) {
          this.sqlSessionTemplate.update(entity.getClass().getName() + this.UPDATE, entity);
      }
      return Collections.emptyList();*/
        SqlSession session = null;
        List<BatchResult> ret = new ArrayList<BatchResult>(list.size());
        try {
            session = sqlSessionFactory.openSession(ExecutorType.BATCH, true);
            int i = 0;
            for (T entity : list) {
                session.update(entity.getClass().getName() + this.UPDATE_BATCH, entity);
                if (i++ > 0 && i % batchFlushSize == 0) {
                    ret.addAll(session.flushStatements());
                }
            }
            ret = session.flushStatements();
        } finally {
            if (null != session) {
                session.clearCache();
                session.close();
            }
        }
        return ret;
    }

    public void delete(PK pk, Class<T> cls) {
        if (pk == null) {
            throw new IllegalArgumentException(" pk can't null!");
        }
        this.sqlSessionTemplate.delete(cls.getName() + this.DELETE, pk);
    }

    public Integer getTotalCount(T object) {
        if (object == null) {
            throw new IllegalArgumentException(" condition can't null!");
        }
        Object obj = this.sqlSessionTemplate.selectOne(object.getClass().getName() + this.COUNT, object);
        if (obj != null) {
            return Integer.valueOf(Integer.parseInt(obj.toString()));
        }
        return Integer.valueOf(0);
    }

    public int insertBatch(Class<T> cls, List<T> domainList) {
        return this.sqlSessionTemplate.insert(cls.getName() + this.INSERT_BATCH, domainList);
    }

    public int deleteBatch(Class<T> cls, List<T> domainList) {
        return this.sqlSessionTemplate.delete(cls.getName() + this.DELETE_BATCH, domainList);
    }


    private String getStatment(String statement) {
        if (StringUtils.isEmpty(statement)) {
            return getStatement();
        }
        return statement;
    }

    public String getStatement() {
        if ((this.target == null) || (this.invokingMethod == null))
            return null;
        Class c = this.target.getClass();
        Method[] methods = c.getMethods();
        String namespace = this.target.getClass().getName();
        StringBuffer bufId = new StringBuffer(this.invokingMethod.getName());
        for (Method m : methods) {
            if ((m.getName().equals(this.invokingMethod.getName())) &&
                    (!m.equals(this.invokingMethod))) {
                Class[] s = m.getParameterTypes();
                if ((s != null) && (s.length > 0)) {
                    for (Class z : s) {
                        bufId.append("_").append(z.getSimpleName());
                    }
                }
            }
        }
        return namespace + "." + bufId.toString();
    }

    public Object getTarget() {
        return this.target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getInvokingMethod() {
        return this.invokingMethod;
    }

    public void setInvokingMethod(Method invokingMethod) {
        this.invokingMethod = invokingMethod;
    }

    public SqlSessionTemplate getCurSqlSessionTemplate() {
        return this.sqlSessionTemplate;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public SqlSessionFactory getCurSqlSessionFactory() {
        return this.sqlSessionFactory;
    }

//    public SqlSessionTemplate getBatchSqlSessionTemplate() {
//        return batchSqlSessionTemplate;
//    }
//
//    public void setBatchSqlSessionTemplate(SqlSessionTemplate batchSqlSessionTemplate) {
//        this.batchSqlSessionTemplate = batchSqlSessionTemplate;
//    }

    private void setAutoCommit(Connection con, boolean autoCommit) {
        try {
            if (con != null)
                con.setAutoCommit(autoCommit);
        } catch (Exception localException) {
            logger.info("连接设置自动提交失败！");
        }
    }

    public int updateExp(T paramT) {
        // TODO Auto-generated method stub
        return 0;
    }

    public int deleteExp(PK paramPK, Class<T> paramClass) {
        // TODO Auto-generated method stub
        return 0;
    }

}


