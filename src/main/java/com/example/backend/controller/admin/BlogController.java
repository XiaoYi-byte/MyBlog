package com.example.backend.controller.admin;

import com.example.backend.entity.Blog;
import com.example.backend.service.BlogService;
import com.example.backend.service.TagsService;
import com.example.backend.service.TypesService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private final BlogService blogService;
    private final TypesService typesService;
    private final TagsService tagsService;

    public BlogController(BlogService blogService, TypesService typesService, TagsService tagsService) {
        this.blogService = blogService;
        this.typesService = typesService;
        this.tagsService = tagsService;
    }

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Blog blog, Model model) {
        model.addAttribute("types", typesService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Blog blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/addBlog")
    public String addBlog(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("tags",tagsService.listTag());
        model.addAttribute("types", typesService.listType());
        return "admin/blog-post";
    }

    @PostMapping("/blogs")
    public String submit(@Valid Blog blog, BindingResult bindingResult, RedirectAttributes attributes){
        if(bindingResult.hasErrors()){
            return "admin/blog-post";
        }
        Blog b = blogService.saveBlog(blog);
        if(b != null){
            attributes.addFlashAttribute("message","发布失败");
        } else {
            attributes.addFlashAttribute("message","发布成功");
        }
        return "redirect:/admin/blogs";
    }
}
