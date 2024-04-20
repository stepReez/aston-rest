package org.aston.task.repository;

import org.aston.task.model.TagEntity;

import java.util.List;
import java.util.UUID;

public interface TagRepository {

    void addTag(String name);

    void removeTag(int id);

    List<TagEntity> getAllTags();

    void check(int id);

    List<TagEntity> getTagsByRecord(UUID id);
}
