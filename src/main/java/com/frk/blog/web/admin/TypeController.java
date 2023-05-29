package com.frk.blog.web.admin;


import com.frk.blog.po.Type;
import com.frk.blog.service.TypeService;
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
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                            Pageable pageable, Model model) {//存十条数据，按id倒叙排列
        model.addAttribute("page", typeService.listType(pageable));
       //pageable 把前端的数据封装到pageable对象中去
        return"admin/types";
    }
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));

        return "admin/types-input";
    }


    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        Type type1= typeService.getTypeByName(type.getName());//通过分类名查询分类
        if(type1 !=null){//判断相同的名字的分类不为空说明该分类已经存在

            bindingResult.rejectValue("name","nameError","该分类已经存在");//手动添加错误
        }
        if(bindingResult.hasErrors()){//如果校验不通过

            return "admin/types-input";
        }

       Type t = typeService.saveType(type);//把typesinput页面中新增的type保存并且返回，用post携带数据提交
        if(t==null){
            redirectAttributes.addFlashAttribute("message","新增失败!");
            //判断添加的类型为空的话提示信息
        }else{
            //判断添加的类型不为空的话提示信息
            redirectAttributes.addFlashAttribute("message","新增成功!");
        }

        return"redirect:/admin/types";
    }


    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type,BindingResult bindingResult,@PathVariable Long id, RedirectAttributes redirectAttributes){
        Type type1= typeService.getTypeByName(type.getName());//通过分类名查询分类
        if(type1 !=null){//判断相同的名字的分类不为空说明该分类已经存在

            bindingResult.rejectValue("name","nameError","该分类已经存在");//手动添加错误
        }
        if(bindingResult.hasErrors()){//如果校验不通过

            return "admin/types-input";
        }

        Type t = typeService.updateType(id,type);//把typesinput页面中新增的type保存并且返回，用post携带数据提交
        if(t==null){
            redirectAttributes.addFlashAttribute("message","更新失败!");
            //判断添加的类型为空的话提示信息
        }else{
            //判断添加的类型不为空的话提示信息
            redirectAttributes.addFlashAttribute("message","更新成功!");

        }

        return"redirect:/admin/types";
    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){

        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除成功!");
        return "redirect:/admin/types";

    }
}
