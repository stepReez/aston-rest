package org.aston.task.service.impl;

import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.TagEntity;
import org.aston.task.repository.TagEntityRepository;
import org.aston.task.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagEntityRepository tagEntityRepository;

    @Autowired
    public TagServiceImpl(TagEntityRepository tagEntityRepository) {
        this.tagEntityRepository = tagEntityRepository;
    }

    @Override
    public TagEntity createTag(TagEntity tag) {
        return tagEntityRepository.save(tag);
    }

    @Override
    public TagEntity findTagById(int id) {
        return tagEntityRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void removeTag(int id) {
        tagEntityRepository.deleteById(id);
    }

    @Override
    public List<TagEntity> getAllTags() {
        return tagEntityRepository.findAll();
    }
}
