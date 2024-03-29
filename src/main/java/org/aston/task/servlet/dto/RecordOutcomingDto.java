package org.aston.task.servlet.dto;

import java.util.List;

public class RecordOutcomingDto {
    private String id;

    private String title;

    private String text;

    private List<Integer> likes;

    private int authorId;

    public RecordOutcomingDto(String id, String title, String text, List<Integer> likes, int authorId) {
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

    public List<Integer> getLikes() {
        return likes;
    }

    public void setLikes(List<Integer> likes) {
        this.likes = likes;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
