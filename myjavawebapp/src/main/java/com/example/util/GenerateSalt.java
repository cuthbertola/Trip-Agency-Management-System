package com.example.util;

public class GenerateSalt {
    public static void main(String[] args) {
        String salt = HashingUtil.generateSalt();
        System.out.println("Generated Salt: " + salt);
    }
}
