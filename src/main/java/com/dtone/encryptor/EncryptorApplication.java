package com.dtone.encryptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EncryptorApplication {
    /*This will be running after every hour checking the validity of a produced license */
    public static void main(String[] args) {
        SpringApplication.run(EncryptorApplication.class, args);
    }

}
