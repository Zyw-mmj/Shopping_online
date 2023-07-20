package com.example.webstore.service;

import com.example.webstore.dao.model.CouponRecord;
import com.example.webstore.domain.CouponRecordBo;

import java.util.List;
import java.util.Map;

/**
 * @author William Lee
 */
public interface ICouponRecordService {

    List<CouponRecordBo> getUserCoupons(Long userId);

    boolean updateUserCoupons(Long userId, List<CouponRecordBo> couponRecords);

    boolean deleteCouponRecordsByCouponId(Long couponId);


    boolean setUseStatusByUserIdAndCouponId(Long userId, Long couponId, Integer useStatus);

    boolean deleteCouponRecordByUserIdAndCouponUserId(Long userId, Long couponUserId);

    boolean setUseStatusByCouponUserId(Long couponUserId, Integer useStatus);

    boolean setIsDeletedByCouponUserId(Long couponUserId);

    boolean createCouponRecord(CouponRecord couponRecord);
}
