package com.example.webstore.controller;


import com.example.webstore.dao.model.RechargeRecord;
import com.example.webstore.service.IRechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rechargeRecord")
public class RechargeRecordController {
    private final IRechargeRecordService rechargeRecordService;

    @Autowired
    public RechargeRecordController(IRechargeRecordService rechargeRecordService) {
        this.rechargeRecordService = rechargeRecordService;
    }

    @PostMapping
    public void createRechargeRecord(@RequestBody RechargeRecord rechargeRecord) {
        rechargeRecordService.createRechargeRecord(rechargeRecord);
    }

    @GetMapping("/{id}")
    public RechargeRecord getRechargeRecord(@PathVariable Long id) {
        return rechargeRecordService.getRechargeRecord(id);
    }
    @GetMapping("/user/{userId}")
    public RechargeRecord[] getRechargeRecordByuser(@PathVariable Long userId) {
        return rechargeRecordService.getRechargeRecordByUser(userId);
    }
}

