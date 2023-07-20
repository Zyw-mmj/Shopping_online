package com.example.webstore.service.impl;

import com.example.webstore.dao.OrderItemRepository;
import com.example.webstore.dao.ShopOrderRepository;
import com.example.webstore.dao.model.OrderItem;
import com.example.webstore.dao.model.ShopOrder;
import com.example.webstore.domain.OrderItemBo;
import com.example.webstore.domain.ShoporderBo;
import com.example.webstore.service.IShopOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author William Lee
 */
@Service
@Slf4j
public class ShopOrderServiceImpl implements IShopOrderService {
    @Autowired
    private final ShopOrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public ShopOrderServiceImpl(ShopOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public ShopOrder getOrderById(Long id) {
        return orderRepository.getById(id);
    }

    @Override
    public List<ShopOrder> getAllOrders() {
        return orderRepository.getAll();
    }

    @Override
    public ShopOrder createOrder(ShoporderBo shoporderBo) {
        ShopOrder order = new ShopOrder();
        order.setUser_id(shoporderBo.getUserId());
        order.setTotal_price(shoporderBo.getTotalPrice());
        order.setPay_status(shoporderBo.getPayStatus());
        order.setPay_type(shoporderBo.getPayType());
        order.setOrder_status(shoporderBo.getOrderStatus());
        order.setUser_name(shoporderBo.getUserName());
        order.setUser_phone(shoporderBo.getUserPhone());
        order.setUser_address(shoporderBo.getUserAddress());
        orderRepository.create(order);
        return order;
    }

    @Override
    public ShopOrder updateOrder(Long id, ShopOrder order) {
        ShopOrder existingOrder = orderRepository.getById(id);
        if (existingOrder != null) {
            order.setId(id);
            orderRepository.update(order);
            return order;
        }
        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.delete(id);
    }

    @Override
    public List<ShopOrder> getOrdersByUserId(Long userId) {
        return orderRepository.getOrdersByUserId(userId);
    }

    @Override
    public OrderItem createOrderItem(OrderItemBo orderItemBo) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder_id(orderItemBo.getOrderId());
        orderItem.setGoods_id(orderItemBo.getGoodsId());
        orderItem.setGoods_name(orderItemBo.getGoodsName());
        orderItem.setSelling_price(orderItemBo.getSellingPrice());
        orderItem.setGoods_count(orderItemBo.getGoodsCount());
        orderItemRepository.createOrderItem(orderItem);
        return orderItem;
    }

    @Override
    public OrderItem[] getOrderItemsByOrderId(Long order_id) {
        return orderItemRepository.getItemsByOrderId(order_id);
    }

    @Override
    public void deleteOrderItem(Long orderId, Long itemId) {
        // 先查出Order对象
        ShopOrder order = orderRepository.getOrdersByOrderId(orderId);
        if (order == null) {
            log.error("Order not found");
        }
        // 查出OrderItem对象
        OrderItem orderItem = orderItemRepository.getOrderItemById(itemId);
        if (orderItem == null) {
            log.error("OrderItem not found");
        }
        // 如果OrderItem不属于Order，则不能删除
        if (!order.getId().equals(orderItem.getOrder_id())) {
            log.error("OrderItem does not belong to the Order");
        }
        // 删除OrderItem
        orderItemRepository.deleteOrderItemById(itemId);
        log.info("DeleteSuccess");
    }

    @Override
    public boolean deliverGoods(Long order_id, Long goods_id) {
        int a=orderItemRepository.deliverGoods(order_id,goods_id);
        if (a>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void updateOrderStatus(Long orderId, int status) {
        orderRepository.updatePayStatus(orderId,status);
    }

    @Override
    public boolean confirmGoods(Long order_id, Long goods_id, String finishtime) {
        int a=orderItemRepository.confirmGoods(order_id,goods_id,finishtime);
        if (a>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public OrderItemBo getItemsByOrderIdGoods_id(Long order_id, Long goods_id) {
        OrderItem order = orderItemRepository.getItemsByOrderIdGoods_id(order_id,goods_id);
        OrderItemBo orderItemBo=new OrderItemBo();
        orderItemBo.setOrderItemId(order.getOrder_item_id());
        orderItemBo.setOrderId(order.getOrder_id());
        orderItemBo.setGoodsId(order.getGoods_id());
        orderItemBo.setGoodsName(order.getGoods_name());
        orderItemBo.setSellingPrice(order.getSelling_price());
        orderItemBo.setGoodsCount(order.getGoods_count());
        orderItemBo.setGoodsStatus(order.getGoods_status());
        orderItemBo.setFinishtime(order.getFinishtime());
        return orderItemBo;
    }

    @Override
    public boolean refundGoods(Long order_id, Long goods_id) {
        int a=orderItemRepository.refundGoods(order_id,goods_id);
        if (a>0){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public boolean undoGoods(Long order_id, Long goods_id) {
        int a=orderItemRepository.undoGoods(order_id,goods_id);
        if (a>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void updateItemPrice(Long itemId, BigDecimal itemPrice){
        orderItemRepository.updateitemPrice(itemId,itemPrice);
    }

    @Override
    public BigDecimal getItemTotalPrice(Long order_id, Long goods_id) {
        BigDecimal result=this.orderItemRepository.getItemTotalPrice(order_id,goods_id);
        return result;
    }


    @Override
    public boolean refuseRefund(Long order_id,Long goods_id,String rejectReason) {
        int a=orderItemRepository.refuseRefund(order_id,goods_id,rejectReason);
        if (a>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean acceptRefund(Long order_id, Long goods_id) {
        int a=orderItemRepository.acceptRefund(order_id,goods_id);
        if (a>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Long getUserIdByOrderId(Long order_id) {
        ShopOrder shopOrder= this.orderRepository.getOrdersByOrderId(order_id);
        Long user_id=shopOrder.getUser_id();
        return user_id;
    }

    @Override
    public String getRefuseReason(Long order_id, Long goods_id) {
        return this.orderItemRepository.getRefuseReason(order_id,goods_id);
    }
}
