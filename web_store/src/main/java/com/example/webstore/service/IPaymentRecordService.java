package com.example.webstore.service;

import com.example.webstore.dao.model.PaymentRecord;
import com.example.webstore.domain.PaymentRecordBo;

/**
 * @author William Lee
 */
public interface IPaymentRecordService {
    void createPaymentRecord(PaymentRecord paymentRecord);

    void savePaymentRecord(PaymentRecord paymentRecord);

    PaymentRecord getPaymentRecord(Long id);

    PaymentRecord[] getPaymentRecordByUser(Long userId);

    void createRefundRecord(PaymentRecordBo paymentRecordBo);
}
