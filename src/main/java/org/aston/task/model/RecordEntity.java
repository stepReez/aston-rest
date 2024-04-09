package org.aston.task.model;

import java.util.List;
import java.util.UUID;

public class RecordEntity {
    private UUID id;

    private String title;

    private String text;

    private List<UserEntity> likes;

    private UserEntity author;

    private TagEntity tag;

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

    public List<UserEntity> getLikes() {
        return likes;
    }

    public void setLikes(List<UserEntity> likes) {
        this.likes = likes;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public TagEntity getTag() {
        return tag;
    }

    public void setTag(TagEntity tag) {
        this.tag = tag;
    }
}
