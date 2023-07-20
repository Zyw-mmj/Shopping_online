package com.example.webstore.service;

import com.example.webstore.dao.model.Feedback;
import com.example.webstore.domain.FeedbackBo;

import java.util.List;

public interface IFeedbackService {
    FeedbackBo createFeedback(FeedbackBo feedbackBo);
    List<Integer> getTotalRating(Long product_Id);
    List<FeedbackBo> UsergetFeedback(Long user_id);
}
