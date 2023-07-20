package com.example.webstore.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * @author William Lee
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartBo {
    private Long id;
    private Long userId;
    private Long productId;
    private Long quantity;
    private BigDecimal price;

    private String goodsname;

    private String path;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    public CartBo(Long id, Long userId, Long productId, Long quantity, BigDecimal price, LocalDateTime createdAt, LocalDateTime updatedAt) {
//        this.id=id;
//        this.userId=userId;
//        this.productId=productId;
//        this.quantity=quantity
//    }

    public CartBo(Long theId, Long theProductID, Long quantity,BigDecimal price) {
        this.userId=theId;
        this.productId=theProductID;
        this.quantity=quantity;
        this.price=price;
    }

    @Override
    public String toString() {
        return "CartBo张有为666{" +
                "id=" + id +
                ", user_id=" + userId +
                ", product_id=" + productId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", goodsname=" +  goodsname +
                ", path=" +path +
                ", created_at=" + createdAt +
                ", updated_at=" + updatedAt +
                '}';
    }



}
