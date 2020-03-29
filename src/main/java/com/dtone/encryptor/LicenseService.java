package com.dtone.encryptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@Service
public class LicenseService {
    @Value("${license_location}")
    private String locationForLicense;
    @Autowired
    LicenseSave licenseSave;

    private EncryptionModel encryptionModel;

    public boolean save(String ip, String months){
        if (licenseSave.existsServerIp(ip)){
            return false;
        }
        int mon = Integer.parseInt(months);
        //Calculate the date first
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, mon);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        encryptionModel.setServerIp(ip);
        encryptionModel.setLicenseVal("");
        encryptionModel.setLicenseLocation(locationForLicense);
        encryptionModel.setLicenseGenerationDate(sdf.format(new Date()));
        encryptionModel.setLicenseExpiryDate(sdf.format(calendar.getTime()));

        return true;
    }

}
