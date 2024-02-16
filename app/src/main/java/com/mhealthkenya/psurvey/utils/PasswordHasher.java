package com.mhealthkenya.psurvey.utils;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for hashing passwords using SHA-256 algorithm.
 */
public class PasswordHasher {
    /**
     * Hashes a password using SHA-256 algorithm.
     *
     * @param password The password to be hashed.
     * @return A hexadecimal string representation of the hashed password.
     *         Returns null if an error occurs during hashing.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e("-->PasswordHasher", "Hashing algorithm not found: " + e.getMessage());
            return null;
        }
    }
}
