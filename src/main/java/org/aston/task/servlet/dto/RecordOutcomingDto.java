package org.aston.task.servlet.dto;

import java.util.List;

public class RecordOutcomingDto {
    private String id;

    private String title;

    private String text;

    private List<String> likes;

    private String authorId;

    public RecordOutcomingDto() {

    }

    public RecordOutcomingDto(String id, String title, String text, List<String> likes, String authorId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.likes = likes;
        this.authorId = authorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
