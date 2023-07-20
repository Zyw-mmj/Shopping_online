package com.example.webstore.service;

import com.example.webstore.domain.BusinessBo;
import com.example.webstore.domain.GoodsBo;

import java.util.List;

public interface IBusinessService {
    public Integer createBusiness(BusinessBo businessBo);
    public boolean login(String account,String password);
    boolean getAccounts(String account);
    public List<BusinessBo> getTheBusiness(String account);
    public List<BusinessBo> getBusinesses();
    public BusinessBo getBusinessByid(Long id);
    public BusinessBo getBusinessByAccount(String account);
    public void updateBusiness(BusinessBo businessBo);
    public void updateBusinessById(BusinessBo businessBo);
    public List<BusinessBo> getMerchants();
    public void updateMarchent(BusinessBo businessBo);
    public void updateStartAndEndToAll(BusinessBo businessBo);
    public BusinessBo getBusinessByBusinessId(Long id);
    boolean  finishOrder(double amount, double wallet, double fee, String account);
    boolean refundMoney(double amount, double wallet,double fee, String account);
}
