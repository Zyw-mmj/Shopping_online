package com.example.webstore.service.impl;

import com.example.webstore.dao.RechargeRecordRepository;
import com.example.webstore.dao.model.RechargeRecord;
import com.example.webstore.service.IRechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RechargeRecordServiceImpl implements IRechargeRecordService {
    private final RechargeRecordRepository rechargeRecordRepository;

    @Autowired
    public RechargeRecordServiceImpl(RechargeRecordRepository rechargeRecordRepository) {
        this.rechargeRecordRepository = rechargeRecordRepository;
    }
    @Override
    public void saveRechargeRecord(RechargeRecord rechargeRecord) {
        rechargeRecordRepository.insert(rechargeRecord);
    }
    @Override
    public void createRechargeRecord(RechargeRecord rechargeRecord) {
        if (rechargeRecord.getCreateTime()==null){
            rechargeRecord.setCreateTime(LocalDateTime.now());
        }
        rechargeRecord.setUpdateTime(LocalDateTime.now());
        rechargeRecordRepository.save(rechargeRecord);
    }

    @Override
    public RechargeRecord getRechargeRecord(Long id) {
        return rechargeRecordRepository.findById(id).orElse(null);
    }

    @Override
    public RechargeRecord[] getRechargeRecordByUser(Long userId) {
        return rechargeRecordRepository.findByUserId(userId);
    }
}

