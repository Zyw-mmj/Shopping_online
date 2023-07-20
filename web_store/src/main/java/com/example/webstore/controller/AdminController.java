package com.example.webstore.controller;

import com.example.webstore.dao.mapper.BusinessMapper;
import com.example.webstore.dao.mapper.UserMapper;
import com.example.webstore.dao.model.FeeRate;
import com.example.webstore.domain.*;
import com.example.webstore.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Admin")
@Slf4j
public class AdminController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IBusinessService businessService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IFeeService feeRateService;

    @Autowired
    private ICarouselService carouselService;

    @PostMapping("/approveUser")
    public ResultInfo approveUser(@RequestBody Map<String, Long> requestData) {  // 查
        log.info("成功运行！");
        Long userId = requestData.get("userId");
        log.info(String.valueOf(userId));
        // 根据 userId 获取用户信息
        UserBo userbo = userService.getUserByid(userId);
        log.info(String.valueOf(userbo.getName()));

        // 执行审核通过的逻辑操作
        userbo.setStatus("Approved"); // 根据实际需求修改状态值
        userService.updateUser(userbo);

        log.info("Goods Bos:{}", userbo);

        return ResultInfo.success(userbo);
    }

    @PostMapping("/approveBusiness")
    public ResultInfo approveBusiness(@RequestBody Map<String, Long> requestData) {  // 查
        log.info("成功运行！");
        Long userId = requestData.get("userId");
        log.info(String.valueOf(userId));
        // 根据 userId 获取用户信息
        BusinessBo businessBo = businessService.getBusinessByid(userId);
        log.info(String.valueOf(businessBo.getName()));

        // 执行审核通过的逻辑操作
        businessBo.setStatus("Approved"); // 根据实际需求修改状态值
        businessService.updateBusiness(businessBo);

        log.info("Goods Bos:{}", businessBo);

        return ResultInfo.success(businessBo);
    }

    @PostMapping("/rejectUser")
    public ResultInfo rejectUser(@RequestBody Map<String, Long> requestData) {  // 查
        log.info("成功运行！");
        Long userId = requestData.get("userId");
        // 根据 userId 获取用户信息
        UserBo userbo = userService.getUserByid(userId);

        // 执行审核通过的逻辑操作
        userbo.setStatus("Rejected"); // 根据实际需求修改状态值
        userService.updateUser(userbo);

        log.info("Goods Bos:{}", userbo);

        return ResultInfo.success(userbo);
    }

    @PostMapping("/rejectBusiness")
    public ResultInfo rejectBusiness(@RequestBody Map<String, Long> requestData) {  // 查
        log.info("成功运行！");
        Long userId = requestData.get("userId");
        log.info(String.valueOf(userId));
        // 根据 userId 获取用户信息
        BusinessBo businessBo = businessService.getBusinessByid(userId);
        log.info(String.valueOf(businessBo.getName()));

        // 执行审核通过的逻辑操作
        businessBo.setStatus("Rejected"); // 根据实际需求修改状态值
        businessService.updateBusiness(businessBo);

        log.info("Goods Bos:{}", businessBo);

        return ResultInfo.success(businessBo);
    }

    @GetMapping("/searchGoods")
    public ResultInfo searchGoods(@RequestParam("keyword") String keyword) {  // 模糊查询
        log.info("成功运行！12312313");
        List<GoodsBo> goodsList = new ArrayList<>();

        // 根据关键字从数据库中获取符合条件的商品数据
        if (keyword != null && !keyword.isEmpty()) {
            goodsList = goodsService.AdminfindByTitleContaining(keyword);
        } else {
            goodsList = goodsService.AdmingetGoods();
        }

        log.info("Goods Bos:{}", goodsList);

        return ResultInfo.success(goodsList);
    }
    @PutMapping("/updateUser")
    public ResultInfo updateUser(@RequestBody UserBo userBo){
        UserBo userBo1 = userService.getUserByid(userBo.getId());

        userBo1.setName(userBo.getName());
        userBo1.setEmail(userBo.getEmail());
        userBo1.setPhonenumber(userBo.getPhonenumber());
        userService.updateUserById(userBo1);

        return ResultInfo.success(userBo1);
    }

    @PutMapping("/updateBusiness")
    public ResultInfo updateBusiness(@RequestBody BusinessBo businessBo){
        BusinessBo businessBo1 = businessService.getBusinessByid(businessBo.getId());

        System.out.println(businessBo1.getName());
        businessBo1.setName(businessBo1.getName());
        businessBo1.setEmail(businessBo1.getEmail());
        businessBo1.setPhonenumber(businessBo1.getPhonenumber());
        System.out.println(businessBo1.getName());
        businessService.updateBusinessById(businessBo1);

        return ResultInfo.success(businessBo1);
    }

    @DeleteMapping("/deleteGood")
    public ResultInfo deleteGood(@RequestParam("id") Long id,@RequestParam("businessAccount") String businessAccount){
        GoodsBo product = goodsService.getGoodsByid(String.valueOf(id));
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        if (!product.getBusinessAccount().equals(businessAccount)) {
            throw new RuntimeException("商家账号与商品不匹配");
        }
        boolean f = goodsService.deleteGoods(String.valueOf(id));

        return ResultInfo.success(f);
    }

    @PostMapping ("/insertStartAndEndTime")
    public ResultInfo insertStartAndEndTime(@RequestParam("start") String start,@RequestParam("end") String end){
        BusinessBo businessBo = new BusinessBo();
        businessBo.setStarttime(start);
        businessBo.setEndtime(end);
        businessService.updateStartAndEndToAll(businessBo);
        return ResultInfo.success("设置时间段成功");
    }

    @GetMapping("/getMerchants")
    public ResultInfo getMerchants(){
        log.info("成功运行！");
        List<BusinessBo> BusinessList = businessService.getMerchants();
        log.info("School Bos:{}", BusinessList);
        return ResultInfo.success(BusinessList);
    }

    @PutMapping("/updateMerchantLevel")
    public ResultInfo updateMerchantGrade(@RequestBody BusinessBo businessBo) {
        log.info("1231231321！");
        BusinessBo businessBo1 = businessService.getBusinessByid(businessBo.getId());
        businessBo1.setGrade(businessBo.getGrade());
        businessService.updateMarchent(businessBo1);
        log.info("School Bos:{}", businessBo1);
        return ResultInfo.success(businessBo1);
    }

    @GetMapping("/getProductsByMerchantAccount/{merchantAccount}")
    public ResultInfo getProductsByMerchantAccount(@PathVariable String merchantAccount) {
        List<GoodsBo> products = goodsService.getProductsByMerchantAccount(merchantAccount);
        return ResultInfo.success(products);
    }

    @PostMapping("/withdrawAllProducts/{account}")
    public ResultInfo withdrawAllProducts(@PathVariable String account) {
        try {
            // 根据商家ID下架全部商品的逻辑
            goodsService.withdrawAllProducts(account);
            BusinessBo businessBo = businessService.getBusinessByAccount(account);
            if(businessBo.getStatus() =="Pending"){
                String s = "false";
                return ResultInfo.success(s);
            }else {
                businessBo.setStatus("Rejected");
                businessService.updateBusiness(businessBo);
                return ResultInfo.success("下架全部商品成功");
            }
        } catch (Exception e) {
            return ResultInfo.error("下架全部商品失败");
        }
    }

    @PostMapping("/recoverAllProducts/{account}")
    public ResultInfo recoverAllProducts(@PathVariable String account) {
        try {
            // 根据商家ID上架全部商品的逻辑
            goodsService.reoverAllProducts(account);
            BusinessBo businessBo = businessService.getBusinessByAccount(account);

            if(businessBo.getStatus() =="Pending"){
                String s = "false";
                return ResultInfo.success(s);
            }else {
                businessBo.setStatus("Approved");
                businessService.updateBusiness(businessBo);
                return ResultInfo.success("请等待管理员审核");
            }
        } catch (Exception e) {
            return ResultInfo.error("下架全部商品失败");
        }
    }

    @PostMapping("/checkPublishPermission")
    public boolean checkPublishPermission(@RequestParam("merchantId") Long merchantId) {
        // 根据商家ID查询商家信息，并判断是否允许发布新商品
        System.out.println(merchantId);
        BusinessBo businessBo = businessService.getBusinessByid(merchantId);
        if (businessBo != null) {
            // 从商家信息中获取管理员设置的发布开始时间和结束时间
            String publishStartTime = businessBo.getStarttime();
            String publishEndTime = businessBo.getEndtime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startTime = LocalDateTime.parse(publishStartTime, formatter);
            LocalDateTime endTime = LocalDateTime.parse(publishEndTime, formatter);

            // 判断当前时间是否在管理员设置的发布时间段内
            LocalDateTime currentTime = LocalDateTime.now();
            if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                return false; // 在该时间段内，不允许发布新商品
            }
        }
        return true; // 允许发布新商品
    }
    @GetMapping("/getCarouselData")
    public ResultInfo getCarouselData(){
        log.info("成功运行！");
        List<CarouselBo> CarouselBoList = carouselService.getGoodshaveCarousel();
        log.info("成功运行！weweeqewqeqw");
        return ResultInfo.success(CarouselBoList);
    }
}

