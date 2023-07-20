package com.example.webstore.service.impl;

import com.example.webstore.dao.FeedbackRepository;
import com.example.webstore.dao.model.Feedback;
import com.example.webstore.domain.FeedbackBo;
import com.example.webstore.service.IFeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class FeedbackServiceImpl implements IFeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public FeedbackBo createFeedback(FeedbackBo feedbackBo) {
        Feedback feedback = new Feedback();
        feedback.setOrderId(feedbackBo.getOrderId());
        feedback.setProductId(feedbackBo.getProductId());
        feedback.setProductStatus(feedbackBo.getProductStatus());
        feedback.setTransactionStatus(feedbackBo.getTransactionStatus());
        feedback.setTransactionFeedback(feedbackBo.getTransactionFeedback());
        feedback.setProductFeedback(feedbackBo.getProductFeedback());
        feedback.setProductRating(feedbackBo.getProductRating());
        feedback.setTransactionRating(feedbackBo.getTransactionRating());
        LocalDateTime currentDateTime = LocalDateTime.now();
        feedback.setCreatedDate(currentDateTime);
        feedbackRepository.save(feedback);
        return feedbackBo;
    }

    @Override
    public List<Integer> getTotalRating(Long product_Id) {
        return feedbackRepository.getTotalRating(product_Id);
    }

    @Override
    public List<FeedbackBo> UsergetFeedback(Long user_id) {
        return feedbackRepository.UsergetFeedback(user_id);
    }
}
