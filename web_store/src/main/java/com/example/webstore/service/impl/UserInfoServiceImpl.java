package com.example.webstore.service.impl;

import com.example.webstore.dao.model.UserInfo;
import com.example.webstore.dao.UserInfoRepository;
import com.example.webstore.domain.UserInfoBo;
import com.example.webstore.service.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public List<UserInfoBo> getInfo(String account) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("account",account);
        List<UserInfo> list=this.userInfoRepository.listByMap(map);
        List<UserInfoBo> list1=new ArrayList<>();
        list.stream().forEach(user ->{
                    UserInfoBo user1=new UserInfoBo(user.getId(),user.getAccount(),user.getPassword(),user.getTouxiang(),user.getIntroduction(),
                            user.getWechat(),user.getGoodsaddress(),user.getNickname());
                list1.add(user1);
                }
        );
        return list1;
    }

    @Override
    public List<UserInfoBo> getAllInfo() {
        return null;
    }

    @Override
    public Boolean updateUserInfo(String account, String nickname, String introduction, String wechat,  String goodsaddress) {
//        return null;
        HashMap<String,Object> map=new HashMap<>();
        map.put("account",account);
        List<UserInfo> list=this.userInfoRepository.listByMap(map);
        UserInfo user=list.get(0);
        if (user!=null){
            user.setNickname(nickname);
            user.setIntroduction(introduction);
            user.setWechat(wechat);
            user.setGoodsaddress(goodsaddress);
            this.userInfoRepository.updateById(user);
            return true;
        } else {
            return  false;
        }
    }

    @Override
    public Boolean updateTouxiang(String account, String touxiang) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("account",account);
        List<UserInfo> list=this.userInfoRepository.listByMap(map);
        UserInfo user=list.get(0);
        if (user!=null){
            user.setTouxiang(touxiang);
            this.userInfoRepository.updateById(user);
            return true;
        } else {
            return  false;
        }
    }

    @Override
    public Boolean updatePassword(String account,String newPassword) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("account",account);
        List<UserInfo> list=this.userInfoRepository.listByMap(map);
        UserInfo user=list.get(0);
        if (user!=null){
            user.setPassword(newPassword);
            this.userInfoRepository.updateById(user);
            return true;
        } else {
            return  false;
        }
    }


}
