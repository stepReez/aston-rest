package org.aston.task.service.impl;

import org.aston.task.model.TagEntity;
import org.aston.task.repository.TagRepository;
import org.aston.task.repository.impl.TagEntityRepositoryImpl;
import org.aston.task.service.TagService;

import java.util.List;

public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    public TagServiceImpl() {
        tagRepository = new TagEntityRepositoryImpl();
    }

    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void createTag(TagEntity tag) {
        tagRepository.addTag(tag.getName());
    }

    @Override
    public void removeTag(int id) {
        tagRepository.check(id);
        tagRepository.removeTag(id);
    }

    @Override
    public List<TagEntity> getAllTags() {
        return tagRepository.getAllTags();
    }
}
