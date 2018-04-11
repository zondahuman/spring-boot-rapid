package com.abin.lee.rapid.framework.api.mapper.base;


import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public abstract interface MyBatisBaseDao<T, PK extends Serializable> {

    public abstract int insert(T paramT);

    public abstract int insertBatch(List<T> paramTs);

    public abstract List<T> findByCondition(T paramT);

    public abstract List<T> findByCondition(T paramT, int paramInt1, int paramInt2);

    public abstract T findByPK(PK paramPK, Class<T> paramClass);

    public abstract void update(T paramT);

    public abstract int updateExp(T paramT);

    public abstract void delete(PK paramPK, Class<T> paramClass);

    public abstract int deleteExp(PK paramPK, Class<T> paramClass);

    public abstract Integer getTotalCount(T paramT);

    public abstract int insertBatch(Class<T> cls, List<T> paramList);

    public abstract List<BatchResult> updateBatch(List<T> domainList) throws SQLException;

    public abstract List<BatchResult> updateBatch(List<T> paramList, Integer batchFlushSize) throws SQLException;

    public abstract int deleteBatch(Class<T> cls, List<T> domainList);

    public abstract SqlSessionTemplate getCurSqlSessionTemplate();

    public abstract SqlSessionFactory getCurSqlSessionFactory();
}
