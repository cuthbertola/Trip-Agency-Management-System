package com.example.util;

public class GenerateHashedPassword {
    public static void main(String[] args) {
        // Password to be hashed
        String password = "Hazardeous16!"; // Replace with your actual password

        // Generate a new hashed password along with a newly generated salt
        String hashedPassword = HashingUtil.hashPassword(password);

        // Print the generated hashed password (with salt included)
        System.out.println("Generated Hashed Password: " + hashedPassword);
    }
}
