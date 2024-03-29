package org.aston.task.model;

import java.util.List;

public class UserEntity {
    private String id;

    private String userName;

    private List<RecordEntity> records;

    private List<RecordEntity> likes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
