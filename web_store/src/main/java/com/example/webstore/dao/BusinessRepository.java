package com.example.webstore.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webstore.dao.mapper.BusinessMapper;
import com.example.webstore.dao.model.Business;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessRepository extends ServiceImpl<BusinessMapper,Business> {
    public int finishOrder(double amount, double wallet, double fee, String account){
        return baseMapper.finishOrder(amount,wallet,fee,account);
    }
    public int refundMoney(double amount, double wallet, double fee, String account){
        return baseMapper.refundMoney(amount,wallet,fee,account);
    }
}
