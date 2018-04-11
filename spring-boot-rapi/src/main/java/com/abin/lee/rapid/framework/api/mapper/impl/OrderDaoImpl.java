package com.abin.lee.rapid.framework.api.mapper.impl;

import com.abin.lee.rapid.framework.api.mapper.OrderDao;
import com.abin.lee.rapid.framework.api.mapper.base.MyBatisBaseDaoImpl;
import com.abin.lee.rapid.framework.api.model.Order;
import org.springframework.stereotype.Repository;


@Repository
public class OrderDaoImpl extends MyBatisBaseDaoImpl<Order, Long> implements OrderDao {


}
