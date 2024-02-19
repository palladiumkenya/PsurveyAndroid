package com.mhealthkenya.psurvey.utils;

import com.mhealthkenya.psurvey.BuildConfig;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * Utility class for encrypting passwords.
 */
public class PasswordHasher {
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = BuildConfig.SECRET_KEY;

    /**
     * Encrypts a password using AES encryption.
     *
     * @param password The password to be encrypted.
     * @return A hexadecimal string representation of the encrypted password.
     *         Returns null if an error occurs during encryption.
     */
    public static String encryptPassword(String password) {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Decrypts an encrypted password using AES encryption.
     *
     * @param encryptedPassword The encrypted password to be decrypted.
     * @return The decrypted password as a string.
     *         Returns null if an error occurs during decryption.
     */
    public static String decryptPassword(String encryptedPassword) {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(hexToBytes(encryptedPassword));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Key generateKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static byte[] hexToBytes(String hexString) {
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }
}
