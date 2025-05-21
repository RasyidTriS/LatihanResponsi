package connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/recruit_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private static Connection connection;
    
    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    // Load driver
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    // Create connection
                    connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                } catch (ClassNotFoundException ex) {
                    System.err.println("Error: Database driver not found");
                    throw new SQLException("Database driver not found", ex);
                }
            }
            return connection;
        } catch (SQLException ex) {
            System.err.println("Error connecting to database: " + ex.getMessage());
            throw ex;
        }
    }
    
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.err.println("Error closing connection: " + ex.getMessage());
        }
    }
}