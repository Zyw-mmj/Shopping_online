package com.example.webstore.controller;

import com.example.webstore.dao.model.Coupon;
import com.example.webstore.service.ICouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author William Lee
 */
@RestController
@RequestMapping("/Coupon")
@Slf4j
public class CouponController {

    private final ICouponService couponService;

    @Autowired
    public CouponController(ICouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/{id}")
    public Coupon getCouponById(@PathVariable("id") Long id) {
        Coupon coupon = couponService.getCouponById(id);
        if (coupon == null) {
            // You can customize the error handling based on your application requirements
            log.error("Coupon not found with ID: " + id);
        }else {
            log.info("已获取优惠卷！优惠卷id是："+id);
        }
        return coupon;
    }

    @GetMapping
    public List<Coupon> getAllCoupons() {
        log.info("已获取所有优惠卷！");
        return couponService.getAllCoupons();
    }

    @PostMapping
    public Coupon createCoupon(@RequestBody Coupon coupon) {
        log.info("成功创建优惠卷！优惠卷id是："+ coupon.getId());
        return couponService.createCoupon(coupon);
    }

    @PutMapping("/{id}")
    public Coupon updateCoupon(@PathVariable("id") Long id, @RequestBody Coupon coupon) {
        Coupon updatedCoupon = couponService.updateCoupon(id, coupon);
        if (updatedCoupon == null) {
            // You can customize the error handling based on your application requirements
            log.error("Coupon not found with ID: " + id);
        }
        return updatedCoupon;
    }

    @DeleteMapping("/{id}")
    public void deleteCoupon(@PathVariable("id") Long id) {
        couponService.deleteCoupon(id);
        log.info("成功删除优惠卷！优惠卷id是："+id);
    }
}
