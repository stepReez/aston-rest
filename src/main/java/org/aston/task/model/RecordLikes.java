package org.aston.task.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecordLikes {
    List<UUID> recordLikes;

    public RecordLikes() {
        recordLikes = new ArrayList<>();
    }

    public List<UUID> getRecordLikes() {
        return recordLikes;
    }

    public void setRecordLikes(List<UUID> recordLikes) {
        this.recordLikes = recordLikes;
    }
}
