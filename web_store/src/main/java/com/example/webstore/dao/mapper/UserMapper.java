package com.example.webstore.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.webstore.dao.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
//    public Integer verifyPassword(String username, String password);
}
