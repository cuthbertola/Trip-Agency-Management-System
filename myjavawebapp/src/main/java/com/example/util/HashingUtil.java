package com.example.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class HashingUtil {

    // Method to hash password with a newly generated salt
    public static String hashPassword(String password) {
        String salt = generateSalt();
        return hashPasswordWithSalt(password, salt);
    }

    // Method to hash password with an existing salt
    public static String hashPasswordWithSalt(String password, String salt) {
        try {
            // Append the salt to the password
            String passwordWithSalt = salt + password;

            // Use SHA-256 hashing algorithm
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(passwordWithSalt.getBytes(StandardCharsets.UTF_8));

            // Convert the hashed bytes to hex format
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            // Combine salt and hashed password, separated by a colon, for storage
            return salt + ":" + sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password: " + e.getMessage(), e);
        }
    }

    // Method to generate a salt
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    // Method to verify if the provided password matches the stored hashed password
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            // Extract the salt and the original hash from the stored hash
            String[] parts = storedHash.split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Stored hash format is incorrect. Expected format: <salt>:<hash>");
            }
            String salt = parts[0];
            String originalHash = parts[1];

            // Hash the provided password with the extracted salt
            String hashedPasswordWithSalt = hashPasswordWithSalt(password, salt);

            // Extract the newly calculated hash part
            String recalculatedHash = hashedPasswordWithSalt.split(":")[1];

            // Compare the recalculated hash with the original hash
            return originalHash.equals(recalculatedHash);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Error verifying password: Malformed stored hash format", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error verifying password: " + e.getMessage(), e);
        }
    }

    // Method to extract salt from stored hash
    public static String getSaltFromStoredHash(String storedHash) {
        try {
            return storedHash.split(":")[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Error extracting salt: Malformed stored hash format", e);
        }
    }

    // Method to extract hash from stored hash
    public static String getHashFromStoredHash(String storedHash) {
        try {
            return storedHash.split(":")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Error extracting hash: Malformed stored hash format", e);
        }
    }

    // NEW: Method to generate a hashed password for manual setup
    public static void generateHashedPasswordForSetup(String password) {
        String hashedPassword = hashPassword(password);
        System.out.println("Generated hashed password: " + hashedPassword);
    }

    // Main method for testing and generating new hashed passwords
    public static void main(String[] args) {
        // Example usage to generate a hashed password
        String passwordToHash = "admin123"; // You can change this password
        generateHashedPasswordForSetup(passwordToHash);
    }
}
