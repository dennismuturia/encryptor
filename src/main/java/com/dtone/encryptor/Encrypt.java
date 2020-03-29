package com.dtone.encryptor;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Encrypt {
    public static String encrypt(String plainText, PrivateKey privateKey, String now, String expiry) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, privateKey);
        String x = plainText + " "+ now + " "+ expiry;
        byte[] cipherText = encryptCipher.doFinal(x.getBytes(UTF_8));

        return Base64.getEncoder().encodeToString(cipherText);
    }
}
