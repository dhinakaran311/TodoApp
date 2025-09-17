package com.todo;

import com.todo.util.DatabaseUtil;
import java.sql.Connection;
import com.todo.gui.TodoAppGUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
            System.exit(1);
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            System.err.println("Failed to set look and feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            try {
                new TodoAppGUI().setVisible(true);
            } catch (Exception e) {
                System.err.println("Failed to launch GUI: " + e.getMessage());
            }
        });
    }
}