package com.jobseeker.JobSeekerApp.contreoller;

import com.jobseeker.JobSeekerApp.dto.ConsultantDTO;
import com.jobseeker.JobSeekerApp.service.ConsultantService;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.util.resources.cldr.chr.CalendarData_chr_US;

@RestController
@CrossOrigin
@RequestMapping("/consultant")
public class ConsultantController {


    @Autowired
    private ConsultantService consultantService;

    @GetMapping("/getAllConsultants")
    public ResponseEntity<CustomizedResponse> getAllConsultants()
    {
        return new ResponseEntity<CustomizedResponse>(consultantService.getAllConsultant(), HttpStatus.OK);
    }

    @PostMapping("/saveConsultant")
    public ResponseEntity<CustomizedResponse> saveConsultant(@RequestBody ConsultantDTO consultantDTO)
    {
        return new ResponseEntity<CustomizedResponse>(consultantService.saveConsultant(consultantDTO),HttpStatus.OK);
    }
}
