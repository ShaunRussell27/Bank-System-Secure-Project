package test;
import org.junit.jupiter.api.Test;

import AccountTester;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class AuthenticationTests {

    @Test
    public void testSuccessfulLogin() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Test logic for successful login
        // Mock valid credentials
        String username = "validUser";
        String password = "ValidPassword1";

        // Simulate login
        boolean result = AccountTester.loginWithMockData(username, password);

        // Assert login is successful
        assertTrue(result, "Login should succeed with valid credentials.");
    }

    @Test
    public void testFailedLoginWithInvalidPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Mock valid username but invalid password
        String username = "validUser";
        String password = "InvalidPassword";

        // Simulate login
        boolean result = AccountTester.loginWithMockData(username, password);

        // Assert login fails
        assertFalse(result, "Login should fail with an invalid password.");
    }

    @Test
    public void testOTPGeneration() {
        // Test logic for OTP generation
        // Generate OTP
        int otp = AccountTester.generateOTP();

        // Assert OTP is a 4-digit number
        assertTrue(otp >= 1000 && otp <= 9999, "OTP should be a 4-digit number.");
    }
}