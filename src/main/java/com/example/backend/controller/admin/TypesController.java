package com.example.backend.controller.admin;

import com.example.backend.entity.Type;
import com.example.backend.service.TypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String addType() {
        return "admin/type-post";
    }

    @PostMapping("/types")
    public String saveTypes(Type type, RedirectAttributes attributes) {
        Type t = typesService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/types";
    }
}
