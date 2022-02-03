package com.example.tasklist;

public class Contact {
    private String name;
    private String id;

    public Contact(String date, String id) {
        this.name = date;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
