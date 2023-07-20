package com.example.webstore.dao;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webstore.dao.mapper.UserMapper;
import com.example.webstore.dao.mapper.ext.UserXMapper;

import com.example.webstore.dao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends ServiceImpl<UserMapper, User> {
//    String getPasswordByAccount(Long Account);
}
