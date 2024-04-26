package org.aston.task.servlet.dto;

import java.util.List;

public class UserOutcomingDto {

    private String id;

    private String name;

    private List<String> recordsId;

    public UserOutcomingDto() {

    }

    public UserOutcomingDto(String id, String name, List<String> recordsId) {
        this.id = id;
        this.name = name;
        this.recordsId = recordsId;
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

    public List<String> getRecordsId() {
        return recordsId;
    }

    public void setRecordsId(List<String> recordsId) {
        this.recordsId = recordsId;
    }
}
