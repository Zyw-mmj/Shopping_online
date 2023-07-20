package com.example.webstore.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webstore.dao.mapper.ShopOrderMapper;
import com.example.webstore.dao.model.OrderItem;
import com.example.webstore.dao.model.ShopOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShopOrderRepository extends ServiceImpl<ShopOrderMapper, ShopOrder> {

    public List<ShopOrder> getAll() {
        return list();
    }

    public void create(ShopOrder order) {
        save(order);
    }

    public void update(ShopOrder order) {
        updateById(order);
    }

    public void delete(Long id) {
        removeById(id);
    }


    public List<ShopOrder> getOrdersByUserId(Long userId) {
        return baseMapper.getOrdersByUserId(userId);
    }

    public ShopOrder getOrdersByOrderId(Long orderId) {
        return baseMapper.getOrdersByOrderId(orderId);
    }

    public void updatePayStatus(Long orderId,int status) {
        baseMapper.updatePayStatus(orderId,status);
    }

    public String getUsername(Long order_id) {
        return baseMapper.getUsername(order_id);
    }
}
