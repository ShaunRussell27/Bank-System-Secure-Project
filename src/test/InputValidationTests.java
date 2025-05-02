package test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InputValidationTests {

    @Test
    public void testValidUsername() {
        // Test logic for valid username
        String username = "ValidUser123";
        boolean isValid = username.matches("[a-zA-Z0-9]{3,20}");
        assertTrue(isValid, "Username should be valid.");
    }

    @Test
    public void testInvalidUsernameTooShort() {
        String username = "Us";
        boolean isValid = username.matches("[a-zA-Z0-9]{3,20}");
        assertFalse(isValid, "Username should be invalid if too short.");
    }

    @Test
    public void testValidPassword() {
        String password = "ValidPass1";
        boolean isValid = password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$");
        assertTrue(isValid, "Password should be valid.");
    }

    @Test
    public void testInvalidPasswordNoUppercase() {
        String password = "invalidpass1";
        boolean isValid = password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$");
        assertFalse(isValid, "Password should be invalid if it lacks an uppercase letter.");
    }
}