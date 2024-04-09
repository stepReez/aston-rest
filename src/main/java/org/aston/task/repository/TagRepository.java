package org.aston.task.repository;

import org.aston.task.model.TagEntity;

import java.util.List;

public interface TagRepository {

    void addTag(String name);

    void removeTag(int id);

    List<TagEntity> getAllTags();

    void check(int id);
}
