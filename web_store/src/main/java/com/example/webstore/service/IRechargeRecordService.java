package com.example.webstore.service;

import com.example.webstore.dao.model.RechargeRecord;

/**
 * @author William Lee
 */
public interface IRechargeRecordService {
    void saveRechargeRecord(RechargeRecord rechargeRecord);

    void createRechargeRecord(RechargeRecord rechargeRecord);

    RechargeRecord getRechargeRecord(Long id);

    public RechargeRecord[] getRechargeRecordByUser(Long userId);
}
