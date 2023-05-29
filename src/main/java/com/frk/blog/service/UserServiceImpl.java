package com.frk.blog.service;

import com.frk.blog.dao.UserRepository;
import com.frk.blog.po.User;
import com.frk.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired//注入
    private UserRepository userRepository;


    @Override
    public User checkUser(String username, String password) {

        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
