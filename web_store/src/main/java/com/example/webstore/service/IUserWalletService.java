package com.example.webstore.service;

import com.example.webstore.dao.model.UserWallet;

import java.math.BigDecimal;

/**
 * @author William Lee
 */
public interface IUserWalletService {
    UserWallet getUserWallet(Long userId);

    void rechargeUserWallet(Long userId, BigDecimal amount);

    void makePayment(Long userId, Long orderId, BigDecimal amount,int point);

    void getRefundInWallet(Long userId, BigDecimal amount);
}
