package com.frk.blog.service;

import com.frk.blog.dao.TypeRepository;
import com.frk.blog.exception.MyException;
import com.frk.blog.po.Type;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{
    @Autowired//注入
    private TypeRepository typeRepository;
    @Transactional//放在事务中
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }
    @Transactional//放在事务中
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }
    @Transactional//放在事务中
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }
    @Transactional//放在事务中
    @Override
    public Type updateType(Long id, Type type) {
        Type t=typeRepository.getOne(id);
        if(t==null){
            throw new MyException("类型不存在");
        }
        BeanUtils.copyProperties(type,t);//把输入的type复制到我们查询到的type

        return typeRepository.save(t);
    }
    @Transactional//放在事务中
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);

    }
    @Transactional//放在事务中
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }
    @Transactional//放在事务中
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort =  JpaSort.unsafe(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size);
        return typeRepository.findTop(pageable);
    }
}
