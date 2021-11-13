package com.example.backend.controller;

import com.example.backend.service.BlogService;
import com.example.backend.service.TagsService;
import com.example.backend.service.TypesService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {
    private final BlogService blogService;
    private final TypesService typesService;
    private final TagsService tagsService;

    public IndexController(BlogService blogService, TypesService typesService, TagsService tagsService) {
        this.blogService = blogService;
        this.typesService = typesService;
        this.tagsService = tagsService;
    }

    @GetMapping("/")
    public String index(@PageableDefault(sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typesService.listTypeTop(6));
        model.addAttribute("tags", tagsService.listTagTop(10));
        model.addAttribute("topBlogs", blogService.listBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable String id) {
        return "blog";
    }

}
