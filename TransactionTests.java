package test;
import org.junit.jupiter.api.Test;

import BankAccount;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTests {

    @Test
    public void testDeposit() {
        // Test logic for deposit
        BankAccount account = new BankAccount(1, "TestUser");
        double initialBalance = account.getBalance();
        double depositAmount = 100.0;

        account.deposit(depositAmount);

        assertEquals(initialBalance + depositAmount, account.getBalance(), "Balance should increase by the deposit amount.");
    }

    @Test
    public void testWithdraw() {
        // Test logic for withdrawal
        BankAccount account = new BankAccount(1, "TestUser");
        account.deposit(200.0); // Ensure sufficient balance
        double initialBalance = account.getBalance();
        double withdrawalAmount = 50.0;

        account.withdraw(withdrawalAmount);

        assertEquals(initialBalance - withdrawalAmount, account.getBalance(), "Balance should decrease by the withdrawal amount.");
    }
    }

    @Test
    public void testInsufficientFunds() {
        // Test logic for insufficient funds
        BankAccount account = new BankAccount(1, "TestUser");
        account.deposit(200.0); // Ensure sufficient balance
        double initialBalance = account.getBalance();
        double withdrawalAmount = 50.0;

        account.withdraw(withdrawalAmount);

        assertEquals(initialBalance - withdrawalAmount, account.getBalance(), "Balance should decrease by the withdrawal amount.");
    }
}