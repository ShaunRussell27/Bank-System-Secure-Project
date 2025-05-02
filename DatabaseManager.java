import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bank_system"; // Replace with  database URL
    private static final String DB_USER = "root"; // Replace with MySQL username
    private static final String DB_PASSWORD = "Leavers2023"; // Replace with MySQL password

    // Method to get a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}