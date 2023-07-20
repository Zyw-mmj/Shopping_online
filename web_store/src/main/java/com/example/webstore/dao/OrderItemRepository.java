package com.example.webstore.dao;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webstore.dao.mapper.OrderItemMapper;
import com.example.webstore.dao.model.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author William Lee
 */
@Repository
public class OrderItemRepository extends ServiceImpl<OrderItemMapper, OrderItem> {
    public void createOrderItem(OrderItem orderItem) {
        baseMapper.insertItem(orderItem);
    }
    public OrderItem getOrderItemById(Long itemId) {
        return baseMapper.getAItemByItemId(itemId);
    }

    public void deleteOrderItemById(Long itemId) {
        baseMapper.deleteById(itemId);
    }

    public OrderItem[] getItemsByOrderId(Long order_id) {
        return baseMapper.getItemsByOrderId(order_id);
    }

    public int deliverGoods(Long order_id, Long goods_id){
        return baseMapper.deliverGoods(order_id,goods_id);
    }
    public int confirmGoods(Long order_id,Long goods_id,String finishtime){
        return baseMapper.confirmGoods(order_id,goods_id,finishtime);
    }
    public OrderItem getItemsByOrderIdGoods_id(Long order_id,Long goods_id){
        return baseMapper.getItemsByOrderIdGoods_id(order_id,goods_id);
    }
    public int refundGoods(Long order_id,Long goods_id){
        return baseMapper.refundGoods(order_id,goods_id);
    }
    public int undoGoods(Long order_id,Long goods_id){ return baseMapper.undoGoods(order_id,goods_id ); };
    public void updateitemPrice(Long itemId, BigDecimal itemTotalPrice){
        baseMapper.updateItemPrice( itemId, itemTotalPrice);
    }
    public BigDecimal getItemTotalPrice( Long order_id, Long goods_id){ return baseMapper.getItemTotalPrice(order_id,goods_id);}
    public int refuseRefund(Long order_id,Long goods_id,String rejectReason){ return baseMapper.refuseRefund(order_id,goods_id,rejectReason);}
    public int acceptRefund(Long order_id,Long goods_id){return baseMapper.acceptRefund(order_id,goods_id);}
    public String getRefuseReason(Long order_id,Long goods_id){ return baseMapper.getRefuseReason(order_id,goods_id);}
}
