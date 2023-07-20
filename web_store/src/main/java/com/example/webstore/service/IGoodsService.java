package com.example.webstore.service;

import com.example.webstore.domain.GoodsBo;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IGoodsService {
    public List<GoodsBo> getGoods();
    public List<GoodsBo> AdmingetGoods();
    public List<GoodsBo> findByTitleContaining(@Param("keyword") String keyword);
    public List<GoodsBo> AdminfindByTitleContaining(@Param("keyword") String keyword);
    public GoodsBo getGoodsByid(String id);
    public List<String> getGoodsType();
    Boolean createGoods(GoodsBo goodsBo);
    Boolean deleteGoods(String id);
    List<GoodsBo> getGoodsByBusinessId(String businessAccount);
    public void saveGood(GoodsBo good);
    public void saveGood1(GoodsBo good);
    public List<GoodsBo> getProductsByMerchantAccount(String merchantAccount);
    public List<GoodsBo> findOrderGoods(String account);
    public boolean deliverGoods(Long id,int quantity);
    public boolean updateProductStatusOff(Long id);
    public void withdrawAllProducts(String account);
    public void reoverAllProducts(String account);
    public boolean updateProductStatusOn(Long id);
    public List<GoodsBo> findShippedOrder (String account);
    public List<GoodsBo> findRefundOrder (String account);
    public List<GoodsBo> findFinishedOrder (String account);
    public boolean updateProductStatusPending(Long id);
    public List<GoodsBo> userFindGoing (Long userId);
    public List<GoodsBo> userFindPending (Long userId);
    public List<GoodsBo> userFindFinished (Long userId);
    public List<GoodsBo> userFindRefund (Long userId);
    public boolean refundGoods(Long id,int quantity);
    public boolean updategoodsRating(Long id,int rating);
}
