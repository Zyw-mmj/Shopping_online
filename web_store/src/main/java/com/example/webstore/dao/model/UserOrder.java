package com.example.webstore.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("price")
    private double price;

    @TableField("categorie")
    private String categorie;

    @TableField("size")
    private String size;

    @TableField("picture")
    private String picture;

    @TableField("describes")
    private String describes;

    @TableField("goodsname")
    private String goodsname;

    @TableField("rating")
    private double rating;

    @TableField("sales")
    private double sales;

    @TableField("businessAccount")
    private String businessAccount;

    @TableField("stock")
    private Long stock;

    @TableField("tradeway")
    private String tradeway;

    @TableField("status")
    private String status;

    @TableField("goods_count")
    private int goods_count;

    @TableField("goods_status")
    private int goods_status;

    @TableField("order_id")
    private long order_id;

    @TableField("create_time")
    private LocalDateTime create_time;

    @TableField("itemtotalprice")
    private BigDecimal itemtotalprice;

    @TableField("rejectreason")
    private BigDecimal rejectreason;

}
