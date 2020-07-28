package com.example.todo_app;

public class TodoModel {
    private int id;
    private String item;

    public TodoModel(int id, String item){
        this.id = id;
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "- " + item;
    }
}
