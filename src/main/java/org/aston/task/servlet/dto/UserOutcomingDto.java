package org.aston.task.servlet.dto;

import java.util.List;

public class UserOutcomingDto {

    private String id;

    private String userName;

    private List<Integer> recordsId;

    private List<Integer> likes;

    public UserOutcomingDto(String id, String userName, List<Integer> recordsId, List<Integer> likes) {
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

    public List<Integer> getRecordsId() {
        return recordsId;
    }

    public void setRecordsId(List<Integer> recordsId) {
        this.recordsId = recordsId;
    }

    public List<Integer> getLikes() {
        return likes;
    }

    public void setLikes(List<Integer> likes) {
        this.likes = likes;
    }
}
