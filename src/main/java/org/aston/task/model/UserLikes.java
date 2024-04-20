package org.aston.task.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserLikes {
    private List<UUID> userLikes;

    public UserLikes() {
        userLikes = new ArrayList<>();
    }

    public List<UUID> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(List<UUID> userLikes) {
        this.userLikes = userLikes;
    }
}
