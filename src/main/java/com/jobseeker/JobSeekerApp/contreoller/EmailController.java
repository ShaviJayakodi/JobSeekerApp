package com.jobseeker.JobSeekerApp.contreoller;

import com.jobseeker.JobSeekerApp.dto.MailRegisterDTO;
import com.jobseeker.JobSeekerApp.service.EmailService;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/mail")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/registerMail")
    public ResponseEntity<CustomizedResponse> registerMail(@RequestBody MailRegisterDTO mailRegisterDTO)
    {
        return new ResponseEntity<CustomizedResponse>(emailService.registerMail(mailRegisterDTO), HttpStatus.OK);
    }

}
