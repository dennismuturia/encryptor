package com.dtone.encryptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class LicenseService {

    @Value("${license_location}")
    private String locationForLicense;
    private final LicenseSave licenseSave;

    public LicenseService(LicenseSave licenseSave) {
        this.licenseSave = licenseSave;
    }

    boolean save1(String ip, String months, String autoGen) throws Exception {
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
        encryptionModel.setActivity(autoGen);
        encryptionModel.setLicenseGenerationDate(sdf.format(new Date()));
        encryptionModel.setLicenseExpiryDate(sdf.format(calendar.getTime()));
        encryptionModel.setActive("ACTIVE");
        //Generate license
        GetIpandMac getIpandMac = new GetIpandMac();

        //Write to file
        File file = new File(locationForLicense+"license.lsc");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        bw.write(Encrypt.encrypt(getIpandMac.getClientMACAddress(), GenKeyStore.getKeyPairFromKeyStore().getPrivate(), sdf.format(new Date()), sdf.format(calendar.getTime())));
        bw.newLine();
        bw.write(Base64.getEncoder().encodeToString(GenKeyStore.getKeyPairFromKeyStore().getPublic().getEncoded()));
        bw.flush();
        bw.close();
        //End of writing to file

        licenseSave.save(encryptionModel);
        return true;
    }

    public void updateLicense(Date generationDate, Date ExpiryDate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        GetIpandMac getIpandMac = new GetIpandMac();
        //Write to file
        File file = new File(locationForLicense+"license.lsc");
        FileOutputStream fileOutputStream = new FileOutputStream(file);



        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        bw.write(Encrypt.encrypt(getIpandMac.getClientMACAddress(), GenKeyStore.getKeyPairFromKeyStore().getPrivate(), sdf.format(generationDate), sdf.format(ExpiryDate)));
        bw.newLine();
        bw.write(Base64.getEncoder().encodeToString(GenKeyStore.getKeyPairFromKeyStore().getPublic().getEncoded()));
        bw.flush();
        bw.close();
    }

    public List<EncryptionModel> getAll(){
        return licenseSave.findAll();
    }

    public Optional<EncryptionModel> getById(long id){
        return licenseSave.findById(id);
    }

}
