package com.frk.blog.web;


import com.frk.blog.exception.MyException;
import com.frk.blog.service.Blogservice;
import com.frk.blog.service.TagService;
import com.frk.blog.service.TypeService;
import com.frk.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @Autowired
    private Blogservice blogservice;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         Model model){//在页面请求拦中接收参数值

        model.addAttribute("page",blogservice.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(5));
        model.addAttribute("tags",tagService.listTagTop(5));


        return "index";
    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
model.addAttribute("blog",blogservice.getAndConvert(id));
        return"blog";
    }
}
