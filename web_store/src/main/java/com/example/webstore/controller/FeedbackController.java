package com.example.webstore.controller;

import com.example.webstore.dao.model.Businessfeedback;
import com.example.webstore.dao.model.Feedback;
import com.example.webstore.domain.BusinessfeedbackBo;
import com.example.webstore.domain.FeedbackBo;
import com.example.webstore.domain.ResultInfo;
import com.example.webstore.service.IBusinessfeedbackService;
import com.example.webstore.service.IFeedbackService;
import com.example.webstore.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Feedback")
@Slf4j
public class FeedbackController {
    @Autowired
    private IFeedbackService feedbackService;

    @Autowired
    private IBusinessfeedbackService businessfeedbackService;

    @Autowired
    private IGoodsService goodsService;

    // 创建反馈数据
    @PostMapping("/")
    public ResultInfo createFeedback(@RequestBody FeedbackBo feedbackBo) {
        Long product_Id = feedbackBo.getProductId();
        log.info(String.valueOf(product_Id));
        List<Integer> Allrating = feedbackService.getTotalRating(product_Id);
        int sum = 0;
        for (Integer integer : Allrating) {
            sum += integer;
        }
        log.info(String.valueOf(sum));
//        if(Allrating.size()!=0){
//            int rating = sum+feedbackBo.getProductRating()/Allrating.size()+1;
//            Boolean t = goodsService.updategoodsRating(product_Id,rating);
//        }

        int rating = (sum+feedbackBo.getProductRating())/(Allrating.size()+1);
        Boolean t = goodsService.updategoodsRating(product_Id,rating);
        feedbackService.createFeedback(feedbackBo);
        return ResultInfo.success(feedbackBo);
    }

    @GetMapping("/UsergetFeedback/{user_id}")
    public ResultInfo getFeedback(@PathVariable("user_id") Long user_id) {
        List<FeedbackBo> feedbackBoList = feedbackService.UsergetFeedback(user_id);
        return ResultInfo.success(feedbackBoList);
    }

    // 创建反馈数据
    @PostMapping("/Businessfeedback")
    public ResultInfo createBusinessFeedback(@RequestBody BusinessfeedbackBo businessfeedbackBo) {
        businessfeedbackService.createbusinessFeedback(businessfeedbackBo);
        return ResultInfo.success(businessfeedbackBo);
    }

    @GetMapping("/BusinessgetFeedback")
    public ResultInfo getBusinessFeedback(@RequestParam("user_id") Long user_id,@RequestParam("order_id") Long order_id,
                                          @RequestParam("good_id") Long good_id,@RequestParam("business_account") String business_account) {

        List<BusinessfeedbackBo> businessfeedbackBoList = businessfeedbackService.getBusinessFeedback(user_id,order_id,good_id,business_account);
        return ResultInfo.success(businessfeedbackBoList);
    }
}
