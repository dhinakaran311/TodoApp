package com.todo.model;
import java.time.LocalDateTime;
public class Todo {
    private int id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime create_at;
    private LocalDateTime update_at;
    public Todo() {
        this.create_at = LocalDateTime.now();
        this.update_at = LocalDateTime.now();
        this.completed = false;
    }
    public Todo(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }
    public Todo(int id, String title, String description, boolean completed, LocalDateTime create_at,
            LocalDateTime update_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.create_at = create_at;
        this.update_at = update_at;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public LocalDateTime getCreate_at() {
        return create_at;
    }
    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }
    public LocalDateTime getUpdate_at() {
        return update_at;
    }
    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
    }
    
}
