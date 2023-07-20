package com.example.webstore.service;

import com.example.webstore.dao.model.OrderItem;
import com.example.webstore.dao.model.ShopOrder;
import com.example.webstore.domain.OrderItemBo;
import com.example.webstore.domain.ShoporderBo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface IShopOrderService {
    ShopOrder getOrderById(Long id);

    List<ShopOrder> getAllOrders();

    ShopOrder createOrder(ShoporderBo shoporderBo);

    ShopOrder updateOrder(Long id, ShopOrder order);

    void deleteOrder(Long id);

    List<ShopOrder> getOrdersByUserId(Long userId);

    OrderItem createOrderItem(OrderItemBo orderItemBo);

    OrderItem[] getOrderItemsByOrderId(Long orderId);

    void deleteOrderItem(Long orderId, Long itemId);

    boolean deliverGoods(Long order_id,Long goods_id);

    void updateOrderStatus(Long orderId, int status);

    boolean confirmGoods(Long order_id,Long goods_id,String finishtime);

    OrderItemBo getItemsByOrderIdGoods_id(Long order_id,Long goods_id);

    boolean refundGoods(Long order_id,Long goods_id);

    boolean undoGoods(Long order_id,Long goods_id);
    void updateItemPrice(Long itemId, BigDecimal itemPrice);
    BigDecimal getItemTotalPrice(Long order_id, Long goods_id);
    boolean refuseRefund(Long order_id,Long goods_id,String rejectReason);
    boolean acceptRefund(Long order_id,Long goods_id);
    Long getUserIdByOrderId(Long order_id);
    String getRefuseReason(Long order_id,Long goods_id);
}
