package com.example.webstore.service.impl;

import com.example.webstore.dao.PaymentRecordRepository;
import com.example.webstore.dao.model.PaymentRecord;
import com.example.webstore.domain.PaymentRecordBo;
import com.example.webstore.service.IPaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentRecordServiceImpl implements IPaymentRecordService {
    private final PaymentRecordRepository paymentRecordRepository;

    @Autowired
    public PaymentRecordServiceImpl(PaymentRecordRepository paymentRecordRepository) {
        this.paymentRecordRepository = paymentRecordRepository;
    }

    @Override
    public void createPaymentRecord(PaymentRecord paymentRecord) {
        if (paymentRecord.getCreateTime() == null) {
            paymentRecord.setCreateTime(LocalDateTime.now());
        }
        paymentRecord.setUpdateTime(LocalDateTime.now());
        paymentRecordRepository.save(paymentRecord);
    }
    @Override
    public void savePaymentRecord(PaymentRecord paymentRecord) {
        paymentRecordRepository.insert(paymentRecord);
    }
    @Override
    public PaymentRecord getPaymentRecord(Long id) {
        return paymentRecordRepository.findById(id).orElse(null);
    }

    @Override
    public PaymentRecord[] getPaymentRecordByUser(Long userId) {
        return paymentRecordRepository.findByUserId(userId);
    }

    @Override
    public void createRefundRecord(PaymentRecordBo paymentRecordBo) {
        PaymentRecord paymentRecord=new PaymentRecord();
        paymentRecord.setOrderId(paymentRecordBo.getOrderId());
        paymentRecord.setUserId(paymentRecordBo.getUserId());
        paymentRecord.setAmount(paymentRecordBo.getAmount());
        paymentRecord.setStatus(paymentRecordBo.getStatus());
        paymentRecordRepository.save(paymentRecord);
    }
}

