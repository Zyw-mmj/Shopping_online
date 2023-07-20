package com.example.webstore.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shop_order")
public class ShopOrder {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField
    private String order_no;
    @TableField
    private Long user_id;
    @TableField
    private double total_price;
    @TableField
    private int pay_status;
    @TableField
    private int pay_type;
    @TableField
    private LocalDateTime pay_time;
    @TableField
    private int order_status;
    @TableField
    private String extra_info;
    @TableField
    private String user_name;
    @TableField
    private String user_phone;
    @TableField
    private String user_address;
    @TableField
    private int is_deleted;
    @TableField
    private LocalDateTime create_time;
    @TableField
    private LocalDateTime update_time;


    public void setId(Long id) {
        this.id = id;
    }
}
