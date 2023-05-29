package com.frk.blog.web.admin;

import com.frk.blog.dao.UserRepository;
import com.frk.blog.po.User;
import com.frk.blog.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){

        return "admin/login";
    }

    @PostMapping("/login")
    public String Login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){

        User user = userService.checkUser(username,password);
        if(user != null){
            user.setPassword(null);//把密码删除因为在页面中传入密码不安全
            session.setAttribute("user",user);//把user值传递给session
            return "admin/index";
        }else{

            attributes.addFlashAttribute("message","用户名或密码错误");//提示

            return "redirect:/admin";//请求重定向
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){

        session.removeAttribute("user");

        return "redirect:/admin";
    }
}
