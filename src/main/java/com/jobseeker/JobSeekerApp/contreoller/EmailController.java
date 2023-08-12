package com.jobseeker.JobSeekerApp.contreoller;

import com.jobseeker.JobSeekerApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/mail")
public class EmailController {
    @Autowired
    private EmailService emailService;
}
