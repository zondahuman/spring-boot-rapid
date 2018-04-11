package com.abin.lee.rapid.framework.api.service.impl;

import com.abin.lee.rapid.framework.api.mapper.OrderDao;
import com.abin.lee.rapid.framework.api.model.Order;
import com.abin.lee.rapid.framework.api.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abin on 2018/2/25 0:12.
 * sharding-dbtable
 * com.abin.lee.sharding.dbtable.api.service.impl
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;



    @Override
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    @Transactional(propagation= Propagation.REQUIRED)
    public void add(Order model) {
        this.orderDao.insert(model);
//        if(true)
//            throw new RuntimeException("throw an own define exception ! ");
    }



    @Override
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    @Transactional(propagation= Propagation.REQUIRED)
    public void update(Long id, Order model) {
        Order order = this.orderDao.findByPK(id, Order.class);
        if(ObjectUtils.notEqual(null, order)){
            order.setBusinessId(model.getBusinessId());
            order.setId(model.getId());
            order.setOrderName(model.getOrderName());
            order.setUpdateTime(model.getUpdateTime());
            order.setVersion(model.getVersion());
            this.orderDao.update(order);
        }
    }

    @Override
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    @Transactional(propagation= Propagation.REQUIRED)
    public void delete(Long id) {
        this.orderDao.delete(id, Order.class);
    }

    @Override
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    @Transactional(propagation= Propagation.REQUIRED)
    public void deleteByParams(Long id, String orderName) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("standardBaseId", deleteByStdId);
//        int deleteRows = this.sqlSessionTemplate.delete(HorseAddress.class.getName()+".deleteByStdId", map);


        this.orderDao.delete(id, Order.class);

    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true)
    public Order findById(Long id) {
        return this.orderDao.findByPK(id, Order.class);
    }


    /**
     * 严格来说这个方法不对，这里只是为了测试下使用方法，正常来说这个方法是通过ES或者Hive来实现
     * @param id
     * @param orderName
     * @return
     */
    @Override
//    @ShardingCache(expiry=7200)//缓存2小时
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true)
    public List<Order> findByParams(Long id, String orderName) {
        Order param = new Order();
        param.setOrderName(orderName);
        List<Order> list = this.orderDao.findByCondition(param);
        return list;
    }

    /**
     * 严格来说这个方法不对，这里只是为了测试下使用方法，正常来说这个方法是通过ES或者Hive来实现
     * @return
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true)
    public List<Order> findAll() {
        Order param = new Order();
        List<Order> businessList = this.orderDao.findByCondition(param);
        return businessList;
    }

    /**
     * 严格来说这个方法不对，这里只是为了测试下使用方法，正常来说这个方法是通过ES或者Hive来实现
     * @param id
     * @param orderName
     * @return
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true)
    public int countByParams(Long id, String orderName) {
        Order param = new Order();
        param.setOrderName(orderName);
        Integer total = this.orderDao.getTotalCount(param);
        return total;
    }




}
