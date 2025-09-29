# Todo Application

A desktop Todo application built with Java Swing and MySQL database. This application provides a clean and intuitive interface for managing your daily tasks with full CRUD operations.

## Features

- Create new todo items with title and description
- Read and view all todos in a table format
- Update existing todos
- Delete completed or unwanted todos
- Filter todos by status (All, Completed, Pending)
- Mark todos as completed/incomplete
- Persistent storage using MySQL database
- Timestamps for creation and update tracking

## Technologies Used

- Java 24 - Core programming language
- Java Swing - GUI framework
- MySQL 8.0.33 - Database
- Maven - Dependency management and build tool
- JDBC - Database connectivity

## Prerequisites

Before running this application, ensure you have:

- Java Development Kit (JDK) 24 or higher
- Apache Maven 3.6+
- MySQL Server 8.0+
- MySQL JDBC Driver (included in dependencies)

## Database Setup

### Create Database

Install and start MySQL Server, then create a new database:

```sql
CREATE DATABASE todo;
```

### Create Table

Create the todos table:

```sql
USE todo;

CREATE TABLE todos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN DEFAULT FALSE,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Update Database Credentials

Update database credentials in `src/main/java/com/todo/util/DatabaseUtil.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/todo?useSSL=true&serverTimezone=UTC";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

## Installation and Setup

### Clone the Repository

```bash
git clone https://github.com/yourusername/dhinakaran311-todoapp.git
cd dhinakaran311-todoapp
```

### Build the Project

Build the project using Maven:

```bash
mvn clean install
```

### Run the Application

Run the application:

```bash
mvn exec:java
```

Alternatively, run the generated JAR file:

```bash
java -jar target/todo-application-1.0.0.jar
```

## Project Structure

```
dhinakaran311-todoapp/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── todo/
                    ├── Main.java              # Application entry point
                    ├── dao/
                    │   └── TodoDao.java       # Data Access Object
                    ├── gui/
                    │   └── TodoAppGUI.java    # GUI components
                    ├── model/
                    │   └── Todo.java          # Todo entity model
                    └── util/
                        └── DatabaseUtil.java  # Database connection utility
```

## Usage

### Adding a Todo

- Enter a title (required)
- Add a description (optional)
- Check "Completed" if applicable
- Click "Add Todo"

### Updating a Todo

- Select a todo from the table
- Modify the title, description, or completion status
- Click "Update Todo"

### Deleting a Todo

- Select a todo from the table
- Click "Delete Todo"
- Confirm the deletion

### Filtering Todos

Use the filter dropdown to view:
- All todos
- Completed todos only
- Pending todos only

### Refreshing the List

Click "Refresh Todo" to reload the current view

## Building Executable JAR

To create a standalone executable JAR with all dependencies:

```bash
mvn clean package
```

The JAR file will be created in the `target/` directory as `todo-application-1.0.0.jar`.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is open source and available under the MIT License.

## Author

Dhinakaran

GitHub: @dhinakaran311

## Acknowledgments

- Java Swing documentation
- MySQL documentation
- Maven Central Repository

## Known Issues

None at the moment

## Future Enhancements

- Add due date functionality
- Implement priority levels
- Add search functionality
- Export todos to CSV/PDF
- Add categories/tags
- Dark mode theme
- Reminder notifications
