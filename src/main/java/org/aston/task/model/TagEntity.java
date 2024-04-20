package org.aston.task.model;

public class TagEntity {

    private int id;

    private String name;

    public TagEntity() {

    }

    public TagEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
