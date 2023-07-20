package com.example.webstore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBo {
    private Long id;
    private String name;
    private String account;
    private String password;
    private String phonenumber;
    private String email;
    private String city;
    private String gender;
    private String bankaccount;
    private String shoptype;
    private String status;
    private int rating;


    public UserBo(String name, String account, String password,String phonenumber,String email,String city,String gender,String bankaccount,String shoptype) {
        this.id=id;
        this.name=name;
        this.account=account;
        this.password=password;
        this.phonenumber=phonenumber;
        this.email=email;
        this.city=city;
        this.gender=gender;
        this.bankaccount=bankaccount;
        this.shoptype=shoptype;
    }



    public UserBo(Long id, String name, String account, String password) {
        this.id=id;
        this.name=name;
        this.account=account;
        this.password=password;
    }

    public UserBo(Long id, String name, String account, String password, String status) {
        this.id=id;
        this.name=name;
        this.account=account;
        this.password=password;
        this.status=status;
    }

    public UserBo(Long id, String name, String account, String email, String shoptype, String status) {
        this.id=id;
        this.name=name;
        this.account=account;
        this.email=email;
        this.shoptype=shoptype;
        this.status=status;
    }

    public UserBo(Long id, String name, String account, String email, String shoptype, String status, String phonenumber) {
        this.id=id;
        this.name=name;
        this.account=account;
        this.email=email;
        this.shoptype=shoptype;
        this.status=status;
        this.phonenumber = phonenumber;
    }
}
