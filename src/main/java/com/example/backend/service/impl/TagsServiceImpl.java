package com.example.backend.service.impl;

import com.example.backend.dao.TagsRepository;
import com.example.backend.entity.Tag;
import com.example.backend.exception.NotFoundException;
import com.example.backend.service.TagsService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagsServiceImpl implements TagsService {

    private final TagsRepository tagsRepository;

    public TagsServiceImpl(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagsRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagsRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagsRepository.findTagByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagsRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagsRepository.findAll();
    }

    @Transactional
    @Override
    public List<Tag> listTag(String tagIds) {
        return tagsRepository.findAllById(convertToList(tagIds));
    }

    private List<Long> convertToList(String tagIds){
        List<Long> tagIdList = new ArrayList<>();
        if(!"".equals(tagIds) && tagIds != null) {
            String[] tagIdArray = tagIds.split(",");
            for (int i = 0; i < tagIdArray.length; i++) {
                tagIdList.add(Long.parseLong(tagIdArray[i]));
            }
        }
        return tagIdList;
    }

    @Transactional
    @Override
    public Tag updateTag(Tag tag, Long id) {
        Tag t = tagsRepository.findById(id).orElse(null);
        if (t == null)
            throw new NotFoundException("不存在该类型");
        BeanUtils.copyProperties(tag,t);
        return tagsRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagsRepository.deleteById(id);
    }
}
