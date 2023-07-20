package com.example.webstore.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author William Lee
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("coupon")
public class Coupon implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long couponId;
    @TableField("coupon_name")
    private String couponName;
    @TableField("coupon_desc")
    private String couponDesc;
    @TableField("coupon_total")
    private Integer couponTotal;
    @TableField("discount")
    private Integer discount;
    @TableField("min")
    private Integer min;
    @TableField("coupon_limit")
    private Byte couponLimit;
    @TableField("coupon_type")
    private Byte couponType;
    @TableField("coupon_status")
    private Byte couponStatus;
    @TableField("goods_type")
    private Byte goodsType;
    @TableField("goods_value")
    private String goodsValue;
    @TableField("coupon_code")
    private String couponCode;
    @TableField("coupon_start_time")
    private LocalDateTime couponStartTime;
    @TableField("coupon_end_time")
    private LocalDateTime couponEndTime;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("update_time")
    private LocalDateTime updateTime;
    @TableField("is_deleted")
    private Byte isDeleted;

    public Long getId() {
        return this.couponId;
    }
}
