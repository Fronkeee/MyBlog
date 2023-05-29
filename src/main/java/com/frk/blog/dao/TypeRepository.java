package com.frk.blog.dao;

import com.frk.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {//dao层用来实现数据库操作

    Type findByName(String name);
   @Query("select t_type from Type t_type ")
    List<Type> findTop( Pageable pageable);
}

