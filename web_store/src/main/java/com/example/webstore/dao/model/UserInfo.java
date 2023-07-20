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
@TableName("userinfo")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("account")
    private String account;

    @TableField("password")
    private String password;

    @TableField("touxiang")
    private String touxiang;

    @TableField("introduction")
    private String introduction;

    @TableField("wechat")
    private String wechat;

    @TableField("goodsaddress")
    private String goodsaddress;

    @TableField("nickname")
    private String nickname;

}
