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
public class BusinessBo {
    private Long id;
    private String account;
    private String password;
    private int grade;
    private BigDecimal wallet;
    private String status;
    private String email;
    private String phonenumber;
    private String shoptype;
    private String name;
    private double amount;
    private int quantity;
    private double fee;
    private String starttime;
    private String endtime;

    public BusinessBo(String account, String password){
        this.id=id;
        this.account=account;
        this.password=password;
    }

    public BusinessBo(Long id, String account, String password, Integer grade,
                      BigDecimal wallet, String status) {
        this.id=id;
        this.account=account;
        this.password=password;
        this.grade=grade;
        this.wallet=wallet;
        this.status=status;
    }

    public BusinessBo(String name,String account, String thePassword, String phonenumber, String email) {
        this.name = name;
        this.account=account;
        this.password=thePassword;
        this.phonenumber=phonenumber;
        this.email=email;
    }
    public BusinessBo(Long id, String account, String status, String email, String shoptype) {
        this.id = id;
        this.account = account;
        this.status = status;
        this.email = email;
        this.shoptype = shoptype;
    }
    public BusinessBo(Long id, String account, String status, String email, String shoptype, String name, String phoneNumber) {
        this.id = id;
        this.account = account;
        this.status = status;
        this.email = email;
        this.shoptype = shoptype;
        this.name = name;
        this.phonenumber = phoneNumber;
    }

    public BusinessBo(Long id, String name, String account,String email,String shoptype,String status) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.email = email;
        this.shoptype = shoptype;
        this.status = status;
    }

    public BusinessBo(Long id, String name, String account,String email,String shoptype,String status,String starttime,String endtime) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.email = email;
        this.shoptype = shoptype;
        this.status = status;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public BusinessBo(Long id, String account, String name, double amount, int quantity, int grade,double fee,String status) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.amount = amount;
        this.quantity = quantity;
        this.grade = grade;
        this.fee = fee;
        this.status = status;
    }

    public BusinessBo(Long id, String name, String account, int grade,int quantity,double amount,double fee) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.grade = grade;
        this.quantity =quantity;
        this.amount = amount;
        this.fee = fee;
    }

    public BusinessBo(Long id, String name, String account,
                      String email, String shoptype, String status,
                      Integer grade, BigDecimal wallet, String phonenumber, double amount,
                      int quantity)
    {
        this.id = id;
        this.name = name;
        this.account = account;
        this.email = email;
        this.shoptype = shoptype;
        this.status = status;
        this.grade = grade;
        this.wallet=wallet;
        this.phonenumber = phonenumber;
        this.amount = amount;
        this.quantity =quantity;
    }
}
