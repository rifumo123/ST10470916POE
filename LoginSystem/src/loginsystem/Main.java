package loginsystem;

import loginsystem.Login; // Import the Login class
import javax.swing.JOptionPane; // Import the JOptionPane class

public class Main {

    public static void main(String[] args) {

        String firstname, lastname, username, password, phone;

        // Show registration header
        JOptionPane.showMessageDialog(null, "-----------Register-----------");

        // Get registration inputs
        firstname = JOptionPane.showInputDialog("Enter First Name: ");
        lastname = JOptionPane.showInputDialog("Enter Last Name: ");
        username = JOptionPane.showInputDialog("Enter username (must include '_' and be no more than 5 characters): ");
        password = JOptionPane.showInputDialog("Enter password (must be at least 8 characters, include capital & small letter, number & special character): ");
        phone = JOptionPane.showInputDialog("Enter phone number (must start with +27 and be 12 characters): ");

        Login login = new Login(); // Create Login system

        // Validate inputs
        boolean validUsername = login.checkUserName(username);
        boolean validPassword = login.checkPasswordComplexity(password);
        boolean validPhone = login.checkCellPhoneNumber(phone);

        // Show validation feedback
        if (validUsername) {
            JOptionPane.showMessageDialog(null, "Username successfully captured.");
        } else {
            JOptionPane.showMessageDialog(null, "Username is not correctly formatted. Please ensure that your username contains an underscore and is not more than five characters in length.");
        }

        if (validPassword) {
            JOptionPane.showMessageDialog(null, "Password successfully captured.");
        } else {
            JOptionPane.showMessageDialog(null, "Password is incorrect. Please ensure it has at least 8 characters, a capital letter, a lowercase letter, a number, and a special character.");
        }

        if (validPhone) {
            JOptionPane.showMessageDialog(null, "Cellphone number successfully added.");
        } else {
            JOptionPane.showMessageDialog(null, "Cellphone number is incorrectly formatted or does not start with the international code +27.");
        }

        // Register user only if all inputs are valid
        if (validUsername && validPassword && validPhone) {
            String registrationMessage = login.registerUser(username, password, phone);
            JOptionPane.showMessageDialog(null, registrationMessage);

            // Show login prompt
            JOptionPane.showMessageDialog(null, "-----------Login-----------");
            String loginUsername = JOptionPane.showInputDialog("Enter username: ");
            String loginPassword = JOptionPane.showInputDialog("Enter password: ");

            // Try to login using Login system
            if (login.loginUser(loginUsername, loginPassword)) {
                JOptionPane.showMessageDialog(null, "Welcome " + firstname + " " + lastname + ", it is great to see you again.");
            } else {
                JOptionPane.showMessageDialog(null, "Login failed! Wrong username or password.");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Failed to register. Please try again with valid details.");
        }
    }
}