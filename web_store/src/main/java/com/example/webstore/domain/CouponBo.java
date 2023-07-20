package com.example.webstore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponBo {
    private Long couponId;

    private Long couponUserId;

    private String couponName;

    private String couponDesc;

    private Integer couponTotal;

    private boolean saleOut;

    private boolean useStatus;

    private Integer discount;

    private Integer min;

    private Byte couponLimit;

    private Byte couponType;

    private Byte status;

    private Byte goodsType;

    private String goodsValue;

    private String code;

    private LocalDateTime couponStartTime;

    private LocalDateTime couponEndTime;

    private boolean hasReceived;
    @Override
    public String toString() {
        return "CouponBO{" +
                "couponId=" + couponId +
                ", couponUserId=" + couponUserId +
                ", couponName='" + couponName + '\'' +
                ", couponDesc='" + couponDesc + '\'' +
                ", couponTotal=" + couponTotal +
                ", saleOut=" + saleOut +
                ", useStatus=" + useStatus +
                ", discount=" + discount +
                ", min=" + min +
                ", couponLimit=" + couponLimit +
                ", couponType=" + couponType +
                ", status=" + status +
                ", goodsType=" + goodsType +
                ", goodsValue='" + goodsValue + '\'' +
                ", code='" + code + '\'' +
                ", couponStartTime=" + couponStartTime +
                ", couponEndTime=" + couponEndTime +
                ", hasReceived=" + hasReceived +
                '}';
    }


}
