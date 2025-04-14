package loginsystem;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Login {
   
    // User Information - Save data of registered users
    private final ArrayList<User> users;

    // Constructor to initialize the list of users
    public Login() {
        this.users = new ArrayList<>();
    }

    // User class to store user details
    private static class User {
        String username;
        String password;

        public User(String username, String password, String phone) {
            this.username = username;
            this.password = password;
        }
    }

    // Method to check if the username is valid
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Method to check if the password is complex
    
    public boolean checkPasswordComplexity(String password) {
        String capital = ".*[A-Z].*";   // At least one uppercase
        String small = ".*[a-z].*";     // At least one lowercase
        String digit = ".*\\d.*";       // At least one digit
        String special = ".*[!@#$%^&*(),.?\":{}|<>].*"; // At least one special character

        return password.length() >= 8 &&
               password.matches(capital) &&
               password.matches(small) &&
               password.matches(digit) &&
               password.matches(special);
    }

    // Method to check if the phone number is a valid South African number
    public boolean checkCellPhoneNumber(String phone) {
        String saCode = "+27";
        if (phone.length() == 12 && phone.startsWith(saCode)) {
            char fourthDigitChar = phone.charAt(3);
            int fourthDigit = Character.getNumericValue(fourthDigitChar);
            return fourthDigit >= 6 && fourthDigit <= 8;
        }
        return false;
    }

    // Method to register the user
    public String registerUser(String username, String password, String phone) {
        boolean validatePhone = checkCellPhoneNumber(phone);
        boolean validateUsername = checkUserName(username);
        boolean validatePassword = checkPasswordComplexity(password);

        if (validatePhone && validateUsername && validatePassword) {
            // Save the user information
            users.add(new User(username, password, phone));
            return "User is successfully registered.";
        } else {
            return "User registration failed!";
        }
    }

    // Method to login user based on validation
    public boolean loginUser(String username, String password) {
        boolean validateUsername = checkUserName(username);
        boolean validatePassword = checkPasswordComplexity(password);

        if (validateUsername && validatePassword) {
            // Check if user exists
            for (User user : users) {
                if (user.username.equals(username) && user.password.equals(password)) {
                    return true; // Found matching user and password
                }
            }
        }
        return false;
    }

   
}





