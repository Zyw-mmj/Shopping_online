package com.example.webstore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsBo {
    private Long id;
    private double price;  //价格
    private String categorie;
    private String size;
    private String picture;
    private String describes;
    private String goodsname;
    private double rating;
    private double sales;
    private String businessAccount;
    private Long stock;
    private String tradeway;
    private String status;
    private int goods_count;
    private int goods_status;
    private Long order_id;
    private LocalDateTime create_time;
    private BigDecimal itemtotalprice;

    public GoodsBo(Long id, double price, String categorie, String size, String picture, String describes, String goodsname, double rating, double sales) {
        this.id = id;
        this.price = price;
        this.categorie = categorie;
        this.size = size;
        this.picture = picture;
        this.describes = describes;
        this.goodsname = goodsname;
        this.rating = rating;
        this.sales = sales;
    }


    public GoodsBo(String goodsname,String categorie,String size, String describes,Long stock,double price, String tradeway,String businessAccount,String picture) {
        this.price = price;
        this.categorie = categorie;
        this.size = size;
        this.describes = describes;
        this.goodsname = goodsname;
        this.businessAccount = businessAccount;
        this.stock = stock;
        this.tradeway = tradeway;
        this.picture=picture;
    }

    public GoodsBo(Long id, double price, String categorie, String size, String describes,
                   String goodsname, double rating, double sales, Long stock, String tradeway) {
        this.id=id;
        this.price=price;
        this.categorie=categorie;
        this.size=size;
        this.describes=describes;
        this.goodsname=goodsname;
        this.rating=rating;
        this.sales=sales;
        this.stock=stock;
        this.tradeway=tradeway;
    }

    public GoodsBo(Long id, double price, String categorie, String size, String picture, String describes, String goodsname, double rating, double sales, String status) {
        this.id=id;
        this.price=price;
        this.categorie=categorie;
        this.size=size;
        this.describes=describes;
        this.goodsname=goodsname;
        this.rating=rating;
        this.sales=sales;
        this.picture=picture;
        this.status=status;
    }

    public GoodsBo(Long id, double price, String categorie, String size, String picture, String describes, String goodsname, double rating, double sales, String status, String businessAccount) {
        this.id=id;
        this.price=price;
        this.categorie=categorie;
        this.size=size;
        this.describes=describes;
        this.goodsname=goodsname;
        this.rating=rating;
        this.sales=sales;
        this.picture=picture;
        this.status=status;
        this.businessAccount = businessAccount;
    }

    public GoodsBo(Long id, String goodsname, double price,String status,String businessAccount) {
        this.id = id;
        this.goodsname = goodsname;
        this.price = price;
        this.status =status;
        this.businessAccount = businessAccount;
    }

    public GoodsBo(Long id, double price, String categorie, String size, String describes,
                   String goodsname, double rating, double sales, Long stock, String tradeway,
                   int goods_count, String status, int goods_status, long order_id) {
        this.id=id;
        this.price=price;
        this.categorie=categorie;
        this.size=size;
        this.describes=describes;
        this.goodsname=goodsname;
        this.rating=rating;
        this.sales=sales;
        this.stock = stock;
        this.tradeway = tradeway;
        this.goods_count = goods_count;
        this.status=status;
        this.goods_status = goods_status;
        this.order_id = order_id;
    }
    public GoodsBo(Long id, double price, String categorie, String size, String describes,
                   String goodsname, double rating, double sales, Long stock, String tradeway, String status) {
        this.id=id;
        this.price=price;
        this.categorie=categorie;
        this.size=size;
        this.describes=describes;
        this.goodsname=goodsname;
        this.rating=rating;
        this.sales=sales;
        this.stock = stock;
        this.tradeway = tradeway;
        this.status=status;
    }

    public GoodsBo(Long id, double price, String categorie, String size,
                   String describes, String goodsname, double rating,
                   double sales, Long stock, String tradeway, int goods_count,
                   String status, int goods_status, long order_id,
                   String businessAccount) {
        this.id=id;
        this.price=price;
        this.categorie=categorie;
        this.size=size;
        this.describes=describes;
        this.goodsname=goodsname;
        this.rating=rating;
        this.sales=sales;
        this.stock = stock;
        this.tradeway = tradeway;
        this.goods_count = goods_count;
        this.status=status;
        this.goods_status = goods_status;
        this.order_id = order_id;
        this.businessAccount=businessAccount;
    }

    public GoodsBo(Long id, double price, String categorie, String size, String picture,
                   String describes, String goodsname, double rating, double sales,
                   String status, String businessAccount, Long stock, String tradeway) {
        this.id=id;
        this.price=price;
        this.categorie=categorie;
        this.size=size;
        this.picture = picture;
        this.describes=describes;
        this.goodsname=goodsname;
        this.rating=rating;
        this.sales=sales;
        this.status=status;
        this.businessAccount=businessAccount;
        this.stock = stock;
        this.tradeway = tradeway;
    }

    public GoodsBo(Long id, double price, String categorie, String size, String describes, String goodsname, double rating, double sales, Long stock, String tradeway,
                   int goods_count, String status, int goods_status, long order_id,
                   String businessAccount, LocalDateTime create_time, BigDecimal itemtotalprice) {
        this.id=id;
        this.price=price;
        this.categorie=categorie;
        this.size=size;
        this.describes=describes;
        this.goodsname=goodsname;
        this.rating=rating;
        this.sales=sales;
        this.status=status;
        this.businessAccount=businessAccount;
        this.stock = stock;
        this.tradeway = tradeway;
        this.goods_count=goods_count;
        this.goods_status=goods_status;
        this.order_id=order_id;
        this.create_time=create_time;
        this.itemtotalprice=itemtotalprice;
    }

    public GoodsBo(Long id, double price, String categorie, String size, String describes, String goodsname,
                   double rating, double sales, Long stock, String tradeway, int goods_count, String status,
                   int goods_status, long order_id, LocalDateTime create_time, BigDecimal itemtotalprice) {
        this.id=id;
        this.price=price;
        this.categorie=categorie;
        this.size=size;
        this.describes=describes;
        this.goodsname=goodsname;
        this.rating=rating;
        this.sales=sales;
        this.status=status;
        this.stock = stock;
        this.tradeway = tradeway;
        this.goods_count=goods_count;
        this.goods_status=goods_status;
        this.order_id=order_id;
        this.create_time=create_time;
        this.itemtotalprice=itemtotalprice;
    }
}
