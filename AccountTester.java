
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;





public class AccountTester {

    private static final SecureRandom random =  new SecureRandom();
    private static String loggedInUser = null;
    private static int loggedInUserId = -1; // Initialize to an invalid ID

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

         Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            System.out.println("Error loading configuration file.");
            e.printStackTrace();
            return;
        }

        // Database credentials
        String url = props.getProperty("DB_URL");
        String user = props.getProperty("DB_USER");
        String password = props.getProperty("DB_PASSWORD");

        try {
            // Load MySQL JDBC Driver (Optional for JDBC 4.0+)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection conn = DriverManager.getConnection(url, user, password);

            System.out.println("Connected successfully!");

            // Always close connections when done
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        boolean exit = false;

        while (!exit) {
            System.out.println("=========================================");
            System.out.println("Welcome to the Shauns Bank account system!");
            System.out.println("1. Login");
            System.out.println("2. Sign up");
            System.out.println("3. exit");
            System.out.println("Type 'exit' at any time to quit the program.");
            System.out.print("Please select an option (1, 2 or 3): ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 1) {
                    // Login process
                    if (login(scanner)) {
                        System.out.println("Login successful!");
                        accessBankFeatures(scanner);
                    } else {
                        System.out.println("Login failed. Exiting system.");
                    }
                } else if (choice == 2) {
                    createAccount(scanner);
                } else if (choice == 3) {
                    System.out.println("Exiting the system. Goodbye!");
                    exit = true;
                } else {
                    System.out.println("Invalid option. Please select either (1 , 2  or 3).");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please select either (1 , 2  or 3).");
                scanner.nextLine(); // Clear invalid input
            }
        }

        scanner.close();
    }

    // login method
    private static boolean login(Scanner scanner) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int attempts = 3; // Allow 3 login attempts
    
        while (attempts > 0) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
    
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
    
            try (Connection conn = DatabaseManager.getConnection()) {
                String querySQL = "SELECT account_id,encrypted_password, salt FROM accounts WHERE account_holder_name = ?";
                PreparedStatement pstmt = conn.prepareStatement(querySQL);
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
    
                if (rs.next()) {
                    int accountId = rs.getInt("account_id");
                    byte[] encryptedPassword = rs.getBytes("encrypted_password");
                    byte[] salt = rs.getBytes("salt");
    
                    // Validate the password
                    if (!PasswordEncryptionService.authenticate(password, encryptedPassword, salt)) {
                        System.out.println("Invalid password.");
                        attempts--;
                        System.out.println("Attempts remaining: " + attempts);
                        continue;
                    }
    
                    // Generate a new OTP
                    int otp = generateOTP();
                    System.out.println("Your One-Time Password (OTP) is: " + otp);
    
                    // Prompt the user to enter the OTP
                    System.out.print("Enter the OTP: ");
                    try {
                        int userOTP = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
    
                        // Validate the OTP
                        if (userOTP == otp) {
                            System.out.println("Login successful!");
                            loggedInUser = username; // Store the logged-in user
                            loggedInUserId = accountId; // Store the logged-in user ID
                            return true; // Login successful
                        } else {
                            System.out.println("Invalid OTP.");
                            attempts--;
                            System.out.println("Attempts remaining: " + attempts);
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a valid OTP.");
                        scanner.nextLine(); // Clear invalid input
                        attempts--;
                        System.out.println("Attempts remaining: " + attempts);
                    }
                } else {
                    System.out.println("Invalid username.");
                    attempts--;
                    System.out.println("Attempts remaining: " + attempts);
                }
            } catch (SQLException e) {
                System.out.println("Error retrieving account from database: " + e.getMessage());
                return false; // Return false if there is a database error
            }
        }
    
        System.out.println("Too many failed attempts. Returning to the main menu.");
        return false; // Login failed after 3 attempts
    }

    private static int generateOTP() {
        // Generate a 4-digit OTP
        return 1000 + random.nextInt(9000); // Generates a number between 1000 and 9999
    }

    // creating a new account
    private static void createAccount(Scanner scanner) {
        //System.out.print("Enter a new username: ");
        //String newUsername = scanner.nextLine();
        String newUsername;
        while (true) {
            System.out.print("Enter a new username (3-20 characters, alphanumeric): ");
            newUsername = scanner.nextLine();
            if (newUsername.equalsIgnoreCase("exit")) {
                System.out.println("Exiting account creation...");
                return; // Exit the method
            }
           // Check if the username is valid
            if (newUsername.length() < 3 || newUsername.length() > 20) {
                System.out.println("Invalid username. It must be between 3 and 20 characters long.");
            } else if (!newUsername.matches("[a-zA-Z0-9]+")) {
                System.out.println("Invalid username. It must only contain alphanumeric characters (letters and numbers).");
            } else {
                break; // Username is valid
            }
        }

        // Check if the username already exists
        try (Connection conn = DatabaseManager.getConnection()) {
            String checkSQL = "SELECT COUNT(*) FROM accounts WHERE account_holder_name = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSQL);
            checkStmt.setString(1, newUsername);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Username already exists. Please choose a different username.");
                return; // Exit the method if the username already exists
            }
        } catch (SQLException e) {
            System.out.println("Error checking username availability: " + e.getMessage());
            return; // Exit the method if there is a database error
        }


        //System.out.print("Enter a new password: ");
        //String newPassword = scanner.nextLine();
        String newPassword;
        while (true) {
            System.out.print("Enter a new password (8-20 characters, must include uppercase, lowercase, and a number): ");
            newPassword = scanner.nextLine();
            if (newPassword.equalsIgnoreCase("exit")) {
                System.out.println("Exiting account creation...");
                return; // Exit the method
            }
            
            // Check if the password is valid
            boolean isValid = true;

            if (newPassword.length() < 8 || newPassword.length() > 20) {
                System.out.println("Invalid password. It must be between 8 and 20 characters long.");
                isValid = false;
            }
            if (!newPassword.matches(".*[A-Z].*")) {
                System.out.println("Invalid password. It must include at least one uppercase letter.");
                isValid = false;
            }
            if (!newPassword.matches(".*[a-z].*")) {
                System.out.println("Invalid password. It must include at least one lowercase letter.");
                isValid = false;
            }
            if (!newPassword.matches(".*\\d.*")) {
                System.out.println("Invalid password. It must include at least one number.");
                isValid = false;
            }
            if (isValid) {
                break; // Password is valid
            }
        }

        try {
            // Generate salt and encrypt the password
            byte[] salt = PasswordEncryptionService.generateSalt();
            byte[] encryptedPassword = PasswordEncryptionService.getEncryptedPassword(newPassword, salt);

            // Save the account to the database
            try (Connection conn = DatabaseManager.getConnection()) {
                String insertSQL = "INSERT INTO accounts (account_holder_name, encrypted_password, salt) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(insertSQL);
                pstmt.setString(1, newUsername);
                pstmt.setBytes(2, encryptedPassword);
                pstmt.setBytes(3, salt);
                pstmt.executeUpdate();
                System.out.println("Account created successfully! You can now log in.");
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("Error creating account: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error saving account to database: " + e.getMessage());
        }
    }

    private static void accessBankFeatures(Scanner scanner) {
        System.out.println("\nWelcome to the bank features menu, " + loggedInUser +"("+loggedInUserId +")"+ "!");
    
        // Create a new BankAccount object for the user
        BankAccount account = new BankAccount(loggedInUserId,loggedInUser);
    
        boolean exitFeatures = false;
    
        while (!exitFeatures) {
            System.out.println("\nBank Features Menu:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit Features and logout");
            System.out.print("Select an option: ");
    
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
    
                switch (choice) {
                    case 1: // Deposit
                    double depositAmount;
                    while (true) {
                        System.out.print("Enter amount to deposit or type 'exit' to quit: ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("exit")) {
                            System.out.println("Exiting deposit process...");
                            break;
                        }
                        try {
                            depositAmount = Double.parseDouble(input);
                            if (depositAmount > 0) {
                                account.deposit(depositAmount);
                                System.out.println("Deposited " + depositAmount + ". New balance: " + account.getBalance());
                                break;
                            } else {
                                System.out.println("Amount must be positive. Please try again.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }
                    break;
    
                    case 2: // Withdraw
                    double withdrawAmount;
                    while (true) {
                        System.out.print("Enter amount to withdraw or type 'exit' to quit: ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("exit")) {
                            System.out.println("Exiting withdrawal process...");
                            break;
                        }
                        try {
                            withdrawAmount = Double.parseDouble(input);
                            if (withdrawAmount > 0) {
                                account.withdraw(withdrawAmount);
                                System.out.println("Withdrawn " + withdrawAmount + ". New balance: " + account.getBalance());
                                break;
                            } else {
                                System.out.println("Amount must be positive. Please try again.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }
                    break;
    
                    case 3: // Check Balance
                        System.out.println("Current balance: " + account.getBalance());
                        break;
    
                    case 4: // Exit Features
                        System.out.println("Exiting bank features...");
                        exitFeatures = true;
                        break;
    
                    default:
                        System.out.println("Invalid option. Please select 1, 2, 3, or 4.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
   

}