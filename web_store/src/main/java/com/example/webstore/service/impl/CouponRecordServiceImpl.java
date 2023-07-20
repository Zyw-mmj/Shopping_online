package com.example.webstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.webstore.dao.CouponRecordRepository;
import com.example.webstore.dao.mapper.CouponRecordMapper;
import com.example.webstore.dao.model.CouponRecord;
import com.example.webstore.domain.CouponRecordBo;
import com.example.webstore.service.ICouponRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author William Lee
 */
@Service
public class CouponRecordServiceImpl implements ICouponRecordService {

    @Autowired
    private CouponRecordRepository couponRecordRepository;

    @Autowired
    private CouponRecordMapper couponRecordMapper;

    @Override
    public List<CouponRecordBo> getUserCoupons(Long userId) {
        QueryWrapper<CouponRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<CouponRecord> couponRecords = couponRecordRepository.selectList(queryWrapper);

//        Iterator<CouponRecord> iterator = couponRecords.iterator();
//        while (iterator.hasNext()) {
//            CouponRecord couponRecord = iterator.next();
//            if (couponRecord.getIsDeleted() == 1) {
//                iterator.remove(); // 删除is_deleted=1的记录
//            }
//        }

        return couponRecords.stream()
                .map(this::convertToBo)
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateUserCoupons(Long userId, List<CouponRecordBo> couponRecords) {
        // Delete existing coupon records for the user
        QueryWrapper<CouponRecord> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("user_id", userId);
        couponRecordRepository.delete(deleteWrapper);

        // Insert new coupon records
        List<CouponRecord> recordsToInsert = couponRecords.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        return couponRecordRepository.saveBatch(recordsToInsert);
    }

    @Override
    public boolean deleteCouponRecordsByCouponId(Long couponId) {
        QueryWrapper<CouponRecord> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("coupon_id", couponId);
        return couponRecordRepository.delete(deleteWrapper);
    }

    @Override
    public boolean setUseStatusByUserIdAndCouponId(Long userId, Long couponId, Integer useStatus) {
        CouponRecord entity = CouponRecord.create(null, userId, couponId, useStatus, null, null, null, null, 0);
        QueryWrapper<CouponRecord> updateWrapper = new QueryWrapper<>();
        updateWrapper.eq("user_id", userId);
        updateWrapper.eq("coupon_id", couponId);
        return couponRecordMapper.update(entity, updateWrapper) > 0;
    }

    @Override
    public boolean deleteCouponRecordByUserIdAndCouponUserId(Long userId, Long couponUserId) {
        QueryWrapper<CouponRecord> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("user_id", userId);
        deleteWrapper.eq("coupon_user_id", couponUserId);
        return couponRecordRepository.delete(deleteWrapper);
    }

    @Override
    public boolean setUseStatusByCouponUserId(Long couponUserId, Integer useStatus) {
        CouponRecord updateEntity = new CouponRecord();
        updateEntity.setUseStatus(useStatus);

        QueryWrapper<CouponRecord> updateWrapper = new QueryWrapper<>();
        updateWrapper.eq("coupon_user_id", couponUserId);

        return couponRecordRepository.update(updateEntity, updateWrapper);
    }


    @Override
    public boolean setIsDeletedByCouponUserId(Long couponUserId) {
        CouponRecord updateRecord = new CouponRecord();
        updateRecord.setIsDeleted(1);
        QueryWrapper<CouponRecord> updateWrapper = new QueryWrapper<>();
        updateWrapper.eq("coupon_user_id", couponUserId);
        return couponRecordRepository.update(updateRecord, updateWrapper);
        // couponRecordRepository.delete(updateWrapper);
    }

    @Override
    public boolean createCouponRecord(CouponRecord couponRecord) {
        int rows = couponRecordRepository.insert(couponRecord);
        return rows > 0;
    }

    private CouponRecordBo convertToBo(CouponRecord entity) {
        return CouponRecordBo.builder()
                .couponUserId(entity.getCouponUserId())
                .userId(entity.getUserId())
                .couponId(entity.getCouponId())
                .useStatus(entity.getUseStatus())
                .usedTime(entity.getUsedTime())
                .orderId(entity.getOrderId())
                .createTime(entity.getCreateTime())
                .updateTime(entity.getUpdateTime())
                .isDeleted(entity.getIsDeleted())
                .build();
    }

    private CouponRecord convertToEntity(CouponRecordBo bo) {
        return CouponRecord.builder()
                .couponUserId(bo.getCouponUserId())
                .userId(bo.getUserId())
                .couponId(bo.getCouponId())
                .useStatus(bo.getUseStatus())
                .usedTime(bo.getUsedTime())
                .orderId(bo.getOrderId())
                .createTime(bo.getCreateTime())
                .updateTime(bo.getUpdateTime())
                .isDeleted(bo.getIsDeleted())
                .build();
    }
}
