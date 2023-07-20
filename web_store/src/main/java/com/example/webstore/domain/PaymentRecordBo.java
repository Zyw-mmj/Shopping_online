package com.example.webstore.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRecordBo {
    private Long id;

    private Long userId;

    private Long orderId;

    private BigDecimal amount;

    private String channel;

    private String tradeNo;

    private String status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public PaymentRecordBo(Long orderId, Long userId, BigDecimal x, String status) {
        this.orderId=orderId;
        this.userId=userId;
        this.amount=x;
        this.status=status;
    }
}
