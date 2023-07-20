package com.example.webstore.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.webstore.dao.model.Business;
import com.example.webstore.dao.model.Feedback;
import com.example.webstore.domain.FeedbackBo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackMapper extends BaseMapper<Feedback> {
    @Select("  SELECT productRating FROM feedback WHERE productId = #{productId}")
    List<Integer> getTotalRating(@Param("productId") Long productId);

    @Select("SELECT f.*, oi.goods_name, b.name\n" +
            "FROM feedback f\n" +
            "JOIN order_item oi ON f.orderId = oi.order_id AND f.productId = oi.goods_id\n" +
            "JOIN shop_order so ON f.orderId = so.id\n" +
            "JOIN shoplist sl ON oi.goods_id = sl.id\n" +
            "JOIN business b ON sl.businessAccount = b.account\n" +
            "WHERE so.user_id = #{user_id}\n")
    List<FeedbackBo> UsergetFeedback(@Param("user_id") Long user_id);
}
