package com.example.webstore.controller;

import com.example.webstore.dao.model.Cart;
import com.example.webstore.domain.CartBo;
import com.example.webstore.domain.ResultInfo;
import com.example.webstore.domain.UserBo;
import com.example.webstore.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author William Lee
 */
@RestController
@RequestMapping("/Cart")
@Slf4j
public class CartController {

    @Autowired
    private ICartService cartService;

//    @GetMapping("/getCart")
//    public ResultInfo getCart() {  // 查
//        log.info("CaRT成功运行！");
//        List<CartBo> cartList = cartService.getCart();
//        log.info("CART Bos:{}",cartList);
//
//        return ResultInfo.success(cartList);
//    }

    @GetMapping("/getUserCart")
    public ResultInfo getCart(@RequestParam Long userId) {  // 查
        log.info("userCart成功运行！");
        List<CartBo> cartList = cartService.getUserCart(userId);
        log.info("CART Bos:{}",cartList);
        return ResultInfo.success(cartList);
    }

    @DeleteMapping("/deleteUserCartItem")
    public ResultInfo deleteUserCartItem(@RequestParam Long cartId, HttpServletRequest request, HttpServletResponse response) {
        log.info("deleteUserCart方法运行，参数：{}", cartId);
//        UserBo userBo = (UserBo) request.getSession().getAttribute("userBo");
//        if (userBo == null) {
//            response.setStatus(401);
//            return ResultInfo.error("请先登录");
//        }
        int count = cartService.deleteUserCart(cartId);
        if (count > 0) {
            return ResultInfo.success("成功删除 " + count + " 个商品");
        } else {
            return ResultInfo.error("删除失败，请重试");
        }
    }

    @PostMapping("/addCartItem")
    public ResultInfo addCartItem(@RequestBody Map map, HttpServletRequest request, HttpServletResponse response) {
        // 判断用户是否登录
        Long theId= Long.valueOf(String.valueOf(map.get("id")));
        Long theProductID=Long.valueOf(String.valueOf(map.get("productId")));
        log.info(theId.toString());
        log.info(theProductID.toString());
        Cart cart = cartService.getCartItemByUserIdAndGoodsId(theId, theProductID);
//        log.info(cart.toString());
        if (cart == null) {
            log.info("1111111111111111111111111");
            Long quantity=Long.valueOf(String.valueOf(map.get("quantity")));
            BigDecimal price=BigDecimal.valueOf(Long.valueOf(String.valueOf(map.get("price"))));
            CartBo cartBo=new CartBo(theId,theProductID,quantity,price);
            int count = cartService.addCartItem(cartBo);
            if (count > 0) {
                return ResultInfo.success("成功添加 " + count + " 个商品到购物车");
            } else {
                return ResultInfo.error("添加失败，请重试");
            }
        }else {
            log.info("22222222222222");
            Long cartId = cart.getId();
            Long quantitys=Long.valueOf(String.valueOf(map.get("quantity")));
            Long quantity =  cart.getQuantity() + quantitys;
            updateCartItemNum(cartId, Math.toIntExact(quantity),request,response);

            return ResultInfo.success("成功添加1个商品到购物车");
        }
    }

    @PutMapping("/updateCartItemNum")
    public ResultInfo updateCartItemNum(@RequestParam Long cartId, @RequestParam Integer quantity, HttpServletRequest request, HttpServletResponse response) {
        // 判断用户是否登录
        log.info("333333333");
        int count = cartService.updateCartItemQuantity(cartId, quantity);
        if (count > 0) {
            return ResultInfo.success("成功修改 " + count + " 个商品的数量");
        } else {
            return ResultInfo.error("修改失败，请重试");
        }
    }

}
