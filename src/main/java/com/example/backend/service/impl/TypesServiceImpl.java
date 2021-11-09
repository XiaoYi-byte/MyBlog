package com.example.backend.service.impl;

import com.example.backend.dao.TypesRepository;
import com.example.backend.entity.Type;
import com.example.backend.exception.NotFoundException;
import com.example.backend.service.TypesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypesServiceImpl implements TypesService {
    private final TypesRepository typesRepository;

    public TypesServiceImpl(TypesRepository typesRepository) {
        this.typesRepository = typesRepository;
    }

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typesRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typesRepository.findById(id).orElse(null);
    }

    @Override
    public Type getTypeByName(String name) {
        return typesRepository.findTypeByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typesRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public List<Type> listType() {
        return typesRepository.findAll();
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typesRepository.findById(id).orElse(null);
        if (t == null)
            throw new NotFoundException("不存在该类型");
        BeanUtils.copyProperties(type, t);
        return typesRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typesRepository.deleteById(id);
    }
}
