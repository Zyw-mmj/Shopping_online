package com.example.webstore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarouselBo {
    private String account;
    private String name;
    private Long id;
    private String goodsname;
    private String describes;
    private double price;
    private String picture;
}
