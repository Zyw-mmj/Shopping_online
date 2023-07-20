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
import java.util.Date;

/**
 * @author William Lee
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_item")
public class OrderItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(value = "order_item_id", type = IdType.AUTO)
    private Long order_item_id;
    @TableField
    private Long order_id;
    @TableField
    private Long goods_id;
    @TableField
    private String goods_name;
    @TableField
    private String goods_cover_img;
    @TableField
    private Integer selling_price;
    @TableField
    private Integer goods_count;
    @TableField
    private Date create_time;
    @TableField
    private String finishtime;
    @TableField
    private Integer goods_status;
    @TableField
    private String rejectreason;
}
