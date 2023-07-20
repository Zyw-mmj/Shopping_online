package com.example.webstore.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author William Lee
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoporderBo {

    private Long id;

    private String orderNo;

    private Long userId;

    private double totalPrice;

    private int payStatus;

    private int payType;

    private LocalDateTime payTime;

    private int orderStatus;

    private String extraInfo;

    private String userName;

    private String userPhone;

    private String userAddress;

    private int isDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    public ShoporderBo(Long userId, double totalPrice, int payStatus, int payType, int orderStatus, String userName, String userPhone, String userAddress) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.payStatus = payStatus;
        this.payType = payType;
        this.orderStatus = orderStatus;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
    }
}
