package com.example.webstore.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.webstore.dao.model.OrderItem;
import com.example.webstore.dao.model.ShopOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author William Lee
 */
public interface ShopOrderMapper extends BaseMapper<ShopOrder> {
    @Select("SELECT * FROM shop_order WHERE user_id = #{user_id}")
    List<ShopOrder> getOrdersByUserId(@Param("user_id") Long userId);


    @Select("SELECT * FROM shop_order WHERE id = #{orderId}")
    ShopOrder getOrdersByOrderId(Long orderId);

    @Update("UPDATE shop_order SET pay_status = #{payStatus} WHERE id = #{orderId}")
    void updatePayStatus(@Param("orderId") Long orderId, @Param("payStatus") int payStatus);


    @Select("SELECT u.name\n" +
            "FROM shop_order so\n" +
            "JOIN userslist u ON u.id = so.user_id\n" +
            "WHERE so.id = #{order_id};\n")
    String getUsername(Long order_id);
}
