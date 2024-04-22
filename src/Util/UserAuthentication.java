package Util;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthentication {

    /**
     * Authenticate a user with the provided username and password.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return true if authentication is successful, false otherwise.
     */
    public static User authenticate(String username, String password) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?"; // Query might need password hashing in real scenarios

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Create and return a new User object based on the fetched data
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getInt("phone"), rs.getString("bank_transactions_path"),
                        rs.getString("assets_liabilities_path"), rs.getString("investments_path"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if authentication fails
    }


    /**
     * Registers a new user with the details provided, including paths for CSV files.
     * @param username The username for the new user.
     * @param password The password for the new user.
     * @param email The email address of the new user.
     * @param phone The phone number of the new user.
     * @param bankPath The path to the bank transactions CSV.
     * @param assetsPath The path to the assets and liabilities CSV.
     * @param investmentsPath The path to the investments CSV.
     * @return true if the registration is successful, false otherwise.
     */
    public static boolean registerUser(String username, String password, String email, int phone, String bankPath, String assetsPath, String investmentsPath) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO users (username, password, email, phone, bank_transactions_path, assets_liabilities_path, investments_path) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);  // In production, use a hashed password here
            stmt.setString(3, email);
            stmt.setInt(4, phone);
            stmt.setString(5, bankPath);
            stmt.setString(6, assetsPath);
            stmt.setString(7, investmentsPath);
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
