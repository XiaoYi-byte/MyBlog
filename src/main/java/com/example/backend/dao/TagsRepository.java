package com.example.backend.dao;

import com.example.backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tag,Long> {

    Tag findTagByName(String name);
}
