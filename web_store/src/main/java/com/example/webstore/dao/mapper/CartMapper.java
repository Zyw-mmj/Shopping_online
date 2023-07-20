package com.example.webstore.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.webstore.dao.model.Cart;
import com.example.webstore.domain.CartBo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author William Lee
 */
public interface CartMapper extends BaseMapper<Cart> {

//    SELECT userslist.goodsname, shopping_cart.quantity, image.path
//    FROM userslist
//    JOIN shopping_cart ON userslist.id = shopping_cart.id
//    JOIN image ON userslist.id = image.id
//    WHERE userslist.id = #{userId}

    @Select("   SELECT shoplist.goodsname, shoplist.picture ,shopping_cart.*" +
            "    FROM userslist" +
            "    JOIN shopping_cart ON userslist.id = shopping_cart.userId" +
            "    JOIN shoplist ON shoplist.id = shopping_cart.productId" +
            "    WHERE userslist.id = #{userId}")
    List<Cart> selectListByUserId(@Param("userId") Long userId);

//    @Select("SELECT * FROM shopping_cart WHERE userId=#{userId}")
//    List<Cart> selectListByUserId(@Param("userId") Long userId);
    @Delete("DELETE FROM shopping_cart WHERE userId=#{userId}")
    int deleteByUserId(@Param("userId") Long userId);


    //    @Delete("DELETE FROM shopping_cart WHERE userId=#{userId} AND id IN <foreach collection='cartId' item='cartId' open='(' separator=',' close=')'>#{cartId}</foreach>")
    //    int deleteUserCart(@Param("userId") Long userId, @Param("cartId") List<Long> cartId);

    @Delete("DELETE FROM shopping_cart WHERE id=#{cartId}")
    int deleteUserCart(@Param("cartId") Long cartId);

    @Update("UPDATE shopping_cart SET quantity=#{quantity} WHERE userId=#{userId} AND id=#{cartId}")
    int updateCartItemQuantity(@Param("userId") Long userId, @Param("cartId") Long cartId, @Param("quantity") Integer quantity);

}
