import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private static final String CONFIG_FILE = "config.properties"; // Path to the properties file
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;

    // Static block to load database configuration from the properties file
    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            Properties props = new Properties();
            props.load(fis);

            dbUrl = props.getProperty("DB_URL");
            dbUser = props.getProperty("DB_USER");
            dbPassword = props.getProperty("DB_PASSWORD");

            if (dbUrl == null || dbUser == null || dbPassword == null) {
                throw new IllegalStateException("Database configuration is missing in config.properties.");
            }
        } catch (IOException e) {
            System.err.println("Error loading database configuration: " + e.getMessage());
            throw new RuntimeException("Failed to load database configuration.", e);
        }
    }

    // Method to get a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}