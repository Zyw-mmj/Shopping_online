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

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("business")
public class Business implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("account")
    private String account;

    @TableField("password")
    private String password;

    @TableField("grade")
    private Integer grade;

    @TableField("wallet")
    private BigDecimal wallet;

    @TableField("status")
    private String status;

    @TableField("email")
    private String email;

    @TableField("phonenumber")
    private String phonenumber;

    @TableField("shoptype")
    private String shoptype;

    @TableField("name")
    private String name;

    @TableField("amount")
    private double amount;

    @TableField("quantity")
    private int quantity;

    @TableField("fee")
    private double fee;

    @TableField("starttime")
    private String starttime;

    @TableField("endtime")
    private String  endtime;

}
