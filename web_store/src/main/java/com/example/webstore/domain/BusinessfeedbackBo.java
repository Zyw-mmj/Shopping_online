package com.example.webstore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessfeedbackBo {
    private Long id;
    private String account;
    private int order_id;
    private int rating;
    private String comment;
    private LocalDateTime created_at;
    private String goodsname;
    private String name;
}
