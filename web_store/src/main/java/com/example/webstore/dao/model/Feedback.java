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
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("feedback")
public class Feedback implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("orderId")
    private Long orderId;

    @TableField("productId")
    private Long productId;

    @TableField("transactionStatus")
    private String transactionStatus;

    @TableField("productStatus")
    private String productStatus;

    @TableField("transactionFeedback")
    private String transactionFeedback;

    @TableField("productFeedback")
    private String productFeedback;

    @TableField("transactionRating")
    private int  transactionRating;

    @TableField("productRating")
    private int  productRating;

    @TableField("createdDate")
    private LocalDateTime createdDate;
}
