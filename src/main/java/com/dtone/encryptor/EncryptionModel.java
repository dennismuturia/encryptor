package com.dtone.encryptor;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "encryption_model")
public class EncryptionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long licenceId;
    private String serverIp;
    private String activity;
    private String licenseGenerationDate;
    private String licenseExpiryDate;
    private String licenseLocation;
    private String active;
}
