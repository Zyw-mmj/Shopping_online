package com.example.webstore.controller;

import com.example.webstore.dao.model.CouponRecord;
import com.example.webstore.domain.CouponRecordBo;
import com.example.webstore.domain.ResultInfo;
import com.example.webstore.service.ICouponRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author William Lee
 */
@RestController
@RequestMapping("/userCoupon")
@Slf4j
public class CouponRecordController {
    @Autowired
    private ICouponRecordService couponRecordService;

    @GetMapping("/{userId}")
    public List<CouponRecordBo> getCouponRecordsByUserId(@PathVariable Long userId) {
        return  couponRecordService.getUserCoupons(userId);
    }
    @PostMapping("/")
    public ResultInfo createCouponRecord(@RequestBody CouponRecordBo couponRecordBo) {
        CouponRecord couponRecord = convertToEntity(couponRecordBo);
        boolean success = couponRecordService.createCouponRecord(couponRecord);
        if (success) {
            return ResultInfo.success("Coupon record created successfully");
        } else {
            return ResultInfo.error("Failed to create coupon record");
        }
    }

    private CouponRecord convertToEntity(CouponRecordBo couponRecordBo) {
        CouponRecord couponRecord = new CouponRecord();
        couponRecord.setCouponUserId(couponRecordBo.getCouponUserId());
        couponRecord.setUserId(couponRecordBo.getUserId());
        couponRecord.setCouponId(couponRecordBo.getCouponId());
        couponRecord.setUseStatus(couponRecordBo.getUseStatus());
        couponRecord.setUsedTime(couponRecordBo.getUsedTime());
        couponRecord.setOrderId(couponRecordBo.getOrderId());
        couponRecord.setCreateTime(couponRecordBo.getCreateTime());
        couponRecord.setUpdateTime(couponRecordBo.getUpdateTime());
        couponRecord.setIsDeleted(couponRecordBo.getIsDeleted());

        // Perform additional conversions if needed

        return couponRecord;
    }

    @PutMapping("/{userId}")
    public ResultInfo updateCouponRecordsByUserId(@PathVariable Long userId, @RequestBody List<CouponRecordBo> couponRecords) {
        boolean success = couponRecordService.updateUserCoupons(userId, couponRecords);
        if (success) {
            return ResultInfo.success("Coupon records updated successfully");
        } else {
            return ResultInfo.error("Failed to update coupon records");
        }
    }

    @DeleteMapping("/{couponId}")
    public ResultInfo deleteCouponRecordsByCouponId(@PathVariable Long couponId) {
        boolean success = couponRecordService.deleteCouponRecordsByCouponId(couponId);
        if (success) {
            return ResultInfo.success("Coupon records deleted successfully");
        } else {
            return ResultInfo.error("Failed to delete coupon records");
        }
    }

    @PutMapping("/{userId}/{couponId}/useStatus")
    public ResultInfo setUseStatusByUserIdAndCouponId(@PathVariable Long userId, @PathVariable Long couponId, @RequestParam Integer useStatus) {
        boolean success = couponRecordService.setUseStatusByUserIdAndCouponId(userId, couponId, useStatus);
        if (success) {
            return ResultInfo.success("Use status updated successfully");
        } else {
            return ResultInfo.error("Failed to update use status");
        }
    }

    @DeleteMapping("/{userId}/{couponUserId}")
    public ResultInfo deleteCouponRecordByUserIdAndCouponUserId(@PathVariable Long userId, @PathVariable Long couponUserId) {
        boolean success = couponRecordService.deleteCouponRecordByUserIdAndCouponUserId(userId, couponUserId);
        if (success) {
            return ResultInfo.success("Coupon record deleted successfully");
        } else {
            return ResultInfo.error("Failed to delete coupon record");
        }
    }


    @PutMapping("/{couponUserId}/useStatus")
    public ResultInfo setUseStatusByCouponUserId(@PathVariable Long couponUserId, @RequestParam Integer useStatus) {
        boolean success = couponRecordService.setUseStatusByCouponUserId(couponUserId, useStatus);
        if (success) {
            return ResultInfo.success("Use status updated successfully");
        } else {
            return ResultInfo.error("Failed to update use status");
        }
    }

    @PutMapping("/{couponUserId}/isDeleted")
    public ResultInfo setIsDeletedByCouponUserId(@PathVariable Long couponUserId) {
        boolean success =couponRecordService.setIsDeletedByCouponUserId(couponUserId);
        if (success) {
            return ResultInfo.success("isDeleted flag updated successfully");
        } else {
            return ResultInfo.error("Failed to update isDeleted flag");
        }
    }
}
