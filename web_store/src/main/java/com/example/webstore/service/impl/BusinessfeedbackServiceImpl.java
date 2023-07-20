package com.example.webstore.service.impl;

import com.example.webstore.dao.BusinessfeedbackRepository;
import com.example.webstore.dao.FeedbackRepository;
import com.example.webstore.dao.model.Businessfeedback;
import com.example.webstore.domain.BusinessfeedbackBo;
import com.example.webstore.service.IBusinessfeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BusinessfeedbackServiceImpl implements IBusinessfeedbackService {

    @Autowired
    private BusinessfeedbackRepository businessfeedbackRepository;

    @Override
    public BusinessfeedbackBo createbusinessFeedback(BusinessfeedbackBo businessfeedbackBo) {
        Businessfeedback businessfeedback = new Businessfeedback();
        businessfeedback.setAccount(businessfeedbackBo.getAccount());
        businessfeedback.setOrder_id(businessfeedbackBo.getOrder_id());
        businessfeedback.setComment(businessfeedbackBo.getComment());
        businessfeedback.setRating(businessfeedbackBo.getRating());
        businessfeedbackRepository.save(businessfeedback);
        return businessfeedbackBo;
    }

    @Override
    public List<BusinessfeedbackBo> getBusinessFeedback(Long user_id, Long order_id,Long good_id,String business_account) {
        return businessfeedbackRepository.getBusinessFeedback(user_id,order_id,good_id,business_account);
    }
}
