package com.dtone.encryptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;



@Controller
public class EncrypterController {
    @Value("${license_location}")
    private String locationForLicense;

    @Autowired
    private LicenseService licenseService;

    @Autowired
    private LicenseSave licenseSave;

    @GetMapping("/")
    public String showAvailableLicenses(Model model){
        model.addAttribute("availableLicenses", licenseSave.findAll());
        return "index";
    }

    @PostMapping("/")
    public String saveLicense(HttpServletRequest request, Model model) throws Exception {
        String ip = request.getParameter("ipServer");
        String numMonths = request.getParameter("numberOfMonths");
        String autoGen = request.getParameter("switchActive");
        if(licenseService.save1(ip, numMonths, autoGen)){
            System.out.println("License saved");
        }else{
            System.out.println("Not saved");
        }

        model.addAttribute("availableLicenses", licenseSave.findAll());

        return "index";
    }

    @GetMapping("/specific/{licenceId}")
    public String showSpecificLicense(@PathVariable(value = "licenceId") long licenceId, Model model){
        model.addAttribute("specificLicense", licenseService.getById(licenceId));
        return "specific";
    }

    @PostMapping("/specific/{licenceId}")
    public String updateLicense(@PathVariable("licenceId") long licenceId, @Valid EncryptionModel encryptionModel, BindingResult result,
                                Model model) throws Exception {
        if(result.hasErrors()){
            encryptionModel.setLicenceId(licenceId);
            return "specific";
        }
        //Generate a new License


       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // you can change format of date
        Date date = formatter.parse(encryptionModel.getLicenseGenerationDate());
        Date date1= formatter.parse(encryptionModel.getLicenseExpiryDate().toString());
        if(date.getTime() > date1.getTime()){
            System.out.println("Error generation date is larger that expiry date");
        }else{
            if (new Date().getTime() < date1.getTime()){
                encryptionModel.setActive("ACTIVE");
            }else{
                encryptionModel.setActive("EXPIRED");
            }
            licenseService.updateLicense(date, date1);
            licenseSave.save(encryptionModel);
        }


        //Generate a new license
        model.addAttribute("specificLicense", licenseService.getById(licenceId));
        return "specific";
    }
}
