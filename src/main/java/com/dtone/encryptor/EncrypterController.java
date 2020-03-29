package com.dtone.encryptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class EncrypterController {
    @Value("${license_location}")
    private String locationForLicense;

    @Autowired
    private LicenseService licenseService;
    @GetMapping("/")
    public String showAvailableLicenses(Model model){
        model.addAttribute("availableLicense", new EncryptionModel());
        return "index";
    }


    @PostMapping("/")
    public String saveLicense(HttpServletRequest request, Model model){
        String ip = request.getParameter("ipServer");
        String numMonths = request.getParameter("numberOfMonths");
        if(licenseService.save(ip, numMonths)){
            System.out.println("License saved");
        }else{
            System.out.println("Not saved");
        }

        return "index";
    }
}
