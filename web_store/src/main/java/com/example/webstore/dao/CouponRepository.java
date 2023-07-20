package com.example.webstore.dao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webstore.dao.mapper.CouponMapper;
import com.example.webstore.dao.model.Coupon;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author William Lee
 */
@Repository
public class CouponRepository extends ServiceImpl<CouponMapper, Coupon> {

    public List<Coupon> getAll() {return list();
    }

    public Coupon create(Coupon coupon) {save(coupon);
        return coupon;
    }

    public void update(Coupon existingCoupon) {  updateById(existingCoupon);
    }

    public void delete(Long id) {removeById(id);
    }
}