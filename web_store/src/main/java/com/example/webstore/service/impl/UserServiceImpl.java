package com.example.webstore.service.impl;

import com.example.webstore.dao.*;
import com.example.webstore.dao.mapper.UserMapper;
import com.example.webstore.dao.model.Businessfeedback;
import com.example.webstore.domain.UserBo;
import com.example.webstore.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.webstore.dao.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
@Slf4j
public class UserServiceImpl  implements IUserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BusinessfeedbackRepository businessfeedbackRepository;

    @Autowired
    private ShopOrderRepository shopOrderRepository;


    @Override
    public List<UserBo> getUsers() {
        List<User> users = this.userRepo.list();

        List<UserBo> userList=new ArrayList<>();
        users.stream().forEach(user ->{
            UserBo user1=new UserBo(user.getId(),user.getName(),user.getAccount(),user.getPassword());
            userList.add(user1);
            }
        );
        return userList;
    }

    @Override
    public Integer createUser(UserBo user){
        User user1=new User();
        user1.setId(user.getId());
        user1.setName(user.getName());
        user1.setAccount(user.getAccount());
        user1.setPassword(user.getPassword());
        user1.setPhonenumber(user.getPhonenumber());
        user1.setEmail(user.getEmail());
        user1.setCity(user.getCity());
        user1.setGender(user.getGender());
        user1.setBankaccount(user.getBankaccount());
        user1.setShoptype(user.getShoptype());
        user1.setStatus(user.getStatus());
        this.userRepo.save(user1);

        return Math.toIntExact(user1.getId());
    }

    @Override
    public boolean delete(Long userId) {
        if (userRepo.getById(userId)!=null){
            this.userRepo.removeById(userId);
            return true;
        }
        else {
            return  false;
        }


    }

    @Override
    public boolean update(Long userId, String password) {
        User user=userRepo.getById(userId);
        if (user!=null){
            user.setPassword(password);
            this.userRepo.updateById(user);
            return true;
        } else {
            return  false;
        }
    }


    @Override
    public boolean login(String account, String password) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("account",account);
        map.put("password",password);
        List<User> list=this.userRepo.listByMap(map);
        System.out.println(list.size());
        return  list.size()>0?true:false;
    }

    @Override
    public boolean getAccounts(String account) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("account",account);
        List<User> list=this.userRepo.listByMap(map);
        System.out.println(list.size());
        return  list.size()==0?true:false;
    }

    @Override
    public List<UserBo> getTheUser(String account) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("account",account);
        List<User> users=this.userRepo.listByMap(map);
        List<UserBo> userList=new ArrayList<>();
        users.stream().forEach(user ->{
                    UserBo user1=new UserBo(user.getId(),user.getName(),user.getAccount(),user.getPassword(),user.getStatus());
                    userList.add(user1);
                }
        );
        return userList;
    }

    @Override
    public UserBo getUserByid(Long id) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("id",id);
        List<User> list = this.userRepo.listByMap(map);
        List<UserBo> list1=new ArrayList<>();
        list.stream().forEach(user -> {
                    UserBo user1 = new UserBo(user.getId(),user.getName(),
                            user.getAccount(),user.getEmail(),user.getShoptype(),user.getStatus());
                    list1.add(user1);
                }
        );
        return list1.get(0);
    }

    @Override
    public List<UserBo> getUserDetail() {
        List<User> users = this.userRepo.list();

        List<UserBo> userList=new ArrayList<>();
        users.stream().forEach(user ->{
                    UserBo user1=new UserBo(user.getId(),user.getName(),
                            user.getAccount(),user.getEmail(),user.getShoptype(),
                            user.getStatus(),user.getPhonenumber());
                    userList.add(user1);
                }
        );
        return userList;
    }

    @Override
    public void updateUser(UserBo userbo) {
        User user = userRepo.getById(userbo.getId());
        user.setStatus(userbo.getStatus());
        userMapper.updateById(user);
    }

    @Override
    public void updateUserById(UserBo userbo) {
        User user = userRepo.getById(userbo.getId());
        user.setName(userbo.getName());
        user.setEmail(userbo.getEmail());
        user.setPhonenumber(userbo.getPhonenumber());
        userMapper.updateById(user);
    }

    @Override
    public List<Integer> getTotalRating(Long order_id) {
        return businessfeedbackRepository.getTotalRating(order_id);
    }

    @Override
    public Boolean updateUserRating(Long order_id, int rating) {
        return businessfeedbackRepository.updateUserRating(order_id,rating);
    }

    @Override
    public String getUsername(Long order_id) {
        return shopOrderRepository.getUsername(order_id);
    }
}
