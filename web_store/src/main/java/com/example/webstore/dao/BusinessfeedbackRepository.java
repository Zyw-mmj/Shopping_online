package com.example.webstore.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webstore.dao.mapper.BusinessMapper;
import com.example.webstore.dao.mapper.BusinessfeedbackMapper;
import com.example.webstore.dao.model.Business;
import com.example.webstore.dao.model.Businessfeedback;
import com.example.webstore.domain.BusinessfeedbackBo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BusinessfeedbackRepository extends ServiceImpl<BusinessfeedbackMapper, Businessfeedback> {

    public List<BusinessfeedbackBo> getBusinessFeedback(Long user_id,Long order_id , Long good_id,String business_account) {
        return baseMapper.getBusinessFeedback(user_id,order_id,good_id,business_account);
    }
    public List<Integer> getTotalRating(Long order_id) {
        return baseMapper.getTotalRating(order_id);
    }

    public Boolean updateUserRating(Long order_id, int rating) {
        return baseMapper.updateUserRating(order_id,rating);
    }
}
