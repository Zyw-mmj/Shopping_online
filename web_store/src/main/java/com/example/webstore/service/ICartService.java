package com.example.webstore.service;

import com.example.webstore.dao.model.Cart;
import com.example.webstore.domain.CartBo;

import java.util.List;

/**
 * @author William Lee
 */
public interface ICartService {
  public List<CartBo> getCart();

  List<CartBo> getUserCart(Long userId);

  int deleteUserCart(Long cartId);

  int addCartItem(CartBo cartBo);

  int updateCartItemQuantity(Long cartId, Integer num);

  // 根据用户ID和商品ID查询购物车内商品
  Cart getCartItemByUserIdAndGoodsId(Long userId, Long goodsId);
//  public void addToCart(int userId, int productId, int quantity, long price);
//  public void deleteFromCart(int userId, int productId);
//  public void updateCart(int userId, int productId, int quantity, long price);
}
