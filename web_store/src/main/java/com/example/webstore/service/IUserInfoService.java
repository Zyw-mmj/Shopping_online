package com.example.webstore.service;

import com.example.webstore.domain.UserInfoBo;
import java.util.List;

public interface IUserInfoService {
    List<UserInfoBo> getInfo(String account);
    List<UserInfoBo> getAllInfo();
    Boolean updateUserInfo(String account,String nickname,String introduction,String wechat,String goodsaddress);
    Boolean updateTouxiang(String account,String touxiang);
    Boolean updatePassword(String account,String newPassword);
}
