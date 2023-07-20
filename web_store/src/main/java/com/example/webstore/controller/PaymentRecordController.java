package com.example.webstore.controller;

import com.example.webstore.dao.model.PaymentRecord;
import com.example.webstore.service.IPaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paymentRecord")
public class PaymentRecordController {
    private final IPaymentRecordService paymentRecordService;

    @Autowired
    public PaymentRecordController(IPaymentRecordService paymentRecordService) {
        this.paymentRecordService = paymentRecordService;
    }

    @PostMapping
    public void createPaymentRecord(@RequestBody PaymentRecord paymentRecord) {
        paymentRecordService.createPaymentRecord(paymentRecord);
    }

    @GetMapping("/{id}")
    public PaymentRecord getPaymentRecord(@PathVariable Long id) {
        return paymentRecordService.getPaymentRecord(id);
    }

    @GetMapping("/user/{userId}")
    public PaymentRecord[] getPaymentRecordByuser(@PathVariable Long userId) {
        return paymentRecordService.getPaymentRecordByUser(userId);
    }
}

