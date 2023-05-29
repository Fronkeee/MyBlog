package com.frk.blog.web.admin;

import com.frk.blog.po.Blog;
import com.frk.blog.po.User;
import com.frk.blog.service.Blogservice;
import com.frk.blog.service.TagService;
import com.frk.blog.service.TypeService;
import com.frk.blog.vo.BlogQuery;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT="admin/blogs-input";
    private static final String LIST ="admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private Blogservice blogservice;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){//model是Spring MVC中的视图模型 以便前端可以拿到model中的数据进行数据渲染

        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogservice.listBlog(pageable,blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){//model是Spring MVC中的视图模型 以便前端可以拿到model中的数据进行数据渲染
        model.addAttribute("page",blogservice.listBlog(pageable,blog));
        return"admin/blogs :: blogList";
    }
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());

        return INPUT;
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        Blog blog=blogservice.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);

        return INPUT;
    }


    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
       Blog b ;
       if(blog.getId() == null){
           b = blogservice.saveBlog(blog);

       }else{
           b = blogservice.updateBlog(blog.getId(),blog);
       }
       if(b==null){
           attributes.addFlashAttribute("message","增加成功!");
       }else{
           attributes.addFlashAttribute("message","增加成功!");
       }

        return REDIRECT_LIST;
    }
    @GetMapping("/blogs/{id}/delete")
    public  String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogservice.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功!");
        return REDIRECT_LIST;
    }

}
