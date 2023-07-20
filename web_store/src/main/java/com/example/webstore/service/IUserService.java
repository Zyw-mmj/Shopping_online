package com.example.webstore.service;

//import com.example.webstore.domain.User;
import com.example.webstore.domain.ResultInfo;
import com.example.webstore.domain.UserBo;

import java.util.List;

public interface IUserService {
    public List<UserBo> getUsers();
    public Integer createUser(UserBo user);
    public boolean delete(Long userId);
    public boolean update(Long userId,String password);
    public boolean login(String account,String password);
    boolean getAccounts(String account);
    public List<UserBo> getTheUser(String account);
    public UserBo getUserByid(Long id);
    public List<UserBo> getUserDetail();
    public void updateUser(UserBo userbo);
    public void updateUserById(UserBo userbo);
    public List<Integer> getTotalRating(Long order_id);
    Boolean updateUserRating(Long order_id, int rating);
    String getUsername(Long order_id);
}
