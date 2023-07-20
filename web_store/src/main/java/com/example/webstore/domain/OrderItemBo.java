package com.example.webstore.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author William Lee
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemBo {
    private Long orderItemId;

    private Long orderId;

    private Long goodsId;

    private String goodsName;

    private String goodsCoverImg;

    private Integer sellingPrice;

    private Integer goodsCount;

    private Date createTime;

    private String finishtime;

    private Integer goodsStatus;

    public OrderItemBo(Long orderId, Long goodsId, String goodsName, Integer sellingPrice, Integer goodsCount) {
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.sellingPrice = sellingPrice;
        this.goodsCount = goodsCount;
    }
}
