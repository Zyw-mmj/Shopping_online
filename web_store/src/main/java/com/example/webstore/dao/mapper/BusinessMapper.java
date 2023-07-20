package com.example.webstore.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.webstore.dao.model.Business;
import com.example.webstore.dao.model.Cart;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BusinessMapper extends BaseMapper<Business> {
    @Select("   SELECT * FROM business WHERE account = #{account}")
    Business selectByAccount(@Param("account") String account);

    @Update("UPDATE business SET starttime = #{starttime},endtime = #{endtime}")
    void updateStartAndEndToAll(@Param("starttime")String starttime,@Param("endtime")String endtime);

    @Update("UPDATE business" +
            "   SET amount = amount + #{amount}," +
            "   quantity = quantity + 1," +
            "   wallet = wallet + #{wallet}," +
            "   fee = fee + #{fee}" +
            "   WHERE account = #{account} ")
    int finishOrder(@Param("amount")double amount,@Param("wallet")double wallet,@Param("fee")double fee,@Param("account") String account);

    @Update("UPDATE business" +
            "   SET amount = amount - #{amount}," +
            "   quantity = quantity - 1," +
            "   wallet = wallet - #{wallet}," +
            "   fee = fee - #{fee}" +
            "   WHERE account = #{account} ")
    int refundMoney(@Param("amount")double amount,@Param("wallet")double wallet,@Param("fee")double fee,@Param("account") String account);
}
