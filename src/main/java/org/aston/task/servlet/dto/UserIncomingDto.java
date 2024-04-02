package org.aston.task.servlet.dto;

public class UserIncomingDto {

    private String userName;

    public UserIncomingDto() {

    }

    public UserIncomingDto(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
