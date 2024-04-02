package org.aston.task.servlet.dto;

import java.util.List;

public class UserOutcomingDto {

    private String id;

    private String userName;

    private List<String> recordsId;

    private List<String> likes;

    public UserOutcomingDto() {

    }

    public UserOutcomingDto(String id, String userName, List<String> recordsId, List<String> likes) {
        this.id = id;
        this.userName = userName;
        this.recordsId = recordsId;
        this.likes = likes;
    }

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

    public List<String> getRecordsId() {
        return recordsId;
    }

    public void setRecordsId(List<String> recordsId) {
        this.recordsId = recordsId;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }
}
