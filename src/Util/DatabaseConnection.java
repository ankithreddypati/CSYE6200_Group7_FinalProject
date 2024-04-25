package Util;

import java.sql.*;

public class DatabaseConnection {
    private static volatile Connection connection = null;

    private DatabaseConnection() {
        // Prevent instantiation
        if (connection != null) {
            throw new RuntimeException("Use getConnection() method to get the single instance of this class.");
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DatabaseConnection.class) {
                if (connection == null) {
                    try {
                        String url = "jdbc:mysql://localhost:3306/";
                        String user = "root";
                        //String password = "Password!@#123";
                        String password = "2001050926";
                        Connection tempConn = DriverManager.getConnection(url, user, password);
                        ensureDatabase(tempConn);
                        connection = DriverManager.getConnection(url + "mydatabase", user, password);
                        initializeDatabase();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }

    private static void ensureDatabase(Connection tempConn) throws SQLException {
        try (Statement statement = tempConn.createStatement()) {
            String databaseName = "mydatabase";
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + databaseName);
        } catch (SQLException e) {
            System.out.println("Error creating database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initializeDatabase() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Enhance the table creation to include paths for CSV files
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(255) NOT NULL UNIQUE, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "email VARCHAR(255) NOT NULL, " +
                    "phone INT NOT NULL, " +
                    "bank_transactions_path VARCHAR(255), " +
                    "assets_liabilities_path VARCHAR(255), " +
                    "investments_path VARCHAR(255))");
            createDefaultUser();
        }
    }

    private static void createDefaultUser() {
        try (PreparedStatement checkUser = connection.prepareStatement("SELECT username FROM users WHERE username = ?")) {
            checkUser.setString(1, "admin");
            try (ResultSet rs = checkUser.executeQuery()) {
                if (!rs.next()) {  // Check if the 'admin' user already exists
                    try (PreparedStatement insertUser = connection.prepareStatement(
                            "INSERT INTO users (username, password, email, phone) VALUES (?, ?, ?, ?)")) {
                        insertUser.setString(1, "admin");
                        insertUser.setString(2, "adminpass");
                        insertUser.setString(3, "admin@example.com");
                        insertUser.setInt(4, 123456789);
                        insertUser.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error creating default user: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
