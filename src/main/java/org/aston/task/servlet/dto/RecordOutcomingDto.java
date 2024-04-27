package org.aston.task.servlet.dto;

import java.util.List;

public class RecordOutcomingDto {
    private String id;

    private String title;

    private String text;

    private UserShortDto author;

    private List<TagOutcomingDto> tag;

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

    public UserShortDto getAuthor() {
        return author;
    }

    public void setAuthor(UserShortDto author) {
        this.author = author;
    }

    public List<TagOutcomingDto> getTag() {
        return tag;
    }

    public void setTag(List<TagOutcomingDto> tag) {
        this.tag = tag;
    }
}
