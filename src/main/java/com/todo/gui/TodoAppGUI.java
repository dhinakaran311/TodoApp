package com.todo.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.todo.dao.TodoDao;
import com.todo.model.Todo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class TodoAppGUI extends JFrame {
    private TodoDao todoDAO;
    private JTable todoTable;
    private DefaultTableModel tableModel;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JCheckBox completCheckBox;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton refreshButton;
    private JComboBox<String> filterComboBox;

    public TodoAppGUI() {
        this.todoDAO = new TodoDao();
        initializeComponents();
        setuplayout();
        setupeventlisteners();
        loadTodos();
    }

    private void initializeComponents() {
        setTitle("Todo Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        String[] columnNames = { "ID", "Title", "Description", "Completed", "Created At", "Updated At" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        todoTable = new JTable(tableModel);
        todoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        todoTable.getSelectionModel().addListSelectionListener(
                (e) -> {
                    if (!e.getValueIsAdjusting()) {
                        loadSelectedtodo();
                    }
                });

        titleField = new JTextField(20);
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        completCheckBox = new JCheckBox("Completed");

        addButton = new JButton("Add Todo");
        updateButton = new JButton("Update Todo");
        deleteButton = new JButton("Delete Todo");
        refreshButton = new JButton("Refresh Todo");

        String[] filteroptions = { "All", "Completed", "Pending" };
        filterComboBox = new JComboBox<>(filteroptions);
        filterComboBox.addActionListener((e) -> {
            filterTodos();
        });
    }

    private void setuplayout() {
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(titleField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(new JScrollPane(descriptionArea), gbc);
        // gbc.gridx = 1;
        gbc.gridx = 2;
        inputPanel.add(completCheckBox, gbc);

        JPanel buttoPanel = new JPanel(new FlowLayout());
        buttoPanel.add(addButton);
        buttoPanel.add(updateButton);
        buttoPanel.add(deleteButton);
        buttoPanel.add(refreshButton);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filter:"));
        filterPanel.add(filterComboBox);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(inputPanel, BorderLayout.CENTER);
        northPanel.add(buttoPanel, BorderLayout.SOUTH);
        northPanel.add(filterPanel, BorderLayout.NORTH);

        add(northPanel, BorderLayout.NORTH);

        add(new JScrollPane(todoTable), BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(new JLabel("Select a todo item to update or delete."));
        add(statusPanel, BorderLayout.SOUTH);
    }

    private void setupeventlisteners() {
        addButton.addActionListener(e -> addtodo());
        updateButton.addActionListener(e -> updatetodo());
        deleteButton.addActionListener(e -> deletetodo());
        refreshButton.addActionListener(e -> refreshtodo());
    }

    private void addtodo() {
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();
        boolean completed = completCheckBox.isSelected();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Todo newTodo = new Todo(title, description);
            newTodo.setCompleted(completed);
            int newId = todoDAO.createtodo(newTodo);
            newTodo.setId(newId);
            loadTodos();
            titleField.setText("");
            descriptionArea.setText("");
            completCheckBox.setSelected(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding todo: " + e.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSelectedtodo() {
        int selectedRow = todoTable.getSelectedRow();
        if (selectedRow >= 0) {
            String title = (String) tableModel.getValueAt(selectedRow, 1);
            String description = (String) tableModel.getValueAt(selectedRow, 2);
            Boolean completed = (boolean) tableModel.getValueAt(selectedRow, 3);

            titleField.setText(title);
            descriptionArea.setText(description);
            completCheckBox.setSelected(completed);
        }
    }

    private void updatetodo() {
        int selectedRow = todoTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a todo to update.", "Selection Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Todo todo = null;
        try {
            todo = todoDAO.getTodobyId(id);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving todo: " + e.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (todo != null) {
            todo.setTitle(titleField.getText().trim());
            todo.setDescription(descriptionArea.getText().trim());
            todo.setCompleted(completCheckBox.isSelected());
            try {
                boolean success = todoDAO.updatetodo(todo);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Todo updated successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadTodos();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update todo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error updating todo: " + e.getMessage(), "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            titleField.setText("");
            descriptionArea.setText("");
            completCheckBox.setSelected(false);
        }
    }

    private void deletetodo() {
        int selectedRows = todoTable.getSelectedRow();
        if (selectedRows < 0) {
            JOptionPane.showMessageDialog(this, "Please select a todo to delete.", "Selection Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) todoTable.getValueAt(selectedRows, 0);
        try {
            boolean deleted = todoDAO.deleteTodo(id);
            if (deleted) {
                JOptionPane.showMessageDialog(this, "Todo deleted successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                titleField.setText("");
                descriptionArea.setText("");
                completCheckBox.setSelected(false);
                loadTodos();

            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete Todo", "Delete Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting todo: " + e.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshtodo() {
        loadTodos();
        filterComboBox.setSelectedIndex(0);
        JOptionPane.showMessageDialog(this, "Todo list refreshed.", "Info", JOptionPane.INFORMATION_MESSAGE);
        titleField.setText("");
        descriptionArea.setText("");
        completCheckBox.setSelected(false);
    }

    private void loadTodos() {

        try {
            List<Todo> todos = todoDAO.getAllTodos();
            updateTable(todos);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(this, "Error loading todos: " + e.getMessage(), "DatabaseError",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void updateTable(List<Todo> todos) {
        tableModel.setRowCount(0);
        for (Todo t : todos) {
            Object[] row = { t.getId(), t.getTitle(), t.getDescription(), t.isCompleted(), t.getCreate_at(),
                    t.getUpdate_at() };
            tableModel.addRow(row);
        }
    }

    private void filterTodos() {
        String filter = (String) filterComboBox.getSelectedItem();
        try {
            List<Todo> todos;
            if (filter.equals("Completed")) {
                todos = todoDAO.getCompletedTodos();
            } else if (filter.equals("Pending")) {
                todos = todoDAO.getNotCompletedTodos();
            } else {
                todos = todoDAO.getAllTodos();
            }
            titleField.setText("");
            descriptionArea.setText("");
            completCheckBox.setSelected(false);
            updateTable(todos);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error filtering todos: " + e.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
