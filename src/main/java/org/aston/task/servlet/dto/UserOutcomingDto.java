package org.aston.task.servlet.dto;

import java.util.List;

public class UserOutcomingDto {

    private String id;

    private String name;

    private List<RecordShortDto> records;

    public UserOutcomingDto() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RecordShortDto> getRecords() {
        return records;
    }

    public void setRecords(List<RecordShortDto> records) {
        this.records = records;
    }
}
