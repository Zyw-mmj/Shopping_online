package com.example.webstore.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webstore.dao.mapper.UserWalletMapper;
import com.example.webstore.dao.model.UserWallet;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author William Lee
 */
@Repository
public class UserWalletRepository extends ServiceImpl<UserWalletMapper, UserWallet> {

    public Optional<UserWallet> findByUserId(Long userId) {
        QueryWrapper<UserWallet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        UserWallet userWallet = getBaseMapper().selectOne(queryWrapper);
        return Optional.ofNullable(userWallet);
    }
}
