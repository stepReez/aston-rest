package org.aston.task.service;

import org.aston.task.model.TagEntity;

import java.util.List;

public interface TagService {

    TagEntity createTag(TagEntity tag);

    TagEntity findTagById(int id);

    void removeTag(int id);

    List<TagEntity> getAllTags();
}
