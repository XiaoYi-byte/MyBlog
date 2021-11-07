package com.example.backend.dao;

import com.example.backend.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypesRepository extends JpaRepository<Type,Long> {

    Type findTypeByName(String name);
}
