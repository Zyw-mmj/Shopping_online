package com.example.webstore.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webstore.dao.mapper.CartMapper;
import com.example.webstore.dao.model.Cart;
import com.example.webstore.domain.CartBo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartRepository extends ServiceImpl<CartMapper, Cart> {
    public List<Cart> list() {
        return baseMapper.selectList(null);
    }

    public Cart findById(Long id) {
        return baseMapper.selectById(id);
    }

    public int insert(Cart cart) {
        return baseMapper.insert(cart);
    }

    public int update(Cart cart) {
        return baseMapper.updateById(cart);
    }

    public int delete(Long id) {
        return baseMapper.deleteById(id);
    }

    public int deleteByUserId(Long userId) {
        return baseMapper.deleteByUserId(userId);
    }

    public int deleteUserCart(Long cartId) {
        return baseMapper.deleteUserCart(cartId);
    }

    public List<Cart> findByUserId(Long userId) {
        return baseMapper.selectListByUserId(userId);
    }


    public int updateCart(Long userId, Long cartId,Integer quantity) {
        return  baseMapper.updateCartItemQuantity(userId,cartId,quantity);
    }

}
