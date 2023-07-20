package com.example.webstore.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.webstore.dao.model.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CouponMapper extends BaseMapper<Coupon> {

}
