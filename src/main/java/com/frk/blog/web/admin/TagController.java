package com.frk.blog.web.admin;

import com.frk.blog.po.Tag;
import com.frk.blog.po.Type;
import com.frk.blog.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                        Pageable pageable, Model model) {//存十条数据，按id倒叙排列
        model.addAttribute("page", tagService.listTag(pageable));
        //pageable 把前端的数据封装到pageable对象中去
        return"admin/tags";
    }
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }


    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));

        return "admin/tags-input";
    }


    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        Tag tag1= tagService.getTagByName(tag.getName());//通过分类名查询分类
        if(tag1 != null){//判断相同的名字的分类不为空说明该标签已经存在

            bindingResult.rejectValue("name","nameError","该分类已经存在");//手动添加错误
        }
        if(bindingResult.hasErrors()){//如果校验不通过

            return "admin/tags-input";
        }

        Tag t = tagService.saveTag(tag);//把typesinput页面中新增的type保存并且返回，用post携带数据提交
        if(t==null){
            redirectAttributes.addFlashAttribute("message","新增失败!");
            //判断添加的类型为空的话提示信息
        }else{
            //判断添加的类型不为空的话提示信息
            redirectAttributes.addFlashAttribute("message","新增成功!");
        }

        return"redirect:/admin/tags";
    }


    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag,BindingResult bindingResult,@PathVariable Long id, RedirectAttributes redirectAttributes){
        Tag tag1= tagService.getTagByName(tag.getName());//通过分类名查询分类
        if(tag1 != null){//判断相同的名字的分类不为空说明该分类已经存在

            bindingResult.rejectValue("name","nameError","该标签已经存在");//手动添加错误
        }
        if(bindingResult.hasErrors()){//如果校验不通过

            return "admin/tags-input";
        }

        Tag t = tagService.updateTag(id,tag);//把typesinput页面中新增的type保存并且返回，用post携带数据提交
        if(t==null){
            redirectAttributes.addFlashAttribute("message","更新失败!");
            //判断添加的类型为空的话提示信息
        }else{
            //判断添加的类型不为空的话提示信息
            redirectAttributes.addFlashAttribute("message","更新成功!");
        }

        return"redirect:/admin/tags";
    }
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){

        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message","删除成功!");
        return "redirect:/admin/tags";

    }



}
