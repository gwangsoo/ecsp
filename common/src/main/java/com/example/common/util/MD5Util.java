package com.example.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@Slf4j
public class MD5Util {

    public static String generator(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        messageDigest.update(input.getBytes(StandardCharsets.UTF_8));

        byte[] md5Hash = messageDigest.digest();
        StringBuilder hexMD5hash = new StringBuilder();

        for(byte b : md5Hash) {
            String hexString = String.format("%02x", b);
            hexMD5hash.append(hexString);
        }

        return hexMD5hash.toString();
    }

    public static String generator2(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String salt = "xrf";

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();

        StringBuilder hexMD5hash = new StringBuilder();

        for (byte b : hash) {
            String hexString = String.format("%02x", b);
            hexMD5hash.append(hexString);
        }

        return hexMD5hash.toString();
    }

}
