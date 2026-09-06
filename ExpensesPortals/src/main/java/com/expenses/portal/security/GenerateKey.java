package com.expenses.portal.security;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

public class GenerateKey {
    public static void main(String[] args) {

        SecretKey key = Jwts.SIG.HS256.key().build();

        String secret = Encoders.BASE64.encode(key.getEncoded());

        System.out.println("JWT Secret Key:");
        System.out.println(secret);
    }
}