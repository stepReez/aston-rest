package org.aston.task.servlet.dto;

import java.util.List;

public class RecordIncomingDto {

    private String title;

    private String text;

    private List<Integer> tag;


    public RecordIncomingDto() {

    }

    public RecordIncomingDto(String title, String text) {
        this.title = title;
        this.text = text;
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

    public List<Integer> getTag() {
        return tag;
    }

    public void setTag(List<Integer> tag) {
        this.tag = tag;
    }
}
