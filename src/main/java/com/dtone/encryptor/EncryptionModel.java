package com.dtone.encryptor;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
public class EncryptionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long licenceId;
    private String serverIp;
    private String licenseGenerationDate;
    private String licenseExpiryDate;
    private String licenseLocation;
}
