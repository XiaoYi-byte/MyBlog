package com.example.backend.controller.admin;

import com.example.backend.entity.Blog;
import com.example.backend.entity.User;
import com.example.backend.service.BlogService;
import com.example.backend.service.TagsService;
import com.example.backend.service.TypesService;
import com.example.backend.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private final BlogService blogService;
    private final TypesService typesService;
    private final TagsService tagsService;

    private BlogQuery blogQuery = new BlogQuery();

    public BlogController(BlogService blogService, TypesService typesService, TagsService tagsService) {
        this.blogService = blogService;
        this.typesService = typesService;
        this.tagsService = tagsService;
    }

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("types", typesService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        BeanUtils.copyProperties(blog,blogQuery);
        return "admin/blogs";
    }

    @GetMapping("/blogs/search")
    public String searchBlog(@PageableDefault(sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("types", typesService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        BeanUtils.copyProperties(blog,blogQuery);
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
    public String submit(@Valid Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typesService.getType(blog.getType().getId()));
        blog.setTags(tagsService.listTag(blog.getTagIds()));
        Blog b = blogService.saveBlog(blog);
        if(b != null){
            attributes.addFlashAttribute("message","发布成功");
        } else {
            attributes.addFlashAttribute("message","发布失败");
        }
        return "redirect:/admin/blogs";
    }
}
