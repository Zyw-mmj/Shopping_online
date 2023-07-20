package com.example.webstore.service.impl;

import com.example.webstore.dao.CouponRepository;
import com.example.webstore.dao.model.Coupon;
import com.example.webstore.service.ICouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author William Lee
 */
@Service
@Slf4j
public class CouponServiceImpl implements ICouponService {


    private final CouponRepository couponRepo;

    @Autowired
    public CouponServiceImpl(CouponRepository couponRepo) {
        this.couponRepo = couponRepo;
    }

    @Override
    public Coupon getCouponById(Long id) {
        return couponRepo.getById(id);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepo.getAll();
    }

    @Override
    public Coupon createCoupon(Coupon coupon) {

        return couponRepo.create(coupon);
    }

    @Override
    public Coupon updateCoupon(Long id, Coupon coupon) {
        Coupon existingCoupon = couponRepo.getById(id);
        if (existingCoupon != null) {
            // Update the existing coupon with the new values
            existingCoupon.setCouponName(coupon.getCouponName());
            existingCoupon.setCouponDesc(coupon.getCouponDesc());
            existingCoupon.setCouponTotal(coupon.getCouponTotal());
            existingCoupon.setDiscount(coupon.getDiscount());
            existingCoupon.setMin(coupon.getMin());
            existingCoupon.setCouponLimit(coupon.getCouponLimit());
            existingCoupon.setCouponType(coupon.getCouponType());
            existingCoupon.setCouponStatus(coupon.getCouponStatus());
            existingCoupon.setGoodsType(coupon.getGoodsType());
            existingCoupon.setGoodsValue(coupon.getGoodsValue());
            existingCoupon.setCouponCode(coupon.getCouponCode());
            existingCoupon.setCouponStartTime(coupon.getCouponStartTime());
            existingCoupon.setCouponEndTime(coupon.getCouponEndTime());
            existingCoupon.setUpdateTime(coupon.getUpdateTime());

            couponRepo.update(existingCoupon);
            return existingCoupon;
        }
        return null;
    }

    @Override
    public void deleteCoupon(Long id) {
        couponRepo.delete(id);
    }
}

