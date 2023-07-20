package com.example.webstore.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webstore.dao.mapper.RechargeRecordMapper;
import com.example.webstore.dao.model.RechargeRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author William Lee
 */
@Repository
public class RechargeRecordRepository extends ServiceImpl<RechargeRecordMapper,RechargeRecord> {
    public Optional<RechargeRecord> findById(Long id) {
        RechargeRecordMapper mapper = getBaseMapper();
        RechargeRecord rechargeRecord = mapper.selectById(id);
        return Optional.ofNullable(rechargeRecord);
    }

    public void insert(RechargeRecord rechargeRecord) {
        RechargeRecordMapper mapper = getBaseMapper();
        mapper.insert(rechargeRecord);
    }

    public RechargeRecord[] findByUserId(Long userId) {
        List<RechargeRecord> records = baseMapper.selectList(new QueryWrapper<RechargeRecord>().eq("user_id", userId));
        return records.toArray(new RechargeRecord[0]);
    }
}