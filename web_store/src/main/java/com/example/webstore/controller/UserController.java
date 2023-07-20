package com.example.webstore.controller;

import com.example.webstore.dao.model.User;
import com.example.webstore.domain.ResultInfo;
import com.example.webstore.domain.UserBo;
import com.example.webstore.service.IBusinessfeedbackService;
import com.example.webstore.service.IFeedbackService;
import com.example.webstore.service.IUserService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mysql.cj.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/User")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/a")
    public ResultInfo getCars() {
        log.info("成功运行！");
        return ResultInfo.builder().code(0).message("success")
                .data("hello world").build();
    }

    @GetMapping("/getUser")
    public ResultInfo getUsers() {  // 查
        log.info("成功运行！");
        List<UserBo> userList = userService.getUsers();
        log.info("School Bos:{}", userList);

        return ResultInfo.success(userList);
    }

    @GetMapping("/getUserDetail")
    public ResultInfo getUserDetail() {  // 查
        log.info("成功运行！");
//        log.info("刷新页面");
        List<UserBo> userList = userService.getUserDetail();
        System.out.println(userList.get(0).getName());
        log.info("School Bos:{}", userList);

        return ResultInfo.success(userList);
    }



//    @PostMapping("/b")   // 增
//    public ResultInfo createUser(@RequestBody UserBo userRequest) {
//        log.info("User Request", userRequest);
//        Integer id = this.userService.createUser(userRequest);
//        return ResultInfo.success(id);
//    }

    @DeleteMapping("/b/{Id}")  //删
    public ResultInfo deleteOne(@PathVariable("Id") Integer Id) {
        Boolean status = this.userService.delete(Long.valueOf(Id));
        return ResultInfo.success(status);
    }
    @PostMapping("/b1")   // 改
    public ResultInfo update(@RequestBody UserBo userRequest) {
        log.info("User Request"+" "+userRequest.getName());
//        log.info("User Request"+userRequest.getId());
        log.info("User Request"+userRequest.getId());
        log.info("User Request"+userRequest.getPassword());
        Boolean status = this.userService.update(userRequest.getId(),userRequest.getPassword());
        return ResultInfo.success(status);
    }

    @GetMapping("/login/stayus")
    public ResultInfo login(HttpServletRequest req) {
        log.info("User Request");
        boolean t=this.userService.login(req.getParameter("account"),req.getParameter("password"));
        return ResultInfo.success(t);
    }

    @GetMapping("/getUserRating/{order_id}")
    public ResultInfo getUserRating(@PathVariable Long order_id) {
        log.info("@@@@@@@");
        log.info(String.valueOf(order_id));
        List<Integer> AllRating = userService.getTotalRating(order_id);
        int sum = 0;
        for (Integer integer : AllRating) {
            sum += integer;
        }
        log.info(String.valueOf(sum));
        int rating = 0;
        if(AllRating.size()!=0){
             rating = sum/AllRating.size();
        }
        Boolean t = userService.updateUserRating(order_id,rating);
        String username = userService.getUsername(order_id);
        UserBo userBo = new UserBo();
        userBo.setRating(rating);
        userBo.setName(username);
        return ResultInfo.success(userBo);
    }

}
