// BankAccount.java
// This class represents a simple bank account with basic operations like deposit, withdraw, and transfer.

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankAccount {

    private Integer account_id; // Unique identifier for the account (not used in this example)
    private String accountHolder; 
    private double balance;

    

    /**
     * Loads the balance of the account from the database.
     * 
     * @return the balance of the account
     */
    private void loadBalanceFromDatabase() {
        try (Connection conn = DatabaseManager.getConnection()) {
            String querySQL = "SELECT account_holder_name,balance FROM accounts WHERE account_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(querySQL);
            pstmt.setInt(1, account_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                this.accountHolder = rs.getString("account_holder_name");
                this.balance = rs.getDouble("balance");
            } else {
                System.out.println("Account not found in the database. Setting balance to 0.");
                this.balance = 0.0;
            }
        } catch (SQLException e) {
            System.out.println("Error loading account balance from database: " + e.getMessage());
            this.balance= 0.0;
        }
    }
    /**
     * Updates the account balance in the database.
     */
    public void updateBalanceInDatabase() {
        try (Connection conn = DatabaseManager.getConnection()) {
            String updateSQL = "UPDATE accounts SET balance = ? WHERE account_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateSQL);
            pstmt.setDouble(1, balance);
            pstmt.setInt(2, account_id);
            pstmt.executeUpdate();
            System.out.println("Account balance updated in database.");
        } catch (SQLException e) {
            System.out.println("Error updating account balance in database: " + e.getMessage());
        }
    }

    /**
     * Constructs a bank account with a given balance.
     * 
     * @param accountHolder  the name or ID of the account holder
     * @param initialBalance the initial balance
     */
    public BankAccount(int account_id,String accountHolder) {
        this.account_id = account_id;
        this.accountHolder = accountHolder;
        loadBalanceFromDatabase(); // Load balance from database
    }

    /**
     * Deposits money into the bank account.
     * 
     * @param amount the amount to deposit
     */
    public void deposit(double amount) {
        balance += amount;
        updateBalanceInDatabase(); // Update balance in database after deposit
    }

    /**
     * Withdraws money from the bank account.
     * 
     * @param amount the amount to withdraw
     */
    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds.");
        } else {
            balance -= amount;
            updateBalanceInDatabase(); // Update balance in database after withdrawal
        }
    }

    /**
     * Gets the current balance of the bank account.
     * 
     * @return the current balance
     */
    public double getBalance() {
        return balance;
    }
}

   