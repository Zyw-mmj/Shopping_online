package com.example.webstore.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webstore.dao.mapper.PaymentRecordMapper;
import com.example.webstore.dao.model.PaymentRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author William Lee
 */
@Repository
public class PaymentRecordRepository  extends ServiceImpl<PaymentRecordMapper, PaymentRecord> {

    public void insert(PaymentRecord paymentRecord) {
        PaymentRecordMapper mapper = getBaseMapper();
        mapper.insert(paymentRecord);
    }

    public Optional<PaymentRecord> findById(Long id) {
        PaymentRecordMapper mapper = getBaseMapper();
        PaymentRecord paymentRecord = mapper.selectById(id);
        return Optional.ofNullable(paymentRecord);
    }

    public PaymentRecord[] findByUserId(Long userId) {
        List<PaymentRecord> records = baseMapper.selectList(new QueryWrapper<PaymentRecord>().eq("user_id", userId));
        return records.toArray(new PaymentRecord[0]);
    }
}
