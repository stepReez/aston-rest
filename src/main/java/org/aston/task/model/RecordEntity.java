package org.aston.task.model;

import java.util.List;
import java.util.UUID;

public class RecordEntity {
    private UUID id;

    private String title;

    private String text;

    private RecordLikes likes;

    private UserEntity author;

    private List<TagEntity> tag;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public RecordLikes getLikes() {
        return likes;
    }

    public void setLikes(RecordLikes likes) {
        this.likes = likes;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public List<TagEntity> getTag() {
        return tag;
    }

    public void setTag(List<TagEntity> tag) {
        this.tag = tag;
    }
}
