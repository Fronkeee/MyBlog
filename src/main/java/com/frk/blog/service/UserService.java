package com.frk.blog.service;

import com.frk.blog.po.User;
import org.springframework.stereotype.Service;


public interface UserService {

    User checkUser(String username,String password);
}
