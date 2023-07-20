package com.example.webstore.dao.model;

import ch.qos.logback.classic.spi.LoggerContextVO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author William Lee
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_coupon_record")
public class CouponRecord {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(value = "coupon_user_id", type = IdType.AUTO)
    private Long couponUserId;
    @TableField("user_id")
    private Long userId;
    @TableField("coupon_id")
    private Long couponId;
    @TableField("use_status")
    private Integer useStatus;
    @TableField("used_time")
    private Integer usedTime;
    @TableField("order_id")
    private Integer orderId;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("update_time")
    private LocalDateTime updateTime;
    @TableField("is_deleted")
    private int isDeleted;

    public CouponRecord() {

    }

    public static CouponRecord create(Long couponUserId, Long userId, Long couponId, Integer useStatus, Integer usedTime, Integer orderId, LocalDateTime createTime, LocalDateTime updateTime, int isDeleted) {
        return new CouponRecord(couponUserId, userId, couponId, useStatus, usedTime, orderId, createTime, updateTime, isDeleted);
    }
    public CouponRecord(Long couponUserId, Long userId, Long couponId, Integer useStatus, Integer usedTime, Integer orderId, LocalDateTime createTime, LocalDateTime updateTime, int isDeleted) {
        this.couponUserId = couponUserId;
        this.userId = userId;
        this.couponId = couponId;
        this.useStatus = useStatus;
        this.usedTime = usedTime;
        this.orderId = orderId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDeleted = isDeleted;
    }

}
