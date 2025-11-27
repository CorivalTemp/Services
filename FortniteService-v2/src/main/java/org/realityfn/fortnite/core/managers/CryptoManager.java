package org.realityfn.fortnite.core.managers;

import java.nio.charset.StandardCharsets;

public class CryptoManager {
    public static String decodeBase64(String encodedString) {
        byte[] decodedBytes = java.util.Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }

    public static String encodeBase64(String decodedString) {
        byte[] encodedBytes = java.util.Base64.getEncoder().encode(decodedString.getBytes());
        return new String(encodedBytes);
    }

    public static String hashSha256(String string) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(string.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        }
        catch (Exception e) {
            System.out.println("Error hashing: " + e.getMessage());
            return null;
        }
    }

    public static String hashSha1(String string) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(string.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        }
        catch (Exception e) {
            System.out.println("Error hashing: " + e.getMessage());
            return null;
        }
    }

    public static String hashSha256(byte[] data) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data);
            return bytesToHex(hash);
        }
        catch (Exception e) {
            System.out.println("Error hashing: " + e.getMessage());
            return null;
        }
    }

    public static String hashSha1(byte[] data) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(data);
            return bytesToHex(hash);
        }
        catch (Exception e) {
            System.out.println("Error hashing: " + e.getMessage());
            return null;
        }
    }


    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);

            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();
    }
}
