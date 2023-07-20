package com.example.webstore.dao.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author William Lee
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("payment_record")
public class PaymentRecord {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "order_id")
    private Long orderId;

    @TableField
    private BigDecimal amount;

    @TableField
    private String channel;

    @TableField(value = "trade_no")
    private String tradeNo;

    @TableField
    private String status;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
