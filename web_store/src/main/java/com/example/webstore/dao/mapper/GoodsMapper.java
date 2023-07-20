package com.example.webstore.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.webstore.dao.model.BusinessOrder;
import com.example.webstore.dao.model.Goods;
import com.example.webstore.dao.model.User;
import com.example.webstore.dao.model.UserOrder;
import com.example.webstore.domain.CarouselBo;
import com.example.webstore.domain.GoodsBo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsMapper extends BaseMapper<Goods> {
    //模糊搜索
    @Select("SELECT * FROM shoplist WHERE goodsname LIKE CONCAT('%', #{keyword}, '%') AND status != 'Rejected' AND status != 'Off'")
    List<GoodsBo> findByTitleContaining(@Param("keyword") String keyword);

    //模糊搜索
    @Select("SELECT * FROM shoplist WHERE goodsname LIKE CONCAT('%', #{keyword}, '%')")
    List<GoodsBo> AdminfindByTitleContaining(@Param("keyword") String keyword);
    //商品类型返回
    @Select("SELECT DISTINCT categorie FROM shoplist;")
    List<String> getGoodsType();

    @Update("UPDATE shoplist SET status = 'Approved' WHERE id = #{id};")
    int updateProductStatus(@Param("id")Long id);

    @Update("UPDATE shoplist SET status = 'Rejected' WHERE id = #{id};")
    int updateProductStatusUnpass(@Param("id")Long id);

    @Select("SELECT sl.*,oi.goods_count,oi.goods_status,oi.order_id" +
            "   FROM shop_order so" +
            "   JOIN order_item oi ON so.id = oi.order_id" +
            "   JOIN shoplist sl ON oi.goods_id = sl.id" +
            "   WHERE so.pay_status = 1" +
            "   AND oi.goods_status IN (1)"+
            "   AND sl.businessAccount = #{account};")
    List<BusinessOrder> findOrderGoods (@Param("account") String account);

    @Update("UPDATE shoplist" +
            "   SET sales = sales + #{quantity}, stock = stock - #{quantity}" +
            "   WHERE id = #{id};")
    int deliverGoods(@Param("id")Long id,@Param("quantity") int quantity);

    @Update("UPDATE shoplist SET status = 'Off' WHERE id = #{id} AND status!='Rejected' AND status!='Pending';")
    int updateProductStatusOff(@Param("id")Long id);

    @Update("UPDATE shoplist SET status = 'Approved' WHERE id = #{id} AND status!='Rejected' AND  status!='Pending';")
    int updateProductStatusOn(@Param("id")Long id);

    @Select("SELECT * FROM shoplist WHERE businessAccount = #{account};")
    List<Goods> getByAccount(@Param("businessAccount")String account);

    @Select("SELECT sl.*,oi.goods_count,oi.goods_status,oi.order_id,oi.create_time,oi.itemtotalprice" +
            "   FROM shop_order so" +
            "   JOIN order_item oi ON so.id = oi.order_id" +
            "   JOIN shoplist sl ON oi.goods_id = sl.id" +
            "   WHERE so.pay_status = 1" +
            "   AND oi.goods_status IN (3)"+
            "   AND sl.businessAccount = #{account};")
    List<BusinessOrder> findShippedOrder (@Param("account") String account);

    @Select("SELECT sl.*,oi.goods_count,oi.goods_status,oi.order_id" +
            "   FROM shop_order so" +
            "   JOIN order_item oi ON so.id = oi.order_id" +
            "   JOIN shoplist sl ON oi.goods_id = sl.id" +
            "   WHERE so.pay_status = 1" +
            "   AND oi.goods_status IN (5)"+
            "   AND sl.businessAccount = #{account};")
    List<BusinessOrder> findRefundOrder (@Param("account") String account);

    @Select("SELECT sl.*,oi.goods_count,oi.goods_status,oi.order_id,oi.create_time,oi.itemtotalprice" +
            "   FROM shop_order so" +
            "   JOIN order_item oi ON so.id = oi.order_id" +
            "   JOIN shoplist sl ON oi.goods_id = sl.id" +
            "   WHERE so.pay_status = 1" +
            "   AND oi.goods_status IN (4,6,7)"+
            "   AND sl.businessAccount = #{account};")
    List<BusinessOrder> findFinishedOrder (@Param("account") String account);

    @Update("UPDATE shoplist SET status = 'Pending' WHERE id = #{id};")
    int updateProductStatusPending(@Param("id")Long id);

    @Select("SELECT sl.*,oi.goods_count,oi.goods_status,oi.order_id,oi.create_time,oi.itemtotalprice" +
            "   FROM shop_order so" +
            "   JOIN order_item oi ON so.id = oi.order_id" +
            "   JOIN shoplist sl ON oi.goods_id = sl.id" +
            "   WHERE so.pay_status = 1" +
            "   AND oi.goods_status IN (1)"+
            "   AND so.user_id = #{userId};")
    List<UserOrder> userFindGoing (@Param("userId") Long userId);

    @Select("SELECT sl.*,oi.goods_count,oi.goods_status,oi.order_id" +
            "   FROM shop_order so" +
            "   JOIN order_item oi ON so.id = oi.order_id" +
            "   JOIN shoplist sl ON oi.goods_id = sl.id" +
            "   WHERE so.pay_status = 1" +
            "   AND oi.goods_status IN (3)"+
            "   AND so.user_id = #{userId};")
    List<UserOrder> userFindPending(@Param("userId") Long userId);

    @Select("SELECT sl.*,oi.goods_count,oi.goods_status,oi.order_id" +
            "   FROM shop_order so" +
            "   JOIN order_item oi ON so.id = oi.order_id" +
            "   JOIN shoplist sl ON oi.goods_id = sl.id" +
            "   WHERE so.pay_status = 1" +
            "   AND oi.goods_status IN (4,6,7)"+
            "   AND so.user_id = #{userId};")
    List<UserOrder> userFindFinished (@Param("userId") Long userId);

    @Select("SELECT sl.*,oi.goods_count,oi.goods_status,oi.order_id" +
            "   FROM shop_order so" +
            "   JOIN order_item oi ON so.id = oi.order_id" +
            "   JOIN shoplist sl ON oi.goods_id = sl.id" +
            "   WHERE so.pay_status = 1" +
            "   AND oi.goods_status IN (5)"+
            "   AND so.user_id = #{userId};")
    List<UserOrder> userFindRefund (@Param("userId") Long userId);

    @Select("SELECT b.account, b.name, s.id, s.goodsname, s.describes, s.price, s.picture " +
            "FROM business b " +
            "JOIN shoplist s ON b.account = s.businessAccount limit 5")
    List<CarouselBo> getGoodshaveCarousel();

    @Update("UPDATE shoplist SET rating = #{rating} WHERE id = #{id};")
    boolean updategoodsRating(@Param("id")Long id,@Param("rating")int rating);

    @Update("UPDATE shoplist" +
            "   SET sales = sales - #{quantity}, stock = stock + #{quantity}" +
            "   WHERE id = #{id};")
    int refundGoods(@Param("id")Long id,@Param("quantity") int quantity);
}
