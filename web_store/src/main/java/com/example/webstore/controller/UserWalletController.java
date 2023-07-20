package com.example.webstore.controller;

import com.example.webstore.dao.model.UserWallet;
import com.example.webstore.service.IUserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author William Lee
 */
@RestController
@RequestMapping("/userWallet")
public class UserWalletController {
    @Autowired
    private  final IUserWalletService userWalletService;

    public UserWalletController(IUserWalletService userWalletService) {
        this.userWalletService = userWalletService;
    }

    @GetMapping("/{userId}")
    public UserWallet getUserWallet(@PathVariable Long userId) {
        return userWalletService.getUserWallet(userId);
    }

    @PostMapping("/{userId}/recharge")
    public void rechargeUserWallet(@PathVariable Long userId, @RequestBody Map map) {
        BigDecimal amount= BigDecimal.valueOf(Double.parseDouble((String) map.get("amount")));
        userWalletService.rechargeUserWallet(userId, amount);
    }


    @PostMapping("/{userId}/payment")
    public void makePayment(@PathVariable Long userId, @RequestParam Long orderId, @RequestParam String amount, @RequestParam String point) {
        // Call the service to make a payment from the user's wallet
        BigDecimal amountFix=BigDecimal.valueOf(Double.valueOf(amount));
        Integer pointFix = Integer.valueOf(point);
        userWalletService.makePayment(userId, orderId, amountFix,pointFix);
    }
}
