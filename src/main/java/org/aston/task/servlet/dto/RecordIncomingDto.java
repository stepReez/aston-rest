package org.aston.task.servlet.dto;

public class RecordIncomingDto {

    private String title;

    private String text;


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
}