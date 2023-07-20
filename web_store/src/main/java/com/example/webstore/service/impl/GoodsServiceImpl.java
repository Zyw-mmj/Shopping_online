package com.example.webstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.webstore.dao.GoodsRepository;
import com.example.webstore.dao.mapper.GoodsMapper;
import com.example.webstore.dao.model.*;

import com.example.webstore.domain.CarouselBo;
import com.example.webstore.domain.GoodsBo;
import com.example.webstore.domain.UserBo;
import com.example.webstore.domain.UserInfoBo;
import com.example.webstore.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private GoodsRepository goodsRepo;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsBo> getGoods() {
        List<Goods> goods = this.goodsRepo.list();
        List<GoodsBo> goodsList=new ArrayList<>();

        goods.stream().forEach(good ->{
            GoodsBo good1 = new GoodsBo(good.getId(),good.getPrice(),good.getCategorie(),good.getSize(),good.getPicture(),good.getDescribes(),good.getGoodsname(),good.getRating(),good.getSales(),good.getStatus());
            if(Objects.equals(good1.getStatus(), "Approved"))
                goodsList.add(good1);
        });
        return goodsList;
    }

    @Override
    public List<GoodsBo> AdmingetGoods() {
        List<Goods> goods = this.goodsRepo.list();
        List<GoodsBo> goodsList=new ArrayList<>();

        goods.stream().forEach(good ->{
            GoodsBo good1 = new GoodsBo(good.getId(),good.getPrice(),good.getCategorie(),good.getSize(),good.getPicture(),good.getDescribes(),good.getGoodsname(),good.getRating(),good.getSales(),good.getStatus(),good.getBusinessAccount());
            goodsList.add(good1);
        });
        return goodsList;
    }

    @Override
    public List<GoodsBo> findByTitleContaining(String keyword) {
//        log.info("23413");
        return goodsMapper.findByTitleContaining(keyword);
    }

    @Override
    public List<GoodsBo> AdminfindByTitleContaining(String keyword) {
        return goodsMapper.AdminfindByTitleContaining(keyword);
    }

    @Override
    public GoodsBo getGoodsByid(String id) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("id",id);
        List<Goods> list = this.goodsRepo.listByMap(map);
        List<GoodsBo> list1=new ArrayList<>();
        list.stream().forEach(good -> {
            GoodsBo good1 = new GoodsBo(good.getId(), good.getPrice(), good.getCategorie(), good.getSize(), good.getPicture(),
                    good.getDescribes(), good.getGoodsname(), good.getRating(), good.getSales(),
                    good.getStatus(),good.getBusinessAccount(),good.getStock(),good.getTradeway());
            list1.add(good1);
            }
        );
        return list1.get(0);
    }

    @Override
    public List<String> getGoodsType() {
        return goodsMapper.getGoodsType();
    }

    @Override
    public Boolean createGoods(GoodsBo goodsBo) {
        Goods goods=new Goods();
        goods.setGoodsname(goodsBo.getGoodsname());
        goods.setCategorie(goodsBo.getCategorie());
        goods.setSize(goodsBo.getSize());
        goods.setDescribes(goodsBo.getDescribes());
        goods.setStock(goodsBo.getStock());
        goods.setPrice(goodsBo.getPrice());
        goods.setTradeway(goodsBo.getTradeway());
        goods.setBusinessAccount(goodsBo.getBusinessAccount());
        goods.setPicture(goodsBo.getPicture());
        Boolean t= this.goodsRepo.save(goods);
        return t;

    }

    @Override
    public Boolean deleteGoods(String id) {
        boolean t=this.goodsRepo.removeById(id);
        return t;
    }

    @Override
    public List<GoodsBo> getGoodsByBusinessId(String businessAccount) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("businessAccount",businessAccount);
        List<Goods> goods=this.goodsRepo.listByMap(map);
        List<GoodsBo> goodsBosList=new ArrayList<>();
        goods.stream().forEach(goods1 ->{
            GoodsBo goodsBo=new GoodsBo(goods1.getId(),goods1.getPrice(),goods1.getCategorie(),goods1.getSize(),goods1.getDescribes(),
                            goods1.getGoodsname(),goods1.getRating(),goods1.getSales(),goods1.getStock(),goods1.getTradeway(),goods1.getStatus());
                goodsBosList.add(goodsBo);
            }
        );
        return goodsBosList;
    }

    @Override
    public void saveGood(GoodsBo good) {
        Goods good1 = goodsRepo.getById(good.getId());
        goodsMapper.updateProductStatus(good1.getId());
    }

    @Override
    public void saveGood1(GoodsBo good) {
        Goods good1 = goodsRepo.getById(good.getId());
        goodsMapper.updateProductStatusUnpass(good1.getId());
    }

    @Override
    public List<GoodsBo> getProductsByMerchantAccount(String merchantAccount) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("businessAccount", merchantAccount);
        List<Goods> goodsList = goodsMapper.selectList(queryWrapper);
        List<GoodsBo> goodsBoList = new ArrayList<>();
        goodsList.stream().forEach(good ->{
                    GoodsBo goodsBo=new GoodsBo(good.getId(),good.getGoodsname(),good.getPrice(),good.getStatus(),good.getBusinessAccount());
                    goodsBoList.add(goodsBo);
                }
        );
        return goodsBoList;
    }
    @Override
    public List<GoodsBo> findOrderGoods(String account) {
        List<BusinessOrder> goods=this.goodsRepo.findOrderGoods(account);
        List<GoodsBo> goodsBosList=new ArrayList<>();
        goods.stream().forEach(goods1 ->{
                    System.out.println(goods1.toString());
                    GoodsBo goodsBo=new GoodsBo(goods1.getId(),goods1.getPrice(),goods1.getCategorie(),goods1.getSize(),goods1.getDescribes(),
                            goods1.getGoodsname(),goods1.getRating(),goods1.getSales(),goods1.getStock(),goods1.getTradeway(),goods1.getGoods_count(),
                            goods1.getStatus(),goods1.getGoods_status(),goods1.getOrder_id());
                    goodsBosList.add(goodsBo);
                }
        );
        return goodsBosList;
    }

    @Override
    public boolean deliverGoods(Long id, int quantity) {
        int a=this.goodsRepo.deliverGoods(id,quantity);
        log.info("a的值为"+a);
        if (a>0){
            return  true;
        }else {
            return false;
        }
    }

    @Override
    public void withdrawAllProducts(String account) {
        List<Goods> goodsList = goodsMapper.getByAccount(account);
        for (Goods goods : goodsList) {
            Long id = goods.getId();
            this.updateProductStatusOff(id);
        }
    }

    @Override
    public void reoverAllProducts(String account) {
        List<Goods> goodsList = goodsMapper.getByAccount(account);
        for (Goods goods : goodsList) {
            Long id = goods.getId();
            this.updateProductStatusOn(id);
        }
    }

    @Override
    public boolean updateProductStatusOff(Long id) {
        int n = goodsRepo.updateProductStatusOff(id);
        if (n>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateProductStatusOn(Long id) {
        int n = goodsRepo.updateProductStatusOn(id);
        if (n>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<GoodsBo> findShippedOrder(String account) {
        List<BusinessOrder> goods=this.goodsRepo.findShippedOrder(account);
        List<GoodsBo> goodsBosList1=new ArrayList<>();
        goods.stream().forEach(goods1 ->{
                    System.out.println(goods1.toString());
                    GoodsBo goodsBo=new GoodsBo(goods1.getId(),goods1.getPrice(),goods1.getCategorie(),goods1.getSize(),goods1.getDescribes(),
                            goods1.getGoodsname(),goods1.getRating(),goods1.getSales(),goods1.getStock(),goods1.getTradeway(),goods1.getGoods_count(),
                            goods1.getStatus(),goods1.getGoods_status(),goods1.getOrder_id(),goods1.getCreate_time(),goods1.getItemtotalprice());
                    goodsBosList1.add(goodsBo);
                }
        );
        return goodsBosList1;
    }

    @Override
    public List<GoodsBo> findRefundOrder(String account) {
        List<BusinessOrder> goods=this.goodsRepo.findRefundOrder(account);
        List<GoodsBo> goodsBosList1=new ArrayList<>();
        goods.stream().forEach(goods1 ->{
                    System.out.println(goods1.toString());
                    GoodsBo goodsBo=new GoodsBo(goods1.getId(),goods1.getPrice(),goods1.getCategorie(),goods1.getSize(),goods1.getDescribes(),
                            goods1.getGoodsname(),goods1.getRating(),goods1.getSales(),goods1.getStock(),goods1.getTradeway(),goods1.getGoods_count(),
                            goods1.getStatus(),goods1.getGoods_status(),goods1.getOrder_id());
                    goodsBosList1.add(goodsBo);
                }
        );
        return goodsBosList1;
    }

    @Override
    public List<GoodsBo> findFinishedOrder(String account) {
        List<BusinessOrder> goods=this.goodsRepo.findFinishedOrder(account);
        List<GoodsBo> goodsBosList1=new ArrayList<>();
        goods.stream().forEach(goods1 ->{
                    System.out.println(goods1.toString());
//                    GoodsBo goodsBo=new GoodsBo(goods1.getId(),goods1.getPrice(),goods1.getCategorie(),goods1.getSize(),goods1.getDescribes(),
//                            goods1.getGoodsname(),goods1.getRating(),goods1.getSales(),goods1.getStock(),goods1.getTradeway(),goods1.getGoods_count(),
//                            goods1.getStatus(),goods1.getGoods_status(),goods1.getOrder_id());
//                    goodsBosList1.add(goodsBo);
                    GoodsBo goodsBo=new GoodsBo(goods1.getId(),goods1.getPrice(),goods1.getCategorie(),goods1.getSize(),goods1.getDescribes(),
                            goods1.getGoodsname(),goods1.getRating(),goods1.getSales(),goods1.getStock(),goods1.getTradeway(),goods1.getGoods_count(),
                            goods1.getStatus(),goods1.getGoods_status(),goods1.getOrder_id(),goods1.getCreate_time(),goods1.getItemtotalprice());
                    goodsBosList1.add(goodsBo);
                }
        );
        return goodsBosList1;
    }

    @Override
    public boolean updateProductStatusPending(Long id) {
        int n = goodsRepo.updateProductStatusPending(id);
        if (n>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public List<GoodsBo> userFindGoing(Long userId) {
        List<UserOrder> goods=this.goodsRepo.userFindGoing(userId);
        List<GoodsBo> goodsBosList1=new ArrayList<>();
        goods.stream().forEach(goods1 ->{
                    System.out.println(goods1.toString());
                    GoodsBo goodsBo=new GoodsBo(goods1.getId(),goods1.getPrice(),goods1.getCategorie(),goods1.getSize(),goods1.getDescribes(),
                            goods1.getGoodsname(),goods1.getRating(),goods1.getSales(),goods1.getStock(),goods1.getTradeway(),goods1.getGoods_count(),
                            goods1.getStatus(),goods1.getGoods_status(),goods1.getOrder_id(),goods1.getBusinessAccount(),goods1.getCreate_time(),goods1.getItemtotalprice());
                    goodsBosList1.add(goodsBo);
                }
        );
        return goodsBosList1;
    }

    @Override
    public List<GoodsBo> userFindPending(Long userId) {
        List<UserOrder> goods=this.goodsRepo.userFindPending(userId);
        List<GoodsBo> goodsBosList1=new ArrayList<>();
        goods.stream().forEach(goods1 ->{
                    System.out.println(goods1.toString());
                    GoodsBo goodsBo=new GoodsBo(goods1.getId(),goods1.getPrice(),goods1.getCategorie(),goods1.getSize(),goods1.getDescribes(),
                            goods1.getGoodsname(),goods1.getRating(),goods1.getSales(),goods1.getStock(),goods1.getTradeway(),goods1.getGoods_count(),
                            goods1.getStatus(),goods1.getGoods_status(),goods1.getOrder_id(),goods1.getBusinessAccount());
                    goodsBosList1.add(goodsBo);
                }
        );
        return goodsBosList1;
    }

    @Override
    public List<GoodsBo> userFindFinished(Long userId) {
        List<UserOrder> goods=this.goodsRepo.userFindFinished(userId);
        List<GoodsBo> goodsBosList1=new ArrayList<>();
        goods.stream().forEach(goods1 ->{
                    System.out.println(goods1.toString());
                    GoodsBo goodsBo=new GoodsBo(goods1.getId(),goods1.getPrice(),goods1.getCategorie(),goods1.getSize(),goods1.getDescribes(),
                            goods1.getGoodsname(),goods1.getRating(),goods1.getSales(),goods1.getStock(),goods1.getTradeway(),goods1.getGoods_count(),
                            goods1.getStatus(),goods1.getGoods_status(),goods1.getOrder_id(),goods1.getBusinessAccount());
                    goodsBosList1.add(goodsBo);
                }
        );
        return goodsBosList1;
    }

    @Override
    public List<GoodsBo> userFindRefund(Long userId) {
        List<UserOrder> goods=this.goodsRepo.userFindRefund(userId);
        List<GoodsBo> goodsBosList1=new ArrayList<>();
        goods.stream().forEach(goods1 ->{
                    System.out.println(goods1.toString());
                    GoodsBo goodsBo=new GoodsBo(goods1.getId(),goods1.getPrice(),goods1.getCategorie(),goods1.getSize(),goods1.getDescribes(),
                            goods1.getGoodsname(),goods1.getRating(),goods1.getSales(),goods1.getStock(),goods1.getTradeway(),goods1.getGoods_count(),
                            goods1.getStatus(),goods1.getGoods_status(),goods1.getOrder_id(),goods1.getBusinessAccount());
                    goodsBosList1.add(goodsBo);
                }
        );
        return goodsBosList1;
    }

    @Override
    public boolean refundGoods(Long id, int quantity) {
        int a=this.goodsRepo.refundGoods(id,quantity);
        log.info("a的值为"+a);
        if (a>0){
            return  true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updategoodsRating(Long id,int rating) {
        this.goodsRepo.updategoodsRating(id,rating);
        return false;
    }
}
