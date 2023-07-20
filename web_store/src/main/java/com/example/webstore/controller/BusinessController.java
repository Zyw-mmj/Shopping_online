package com.example.webstore.controller;

import com.example.webstore.domain.BusinessBo;
import com.example.webstore.domain.ResultInfo;
import com.example.webstore.domain.UserBo;
import com.example.webstore.service.IBusinessService;
import com.example.webstore.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Business")
@Slf4j
public class BusinessController {
    @Autowired
    private IBusinessService businessService;

    @GetMapping("/getBusinesses")
    public ResultInfo getBusinesses() {  // 查
        log.info("成功运行！");
        List<BusinessBo> BusinessList = businessService.getBusinesses();
        log.info("School Bos:{}", BusinessList);

        return ResultInfo.success(BusinessList);
    }

    @GetMapping("/getBusinessInfo")
    public ResultInfo getUserInfo(@RequestParam("id") String id) {  // 查
        Long theId=Long.valueOf(id);
        BusinessBo userInfoBo = businessService.getBusinessByBusinessId(theId);
        return ResultInfo.success(userInfoBo);
    }
}
