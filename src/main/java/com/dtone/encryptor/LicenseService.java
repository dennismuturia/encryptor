package com.dtone.encryptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@Service
public class LicenseService {
    @Value("${license_location}")
    private String locationForLicense;
    @Autowired
    private LicenseSave licenseSave;



    public boolean save(String ip, String months) throws Exception {
        EncryptionModel encryptionModel = new EncryptionModel();
        if (licenseSave.existsServerIp(ip)){
            return false;
        }
        int mon = Integer.parseInt(months);
        //Calculate the date first
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, mon);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        encryptionModel.setServerIp(ip);
        encryptionModel.setLicenseLocation(locationForLicense);
        encryptionModel.setLicenseGenerationDate(sdf.format(new Date()));
        encryptionModel.setLicenseExpiryDate(sdf.format(calendar.getTime()));
        //Generate license
        GenKeyStore genKeyStore = new GenKeyStore();

        Encrypt encrypt = new Encrypt();
        GetIpandMac getIpandMac = new GetIpandMac();

        //Write to file
        File file = new File(locationForLicense+"license.lsc" + sdf.format(new Date()));
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        bw.write(encrypt.encrypt(getIpandMac.getClientMACAddress(), genKeyStore.getKeyPairFromKeyStore().getPrivate(), sdf.format(new Date()), sdf.format(calendar.getTime())));
        bw.newLine();
        bw.write(Base64.getEncoder().encodeToString(genKeyStore.getKeyPairFromKeyStore().getPublic().getEncoded()));
        bw.flush();
        bw.close();
        //End of writing to file

        licenseSave.save(encryptionModel);
        return true;
    }

}
