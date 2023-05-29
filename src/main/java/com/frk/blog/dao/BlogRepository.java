package com.frk.blog.dao;

import com.frk.blog.po.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long> , JpaSpecificationExecutor<Blog> {
    @Transactional
    @Modifying
    @Query("update Blog b set b.views=b.views+1 where b.id=?1")
    int updateViews(Long id);
    @Query("select function('date_format',b.creatTime,'%Y') as year from Blog b group by function('date_format',b.creatTime,'%Y') order by year desc ")
    List<String> findGroupYear();
    @Query("select b from Blog b where function('date_format',b.creatTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}
