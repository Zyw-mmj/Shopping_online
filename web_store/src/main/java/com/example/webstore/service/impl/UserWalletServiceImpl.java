package com.example.webstore.service.impl;


import com.example.webstore.dao.UserWalletRepository;
import com.example.webstore.dao.model.UserWallet;
import com.example.webstore.service.IUserWalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author William Lee
 */
@Service
@Slf4j
public class UserWalletServiceImpl implements IUserWalletService {
    @Autowired
    private final UserWalletRepository userWalletRepo;

    public UserWalletServiceImpl(UserWalletRepository userWalletRepo) {
        this.userWalletRepo = userWalletRepo;
    }

    @Override
    public UserWallet getUserWallet(Long userId) {
        try {
            return userWalletRepo.findByUserId(userId).orElse(null);
        } catch (Exception e) {
            log.error("Error occurred while retrieving user wallet for userId: {}", userId, e);
            // Handle the exception or return an appropriate response
            return null;
        }
    }
    @Override
    public void rechargeUserWallet(Long userId, BigDecimal amount) {
        UserWallet userWallet = userWalletRepo.findByUserId(userId).orElse(null);
        if (userWallet != null) {
            BigDecimal currentBalance = userWallet.getBalance();
            BigDecimal newBalance = currentBalance.add(amount);
            userWallet.setBalance(newBalance);
            userWalletRepo.updateById(userWallet);
        }
    }

    @Override
    public void makePayment(Long userId, Long orderId, BigDecimal amount,int point) {
        UserWallet userWallet = userWalletRepo.findByUserId(userId).orElse(null);
        if (userWallet != null) {
            BigDecimal currentBalance = userWallet.getBalance();
            BigDecimal newBalance = currentBalance.subtract(amount);
            userWallet.setBalance(newBalance);
            userWallet.setPoints(userWallet.getPoints()+amount.intValue()-point);
            userWalletRepo.updateById(userWallet);
        }
    }

    @Override
    public void getRefundInWallet(Long userId, BigDecimal amount) {
        UserWallet userWallet = userWalletRepo.findByUserId(userId).orElse(null);
        if (userWallet != null) {
            BigDecimal currentBalance = userWallet.getBalance();
            BigDecimal newBalance = currentBalance.add(amount);
            userWallet.setBalance(newBalance);
            log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            log.info(newBalance.toString());
            userWalletRepo.updateById(userWallet);
        }
    }

}
