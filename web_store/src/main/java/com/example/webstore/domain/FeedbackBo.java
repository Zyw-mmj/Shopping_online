package com.example.webstore.domain;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackBo {
    private int id;
    private Long orderId;
    private Long productId;
    private String transactionStatus;
    private String productStatus;
    private String transactionFeedback;
    private String productFeedback;
    private int transactionRating;
    private int productRating;
    private LocalDateTime createdDate;
    private String goods_name;
    private String name;
}
