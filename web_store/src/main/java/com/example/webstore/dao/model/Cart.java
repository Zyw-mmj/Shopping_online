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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.time.LocalTime.now;

/**
 * @author William Lee
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shopping_cart")
public class Cart implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("userId")
    private Long userId;

    @TableField("productId")
    private Long productId;

    @TableField("quantity")
    private Long quantity;

    @TableField("price")
    private BigDecimal price;

    @TableField("goodsname")
    private String goodsname;

    @TableField("path")
    private String path;

    @TableField("createdAt")
    private LocalDateTime createdAt;

    @TableField("updatedAt")
    private LocalDateTime updatedAt;

    public void setUpdateTime() {
        this.updatedAt = LocalDateTime.now();
    }
}
