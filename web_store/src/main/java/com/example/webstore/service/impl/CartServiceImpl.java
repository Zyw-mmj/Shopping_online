package com.example.webstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.webstore.dao.CartRepository;
import com.example.webstore.dao.GoodsRepository;
import com.example.webstore.dao.UserRepository;
import com.example.webstore.dao.mapper.CartMapper;
import com.example.webstore.dao.model.*;
import com.example.webstore.domain.CartBo;
import com.example.webstore.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

/**
 * @author William Lee
 */
@Service
@Slf4j
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private GoodsRepository goodsRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public List<CartBo> getCart() {
        List<Cart> cartList = this.cartRepo.list();
        List<CartBo> cartBoList=new ArrayList<>();

        cartList.forEach(good ->{
//            log.info(good.getUser_id().toString());
            CartBo cartBo = new CartBo(good.getId(), good.getUserId(),good.getProductId(),good.getQuantity(),good.getPrice(),good.getGoodsname(),good.getPath(),good.getCreatedAt(),good.getUpdatedAt());
            cartBoList.add(cartBo);
        });
        return cartBoList;
    }

    @Override
    public List<CartBo> getUserCart(Long userId){
        List<Cart> cartList = cartRepo.findByUserId(userId);
        List<CartBo> cartBoList = new ArrayList<>();
        cartList.forEach(good ->{
            CartBo cartBo = new CartBo(good.getId(), good.getUserId(),good.getProductId(),good.getQuantity(),good.getPrice(),good.getGoodsname(),good.getPath(),good.getCreatedAt(),good.getUpdatedAt());
            cartBoList.add(cartBo);
        });
        return cartBoList;
    }

    @Override
    public int deleteUserCart(Long cartId) {
        // Check if the user exists
//        User user = userRepo.getById(userId);
//        if (user == null) {
//            log.error("User not found with id: {}", userId);
//            return 0;
//        }

        // Delete the carts for the given user and cart IDs
        int count = cartRepo.deleteUserCart(cartId);
        log.info("Deleted {} cart(s) for user with id: {}", count);

        return count;
    }

    @Override
    public int addCartItem(CartBo cartBo) {
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartBo, cart);
        return cartRepo.insert(cart);
    }
    @Override
    public int updateCartItemQuantity(Long cartId, Integer quantity) {
        // 查询购物车中是否存在该商品
        Cart cart =  cartRepo.getById(cartId);
        if (cart == null) {
            return 0;
        }

        // 更新购物车商品数量
        cart.setQuantity(Long.valueOf(quantity));
        cart.setUpdateTime();
        return cartRepo.update(cart);
    }

    @Override
    public Cart getCartItemByUserIdAndGoodsId(Long userId, Long goodsId) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId).eq("productId", goodsId);
        return cartRepo.getOne(wrapper);
    }


}
