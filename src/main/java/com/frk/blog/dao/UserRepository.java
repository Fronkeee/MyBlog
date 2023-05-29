package com.frk.blog.dao;

import com.frk.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {//dao层操作数据库，long为主键类型

    User findByUsernameAndPassword(String username,String password);//查找数据库中的记录
}
