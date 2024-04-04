package org.aston.task.servlet.dto;

public class UserIncomingDto {

    private String name;

    public UserIncomingDto() {

    }

    public UserIncomingDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
