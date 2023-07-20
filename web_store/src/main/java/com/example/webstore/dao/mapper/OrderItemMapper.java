package com.example.webstore.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.webstore.dao.model.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author William Lee
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    @Select("SELECT * FROM order_item WHERE order_item_id = #{order_item_id}")
    OrderItem getAItemByItemId(@Param("order_item_id") Long itemId);

    @Insert("INSERT INTO order_item (order_id, goods_id, goods_name, selling_price, goods_count) " +
            "VALUES(#{order_id}, #{goods_id}, #{goods_name}, #{selling_price}, #{goods_count})")
    void insertItem(OrderItem orderItem);

    @Update("UPDATE order_item" +
            "   SET goods_status = 3" +
            "   WHERE order_id = #{order_id} AND goods_id = #{goods_id} AND goods_status = 1")
    int deliverGoods(@Param("order_id") Long order_id,@Param("goods_id") Long goods_id);

    @Select("SELECT * FROM order_item WHERE order_id = #{order_id}")
    OrderItem[] getItemsByOrderId(@Param("order_id") Long order_id);

    @Update("UPDATE order_item" +
            "   SET goods_status = 4," +
            "   finishtime =  #{finishtime}" +
            "   WHERE order_id = #{order_id} AND goods_id = #{goods_id} AND goods_status = 3")
    int confirmGoods(@Param("order_id") Long order_id,@Param("goods_id") Long goods_id,@Param("finishtime") String finishtime);

    @Select("SELECT * FROM order_item WHERE order_id = #{order_id} AND goods_id = #{goods_id} AND goods_status = 4")
    OrderItem getItemsByOrderIdGoods_id(@Param("order_id") Long order_id,@Param("goods_id") Long goods_id);

    @Update("UPDATE order_item" +
            "   SET goods_status = 5" +
            "   WHERE order_id = #{order_id} AND goods_id = #{goods_id} AND goods_status = 4")
    int refundGoods(@Param("order_id") Long order_id,@Param("goods_id") Long goods_id);

    @Update("UPDATE order_item" +
            "   SET goods_status = 4" +
            "   WHERE order_id = #{order_id} AND goods_id = #{goods_id} AND goods_status = 5")
    int undoGoods(@Param("order_id") Long order_id,@Param("goods_id") Long goods_id);

    @Update("UPDATE order_item SET itemtotalprice = #{itemtotalprice} WHERE order_item_id = #{order_item_id}")
    void updateItemPrice(@Param("order_item_id") Long itemId, @Param("itemtotalprice") BigDecimal itemtotalprice);

    @Select("SELECT itemtotalprice FROM order_item WHERE order_id = #{order_id} AND goods_id = #{goods_id} ")
    BigDecimal getItemTotalPrice(@Param("order_id") Long order_id, @Param("goods_id") Long goods_id);

    @Update("UPDATE order_item" +
            "   SET goods_status = 7," +
            "       rejectreason =#{rejectReason}" +
            "   WHERE order_id = #{order_id} AND goods_id = #{goods_id} AND goods_status = 5")
    int refuseRefund(@Param("order_id") Long order_id,@Param("goods_id") Long goods_id,@Param("rejectReason") String rejectReason);

    @Update("UPDATE order_item" +
            "   SET goods_status = 6" +
            "   WHERE order_id = #{order_id} AND goods_id = #{goods_id} AND goods_status = 5")
    int acceptRefund(@Param("order_id") Long order_id,@Param("goods_id") Long goods_id);

    @Select("SELECT rejectreason FROM order_item WHERE order_id = #{order_id} AND goods_id = #{goods_id} ")
    String getRefuseReason(@Param("order_id") Long order_id,@Param("goods_id") Long goods_id);
}
