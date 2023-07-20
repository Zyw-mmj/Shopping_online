package com.example.webstore.controller;

import com.example.webstore.Util.PasswordEncryption;
import com.example.webstore.domain.ResultInfo;
import com.example.webstore.domain.UserBo;
import com.example.webstore.domain.UserInfoBo;
import com.example.webstore.service.IUserInfoService;
import com.example.webstore.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Info")
@Slf4j
public class InfoController {
    @Autowired
    private IUserInfoService iUserInfoService;


    @Autowired
    private IUserService userService;


    @GetMapping("/getUserInfo")
    public ResultInfo getUserInfo(@RequestParam("account") String account) {  // 查
        List<UserInfoBo> userInfoBos = iUserInfoService.getInfo(account);
        return ResultInfo.success(userInfoBos);
    }
    @GetMapping("/getUserImage")
    public void getUserImage(@RequestParam("account") String account, HttpServletResponse response) throws IOException {
        List<UserInfoBo> userInfoBos = iUserInfoService.getInfo(account);
//        String path=this.imageService.getImagePath(Long.valueOf(userInfoBos.get(0).getTouxiang()));
        String path=userInfoBos.get(0).getTouxiang();
        log.info(path);
        FileInputStream inputStream = new FileInputStream(path);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();
        response.setContentType("image/jpeg"); // 设置响应头
        response.getOutputStream().write(bytes); // 将图片数据写入响应输出流
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }


    @PostMapping("/updateUserInfo")
    public ResultInfo updateUserInfo(@RequestBody Map map) {  // 查
        String account= (String)map.get("account");
        String nickname= (String) map.get("nickname");
        String introduction= (String) map.get("introduction");
        String wechat= (String) map.get("wechat");
        String goodsaddress= (String) map.get("goodsaddress");
        Boolean s=this.iUserInfoService.updateUserInfo(account,nickname,introduction,wechat,goodsaddress);
        return ResultInfo.success(s);
    }

    @PostMapping("/updatePassword")
    public ResultInfo updatePassword(@RequestBody Map map) {  // 查
        String account= (String)map.get("account");
        String oldPassword= (String) map.get("oldPassword");
        String newPassword= (String) map.get("newPassword");
        UserBo user = this.userService.getTheUser(account).get(0);
        String real=user.getPassword();
        String salt=real.substring(Math.max(real.length() - 32, 0));
        String thePassword= PasswordEncryption.getMD5Password(oldPassword,salt);
        String thePassword2=PasswordEncryption.getMD5Password(newPassword);
        if (real.equals(thePassword)){
            boolean t=this.iUserInfoService.updatePassword(account,thePassword2);
            return ResultInfo.success(t);
        }else {
            return ResultInfo.error("oldPasswordError");
        }
//        Boolean s=this.iUserInfoService.updateUserInfo(account,nickname,introduction,wechat,goodsaddress);
//        return ResultInfo.success(s);
    }

}
