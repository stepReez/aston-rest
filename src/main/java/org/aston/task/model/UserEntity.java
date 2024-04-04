package org.aston.task.model;

import java.util.List;
import java.util.UUID;

public class UserEntity {
    private UUID id;

    private String name;

    private List<RecordEntity> records;

    private List<RecordEntity> likes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RecordEntity> getRecords() {
        return records;
    }

    public void setRecords(List<RecordEntity> records) {
        this.records = records;
    }

    public List<RecordEntity> getLikes() {
        return likes;
    }

    public void setLikes(List<RecordEntity> likes) {
        this.likes = likes;
    }
}