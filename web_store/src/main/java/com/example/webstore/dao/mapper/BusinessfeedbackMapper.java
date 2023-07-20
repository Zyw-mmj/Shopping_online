package com.example.webstore.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.webstore.dao.model.Businessfeedback;
import com.example.webstore.domain.BusinessfeedbackBo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessfeedbackMapper extends BaseMapper<Businessfeedback> {
    @Select("SELECT bf.*, sl.goodsname, b.name\n" +
            "FROM shop_order so\n" +
            "JOIN businessfeedback bf ON bf.order_id = so.id\n" +
            "JOIN order_item oi ON oi.order_id = so.id\n" +
            "JOIN shoplist sl ON oi.goods_id = sl.id\n" +
            "JOIN business b ON sl.businessAccount = b.account\n" +
            "WHERE so.user_id = #{user_id}\n" +
            "AND bf.order_id = #{order_id}\n " +
            "AND sl.id = #{good_id}\n " +
            "AND bf.account = #{business_account};")
    List<BusinessfeedbackBo> getBusinessFeedback(Long user_id , Long order_id,Long good_id,String business_account);

    @Select("SELECT rating FROM businessfeedback WHERE order_id= #{order_id}")
    List<Integer> getTotalRating(Long order_id);


    @Update("UPDATE userslist u\n" +
            "JOIN shop_order so ON u.id = so.user_id\n" +
            "SET u.rating = #{rating}\n" +
            "WHERE so.id = #{order_id};\n")
    Boolean updateUserRating(Long order_id, int rating);
}
