package com.example.webstore.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webstore.dao.mapper.GoodsMapper;
import com.example.webstore.dao.model.BusinessOrder;
import com.example.webstore.dao.model.Goods;
import com.example.webstore.dao.model.UserOrder;
import com.example.webstore.domain.CarouselBo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GoodsRepository extends ServiceImpl<GoodsMapper, Goods> {
    public List<BusinessOrder> findOrderGoods(String  account) {
        return baseMapper.findOrderGoods(account);
    }

    public int deliverGoods(Long id,int quantity){
        return  baseMapper.deliverGoods(id,quantity);
    }

    public int updateProductStatusOff(Long id){
        return baseMapper.updateProductStatusOff(id);
    }
    public int updateProductStatusOn(Long id){
        return baseMapper.updateProductStatusOn(id);
    }
    public List<BusinessOrder> findShippedOrder (String account){
        return baseMapper.findShippedOrder(account);
    }
    public List<BusinessOrder> findRefundOrder (String account){
        return baseMapper.findRefundOrder(account);
    }
    public List<BusinessOrder> findFinishedOrder (String account){
        return baseMapper.findFinishedOrder(account);
    }
    public int updateProductStatusPending(Long id){
        return baseMapper.updateProductStatusPending(id);
    }
    public List<UserOrder> userFindGoing (Long userId){
        return  baseMapper.userFindGoing(userId);
    }
    public List<UserOrder> userFindPending( Long userId){
        return baseMapper.userFindPending(userId);
    }
    public List<UserOrder> userFindFinished ( Long userId){
        return baseMapper.userFindFinished(userId);
    }
    public List<UserOrder> userFindRefund ( Long userId){
        return baseMapper.userFindRefund(userId);
    }
    public List<CarouselBo> getGoodshaveCarousel(){
        return baseMapper.getGoodshaveCarousel();
    }
    public int refundGoods(Long id,int quantity){ return baseMapper.refundGoods(id,quantity);}
    public boolean updategoodsRating(Long id,int rating){
        return baseMapper.updategoodsRating(id,rating);
    }
}
