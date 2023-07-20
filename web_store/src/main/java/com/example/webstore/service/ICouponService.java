package com.example.webstore.service;

import com.example.webstore.dao.model.Coupon;

import java.util.List;

/**
 * @author William Lee
 */
public interface ICouponService {
    Coupon getCouponById(Long id);

    List<Coupon> getAllCoupons();

    Coupon createCoupon(Coupon coupon);

    Coupon updateCoupon(Long id, Coupon coupon);

    void deleteCoupon(Long id);
}
