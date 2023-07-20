package com.example.webstore.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webstore.dao.mapper.UserInfoMapper;
import com.example.webstore.dao.model.UserInfo;
import org.springframework.stereotype.Repository;


@Repository
public class UserInfoRepository extends ServiceImpl<UserInfoMapper, UserInfo> {

}
