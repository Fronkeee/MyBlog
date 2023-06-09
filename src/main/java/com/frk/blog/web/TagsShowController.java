package com.frk.blog.web;

import com.frk.blog.po.Tag;

import com.frk.blog.service.Blogservice;
import com.frk.blog.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class TagsShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private Blogservice blogservice;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTagTop(10000);
        if(id == -1){
            id=tags.get(0).getId();

        }

        model.addAttribute("tags",tags);
        model.addAttribute("page",blogservice.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        return"tags";
    }
}
