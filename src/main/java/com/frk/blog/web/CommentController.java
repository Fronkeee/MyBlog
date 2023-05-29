package com.frk.blog.web;

import com.frk.blog.po.Comment;
import com.frk.blog.po.User;
import com.frk.blog.service.Blogservice;
import com.frk.blog.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private Blogservice blogservice;
    @Value("${comment.avatar}")
    private String avatar;
    @GetMapping("/comments/{blogId}")//评论列表
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){//接收评论的信息
        //建立评论和博客的关系
        Long blogId=comment.getBlog().getId();
        comment.setBlog(blogservice.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);

        }else{
           comment.setAvatar(avatar);
        }

        commentService.saveComment(comment);
        return"redirect:/comments/"+blogId;
    }
}
