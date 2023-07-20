package com.example.webstore.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.webstore.dao.model.CouponRecord;
import com.example.webstore.domain.CouponRecordBo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author William Lee
 */
@Mapper
public interface CouponRecordMapper extends BaseMapper<CouponRecord> {

}
