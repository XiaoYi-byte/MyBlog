package com.example.backend.service.impl;

import com.example.backend.dao.BlogRepository;
import com.example.backend.entity.Blog;
import com.example.backend.entity.Type;
import com.example.backend.service.BlogService;
import com.example.backend.util.MyBeanUtils;
import com.example.backend.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getById(id);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        return blogRepository.save(blog);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll((Specification<Blog>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (blog.getTitle() != null && !("".equals(blog.getTitle()))) {
                predicates.add(criteriaBuilder.like(root.get("title"), blog.getTitle()));
            }
            if (blog.getTypeId()!= null) {
                predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
            }
            if (blog.getRecommend() != null) {
                predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.getRecommend()));
            }

            query.where(predicates.toArray(new Predicate[0]));
            return null;
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listBlogTop(Integer size) {
        Pageable pageable = PageRequest.of(0,size, Sort.by(Sort.Direction.DESC,"updateTime"));
        return blogRepository.findTop(pageable);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getById(id);
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
