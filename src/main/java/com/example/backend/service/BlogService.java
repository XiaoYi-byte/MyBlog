package com.example.backend.service;

import com.example.backend.entity.Blog;
import com.example.backend.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    Blog getBlog(Long id);

    Blog saveBlog(Blog blog);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);
}
