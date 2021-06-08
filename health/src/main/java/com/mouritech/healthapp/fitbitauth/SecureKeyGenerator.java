package com.mouritech.healthapp.fitbitauth;


import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;



public class SecureKeyGenerator {

    public static void main(String[] args) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();
//            System.out.println(Base64.encodeBase64String(secretKey.getEncoded()));
//            System.out.println(Base64.encodeBase64String(secretKey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
