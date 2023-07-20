package com.example.webstore.service;

import com.example.webstore.dao.model.Businessfeedback;
import com.example.webstore.domain.BusinessfeedbackBo;
import com.example.webstore.domain.FeedbackBo;

import java.util.List;

public interface IBusinessfeedbackService {
    BusinessfeedbackBo createbusinessFeedback(BusinessfeedbackBo businessfeedbackBo);
    List<BusinessfeedbackBo> getBusinessFeedback(Long user_id,Long order_id,Long good_id,String business_account);
}
