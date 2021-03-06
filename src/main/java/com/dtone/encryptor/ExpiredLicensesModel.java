package com.dtone.encryptor;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class ExpiredLicensesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long licenceId;
    private String licenseVal;
    private String serverIp;
    private Date licenseExpiryDate;
    private String licenseLocation;
}
