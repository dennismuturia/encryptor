package com.dtone.encryptor;

import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;


public class GenKeyStore {
    @Value("${keystore_location}")
    private static String keyToolLocation;


    public static KeyPair getKeyPairFromKeyStore() throws Exception {
        //Make the keystore be accessed in another part of the file.
        InputStream ins = GenKeyStore.class.getResourceAsStream("/keystore.jks");
        //InputStream ins = GenKeyStore.class.getResourceAsStream(keyToolLocation+"keystore.jks");
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        keyStore.load(ins, "s3cr3t".toCharArray());
        KeyStore.PasswordProtection keyPassword =
                new KeyStore.PasswordProtection("s3cr3t".toCharArray());

        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("myKey", keyPassword);

        java.security.cert.Certificate cert = keyStore.getCertificate("myKey");
        PublicKey publicKey = cert.getPublicKey();
        PrivateKey privateKey = privateKeyEntry.getPrivateKey();

        return new KeyPair(publicKey, privateKey);
    }
}
