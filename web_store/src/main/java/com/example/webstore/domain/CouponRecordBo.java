package com.example.webstore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author William Lee
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponRecordBo {

    private Long couponUserId;

    private Long userId;

    private Long couponId;

    private Integer useStatus;

    private Integer usedTime;

    private Integer orderId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private int isDeleted;
}
