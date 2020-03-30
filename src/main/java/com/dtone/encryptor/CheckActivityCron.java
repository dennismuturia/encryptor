package com.dtone.encryptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;
@Service
public class CheckActivityCron {

    @Value("${license_location}")
    private String locationForLicense;

    @Autowired
    private LicenseService licenseService;


     void updateLicenses() {

       licenseService.getAll().forEach(x -> {
           LocalDate startDate= LocalDate.parse(x.getLicenseGenerationDate());
           LocalDate endDate= LocalDate.parse(x.getLicenseExpiryDate());

           if (DAYS.between(startDate, endDate) < 0 && !x.getActivity().equals("on")){
               x.setActive("EXPIRED");
           }else if(DAYS.between(startDate, endDate) < 0 && x.getActivity().equals("on")){
               //Auto generate a new license
               Calendar startCalendar = new GregorianCalendar();
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
               try {
                   startCalendar.setTime(sdf.parse(x.getLicenseGenerationDate()));
               } catch (ParseException e) {
                   e.printStackTrace();
               }
               Calendar endCalendar = new GregorianCalendar();
               try {
                   endCalendar.setTime(sdf.parse(x.getLicenseExpiryDate()));
               } catch (ParseException e) {
                   e.printStackTrace();
               }

               int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
               int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

               Calendar cal = new GregorianCalendar();
               cal.add(Calendar.MONTH, diffMonth);
               x.setLicenseGenerationDate(sdf.format(new Date()));
               x.setLicenseExpiryDate(sdf.format(cal.getTime()));




               GetIpandMac getIpandMac = new GetIpandMac();

               //Write to file
               File file = new File(locationForLicense+"license.lsc" + sdf.format(new Date()));
               FileOutputStream fileOutputStream = null;
               try {
                   fileOutputStream = new FileOutputStream(file);
               } catch (FileNotFoundException e) {
                   e.printStackTrace();
               }
               assert fileOutputStream != null;
               BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
               try {
                   bw.write(Encrypt.encrypt(getIpandMac.getClientMACAddress(), GenKeyStore.getKeyPairFromKeyStore().getPrivate(), sdf.format(new Date()), sdf.format(cal.getTime())));
               } catch (Exception e) {
                   e.printStackTrace();
               }
               try {
                   bw.newLine();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               try {
                   bw.write(Base64.getEncoder().encodeToString(GenKeyStore.getKeyPairFromKeyStore().getPublic().getEncoded()));
               } catch (Exception e) {
                   e.printStackTrace();
               }
               try {
                   bw.flush();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               try {
                   bw.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       });



    }
}
