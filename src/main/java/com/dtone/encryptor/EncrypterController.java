package com.dtone.encryptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Calendar;
import java.util.Date;

@Controller
public class EncrypterController {
    @Value("${license_location}")
    private String locationForLicense;

    @Autowired
    private LicenseSave licenseSave;
    @GetMapping("/")
    public String showAvailableLicenses(Model model){
        model.addAttribute("availableLicense", new EncryptionModel());
        return "index";
    }


    @PostMapping("/")
    public String saveLicense(@ModelAttribute EncryptionModel encryptionModel){
        encryptionModel.setLicenseGenerationDate(new Date());
        encryptionModel.setLicenseLocation(locationForLicense);
        encryptionModel.setLicenseExpiryDate(new Date(2));
        encryptionModel.setLicenseVal("x");
        //First check if this key exists
        if(encryptionModel.getServerIp().equals(licenseSave.existsServerIp(encryptionModel.getServerIp()))){
            //Show that the license for this server already exists
                System.out.println("This IP exixts");

        }else {

            licenseSave.save(encryptionModel);
        }
        return "index";
    }
    /*
    @GetMapping("/")
    public String showItem(Model model){
        model.addAttribute("addIp", new LicenseDTO());
        return "index";
    }


     */
}
