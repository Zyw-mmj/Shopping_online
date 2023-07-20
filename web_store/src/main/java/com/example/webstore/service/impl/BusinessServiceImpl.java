package com.example.webstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.webstore.dao.BusinessRepository;
import com.example.webstore.dao.mapper.BusinessMapper;
import com.example.webstore.dao.mapper.GoodsMapper;
import com.example.webstore.dao.mapper.UserMapper;
import com.example.webstore.dao.model.Business;
import com.example.webstore.dao.model.Goods;
import com.example.webstore.dao.model.User;
import com.example.webstore.domain.BusinessBo;
import com.example.webstore.domain.GoodsBo;
import com.example.webstore.domain.UserBo;
import com.example.webstore.service.IBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class BusinessServiceImpl implements IBusinessService {
    @Autowired
    BusinessRepository businessRepository;
    @Autowired
    private BusinessMapper businessMapper;
    @Override
    public Integer createBusiness(BusinessBo businessBo) {
        Business business=new Business();
        business.setId(businessBo.getId());
        business.setName(businessBo.getName());
        business.setAccount(businessBo.getAccount());
        business.setPassword(businessBo.getPassword());
        business.setPhonenumber(businessBo.getPhonenumber());
        business.setEmail(businessBo.getEmail());
        business.setShoptype(businessBo.getShoptype());
        business.setStatus(businessBo.getStatus());
        this.businessRepository.save(business);

        return Math.toIntExact(business.getId());
    }

    @Override
    public boolean login(String account, String password) {
        return false;
    }

    @Override
    public boolean getAccounts(String account) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("account",account);
        List<Business> list=this.businessRepository.listByMap(map);
        System.out.println(list.size());
        return  list.size()==0?true:false;
    }

    @Override
    public List<BusinessBo> getTheBusiness(String account) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("account",account);
        List<Business> list=this.businessRepository.listByMap(map);
        List<BusinessBo> theList=new ArrayList<>();
        list.stream().forEach(business ->{
                    BusinessBo businessBo1=new BusinessBo(business.getId(),business.getAccount(),business.getPassword(),
                            business.getGrade(),business.getWallet(),business.getStatus());
                    theList.add(businessBo1);
                }
        );
        return theList;
    }

    @Override
    public List<BusinessBo> getBusinesses() {
        List<Business> businesses = this.businessRepository.list();

        List<BusinessBo> businessBosList=new ArrayList<>();
        businesses.stream().forEach(business ->{
            BusinessBo businessBo1=new BusinessBo(business.getId(),business.getName(),business.getAccount(),business.getEmail(),business.getShoptype(),business.getStatus());
            businessBosList.add(businessBo1);
            }
        );
        return businessBosList;
    }

    @Override
    public BusinessBo getBusinessByid(Long id) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("id",id);
        List<Business> list = this.businessRepository.listByMap(map);
        List<BusinessBo> list1=new ArrayList<>();
        list.stream().forEach(business -> {
            BusinessBo business1 = new BusinessBo(business.getId(),business.getName(),
                    business.getAccount(),business.getEmail(),business.getShoptype(),business.getStatus(),
                    business.getStarttime(),business.getEndtime());
                    list1.add(business1);
                }
        );
        return list1.get(0);
    }

//    @Override
//    public BusinessBo getBusinessByidhaveGrade(Long id) {
//        HashMap<String,Object> map=new HashMap<>();
//        map.put("id",id);
//        List<Business> list = this.businessRepository.listByMap(map);
//        List<BusinessBo> list1=new ArrayList<>();
//        list.stream().forEach(business -> {
//                    BusinessBo business1 = new BusinessBo(business.getId(),business.getName(),business.getAccount(),business.getGrade(),business.getQuantity(),business.getAmount(),business.getFee());
//                    list1.add(business1);
//                }
//        );
//        return list1.get(0);
//    }

    @Override
    public BusinessBo getBusinessByAccount(String account) {
        Business business = businessMapper.selectByAccount(account);
        BusinessBo businessBo = new BusinessBo();
        businessBo.setId(business.getId());
        businessBo.setAccount(business.getAccount());
        businessBo.setStatus(business.getStatus());
        int grade =Integer.parseInt(business.getGrade().toString());
        businessBo.setGrade(grade);
        return businessBo;
    }

    @Override
    public void updateBusiness(BusinessBo businessBo) {
        Business business = businessRepository.getById(businessBo.getId());
        business.setStatus(businessBo.getStatus());
        businessMapper.updateById(business);
    }

    @Override
    public void updateBusinessById(BusinessBo businessBo) {
        Business business = businessRepository.getById(businessBo.getId());
        business.setName(businessBo.getName());
        business.setEmail(businessBo.getEmail());
        business.setPhonenumber(businessBo.getPhonenumber());
        businessMapper.updateById(business);
    }

    @Override
    public List<BusinessBo> getMerchants() {
        List<Business> businesses = this.businessRepository.list();
        List<BusinessBo> businessBosList=new ArrayList<>();

        businesses.stream().forEach(business ->{
                    BusinessBo businessBo1=new BusinessBo(business.getId(),business.getAccount(),business.getName(),
                            business.getAmount(),business.getQuantity(),
                            business.getGrade(),business.getFee(),business.getStatus());
                    businessBosList.add(businessBo1);
                }
        );
        return businessBosList;
    }

    @Override
    public void updateMarchent(BusinessBo businessBo) {
        Business business = businessRepository.getById(businessBo.getId());
        business.setGrade(businessBo.getGrade());
        businessMapper.updateById(business);
    }

//    @Override
//    public void updateMarchentFee(BusinessBo businessBo) {
//        Business business = businessRepository.getById(businessBo.getId());
//        business.setFee(businessBo.getFee());
//        businessMapper.updateById(business);
//    }

    @Override
    public void updateStartAndEndToAll(BusinessBo businessBo) {
        String starttime = businessBo.getStarttime();
        String endtime = businessBo.getEndtime();
        businessMapper.updateStartAndEndToAll(starttime,endtime);
    }

    @Override
    public BusinessBo getBusinessByBusinessId(Long id) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("id",id);
        List<Business> list = this.businessRepository.listByMap(map);
        List<BusinessBo> list1=new ArrayList<>();
        list.stream().forEach(business -> {
                    BusinessBo business1 = new BusinessBo(business.getId(),business.getName(),
                            business.getAccount(),business.getEmail(),business.getShoptype(),
                            business.getStatus(),business.getGrade(),business.getWallet(),
                            business.getPhonenumber(),business.getAmount(),business.getQuantity());
                    list1.add(business1);
                }
        );
        return list1.get(0);
    }

    @Override
    public boolean finishOrder(double amount, double wallet, double fee, String account) {
        int n=this.businessRepository.finishOrder(amount,wallet,fee,account);
        if (n>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean refundMoney(double amount, double wallet, double fee, String account) {
        int n=this.businessRepository.refundMoney(amount,wallet,fee,account);
        if (n>0){
            return true;
        }else {
            return false;
        }
    }
}
