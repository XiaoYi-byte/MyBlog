package com.example.backend.controller.admin;

import com.example.backend.entity.Tag;
import com.example.backend.service.TagsService;
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

@Controller
@RequestMapping("/admin")
public class TagsController {

    private final TagsService tagsService;

    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Tag> tags = tagsService.listTag(pageable);
        model.addAttribute("page",tags);
        return "admin/tags";
    }

    @GetMapping("/tags/addTag")
    public String addTag(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tag-post";
    }

    @PostMapping("/tags")
    public String saveTag(Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tagByName = tagsService.getTagByName(tag.getName());
        if(tagByName != null){
            result.rejectValue("name","nameError","不能添加重复的标签");
        }
        if(result.hasErrors()){
            return "admin/tag-post";
        }
        Tag t = tagsService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "添加失败");
        } else {
            attributes.addFlashAttribute("message", "添加成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/edit")
    public String editTag(@PathVariable Long id, Model model){
        Tag tag = tagsService.getTag(id);
        model.addAttribute("tag",tag);
        return "admin/tag-post";
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id,RedirectAttributes attributes){
        tagsService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String submitEditingTag(Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tagByName = tagsService.getTagByName(tag.getName());
        if(tagByName != null){
            result.rejectValue("name","nameError","不能添加重复的标签");
        }
        if(result.hasErrors()){
            return "admin/tag-post";
        }
        Tag t = tagsService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "编辑失败");
        } else {
            attributes.addFlashAttribute("message", "编辑成功");
        }
        return "redirect:/admin/tags";
    }
}
