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
@TableName("userslist")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("account")
    private String account;

    @TableField("password")
    private String password;

    @TableField("phonenumber")
    private String phonenumber;

    @TableField("email")
    private String email;

    @TableField("city")
    private String city;

    @TableField("gender")
    private String gender;

    @TableField("bankaccount")
    private String bankaccount;

    @TableField("shoptype")
    private String shoptype;

    @TableField("status")
    private String status;

    @TableField("rating")
    private int rating;

}
