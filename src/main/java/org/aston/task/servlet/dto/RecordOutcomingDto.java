package org.aston.task.servlet.dto;

import java.util.List;

public class RecordOutcomingDto {
    private String id;

    private String title;

    private String text;

    private RecordLikesDto likes;

    private String authorId;

    private List<String> tag;

    public RecordOutcomingDto() {

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

    public RecordLikesDto getLikes() {
        return likes;
    }

    public void setLikes(RecordLikesDto likes) {
        this.likes = likes;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }
}
