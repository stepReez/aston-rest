package org.aston.task.servlet.dto;

import java.util.List;

public class UserLikesDto {
    List<String> userLikes;

    public List<String> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(List<String> userLikes) {
        this.userLikes = userLikes;
    }
}
