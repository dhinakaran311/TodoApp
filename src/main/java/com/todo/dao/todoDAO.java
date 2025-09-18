package com.todo.dao;

import com.todo.util.DatabaseUtil;
import com.todo.model.Todo;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class TodoDao {
    // private static final String SELECT_ALL_TODOS = "SELECT * from todos ORDER BY create_at DESC";
    // private static final String INSERT_TODO = "INSERT INTO todos (title, description, completed,create_at,update_at) VALUES (?, ?, ?,?,?)";

    // public int createtodo(Todo todo){
    //     try{

    //     }
    // }

    private Todo geTodorow(ResultSet res) throws SQLException // Mapping ResultSet to Todo Object
    {
        int id = res.getInt("id");
        String title = res.getString("title");
        String description = res.getString("description");
        boolean completed = res.getBoolean("completed");
        LocalDateTime create_at = res.getTimestamp("create_at").toLocalDateTime();
        LocalDateTime update_at = res.getTimestamp("update_at").toLocalDateTime();
        return new Todo(id, title, description, completed, create_at, update_at);// Object Mapping
    }

    public List<Todo> getAllTodos() throws SQLException {
        List<Todo> todos = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getDBConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos ORDER BY create_at DESC");
                ResultSet res = stmt.executeQuery()) {
            while (res.next()) {
                todos.add(geTodorow(res));
            }
        }
        return todos;
    }
}
