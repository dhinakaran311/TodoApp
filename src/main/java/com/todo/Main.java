package com.todo;

import com.todo.util.DatabaseUtil;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DatabaseUtil db_Connection = new DatabaseUtil();
        try {
            Connection cn = db_Connection.getDBConnection();
            if (cn != null) {
                System.out.println("✅Database Connection Successful");
            }
        } catch (Exception e) {
            System.out.println("❌❌Database Connection Failed: " + e.getMessage());
        }
    }
}