package com.example.webstore.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webstore.dao.mapper.CouponRecordMapper;
import com.example.webstore.dao.model.CouponRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author William Lee
 */
@Repository
public class CouponRecordRepository extends ServiceImpl<CouponRecordMapper, CouponRecord> {


    public List<CouponRecord> selectList(QueryWrapper<CouponRecord> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    public boolean delete(QueryWrapper<CouponRecord> deleteWrapper) {
        return baseMapper.delete(deleteWrapper) > 0;
    }

    public int insert(CouponRecord couponRecord) {
        return baseMapper.insert(couponRecord);
    }
}

