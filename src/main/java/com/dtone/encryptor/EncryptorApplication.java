package com.dtone.encryptor;

import it.sauronsoftware.cron4j.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EncryptorApplication implements CommandLineRunner {
    @Autowired
    CheckActivityCron checkActivityCron;
    //If the license is about to end then rerun and reproduce another one
    public static void main(String[] args) {
        SpringApplication.run(EncryptorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        checkActivityCron.updateLicenses();
        Scheduler s = new Scheduler();
        s.schedule("* * * * *", () -> {
            System.out.println("Check Activity");
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        s.start();
    }

}
