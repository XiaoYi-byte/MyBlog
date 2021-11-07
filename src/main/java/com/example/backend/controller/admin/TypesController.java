package com.example.backend.controller.admin;

import com.example.backend.entity.Type;
import com.example.backend.service.TypesService;
import org.springframework.data.domain.Page;
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

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypesController {

    private final TypesService typesService;

    public TypesController(TypesService typesService) {
        this.typesService = typesService;
    }

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        Page<Type> types = typesService.listType(pageable);
        model.addAttribute("page", types);
        return "admin/types";
    }

    @GetMapping("/types/addType")
    public String addType(Model model) {
        model.addAttribute("type", new Type());
        return "admin/type-post";
    }

    @GetMapping("/types/{id}/edit")
    public String editType(@PathVariable Long id, Model model) {
        model.addAttribute("type", typesService.getType(id));
        return "admin/type-post";
    }

    @PostMapping("/types")
    public String saveTypes(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        Type typeByName = typesService.getTypeByName(type.getName());
        if (typeByName != null) {
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/type-post";
        }
        Type t = typesService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("message", "添加失败");
        } else {
            attributes.addFlashAttribute("message", "添加成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String submitEditingType(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Type typeByName = typesService.getTypeByName(type.getName());
        if (typeByName != null) {
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/type-post";
        }
        Type t = typesService.updateType(id, type);
        if (t == null) {
            attributes.addFlashAttribute("message", "编辑失败");
        } else {
            attributes.addFlashAttribute("message", "编辑成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes){
        typesService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
