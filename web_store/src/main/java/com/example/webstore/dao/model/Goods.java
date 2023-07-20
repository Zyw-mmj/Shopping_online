package com.example.webstore.dao.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shoplist")
public class Goods implements Serializable {
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

}
