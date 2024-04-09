package org.aston.task.service;

import org.aston.task.model.TagEntity;

import java.util.List;

public interface TagService {

    void createTag(TagEntity tag);

    void removeTag(int id);

    List<TagEntity> getAllTags();
}
