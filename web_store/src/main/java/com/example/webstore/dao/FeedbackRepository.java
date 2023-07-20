package com.example.webstore.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webstore.dao.mapper.FeedbackMapper;
import com.example.webstore.dao.model.Feedback;
import com.example.webstore.domain.FeedbackBo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackRepository extends ServiceImpl<FeedbackMapper, Feedback> {
    public List<Integer> getTotalRating(Long product_Id){
        return baseMapper.getTotalRating(product_Id);
    };
    public List<FeedbackBo> UsergetFeedback(Long user_id){
        return baseMapper.UsergetFeedback(user_id);
    };
}
