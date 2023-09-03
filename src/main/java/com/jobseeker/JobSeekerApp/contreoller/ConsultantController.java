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

    @GetMapping("/getAllConsultant")
    public ResponseEntity<CustomizedResponse> getAllConsultants()
    {
        return new ResponseEntity<CustomizedResponse>(consultantService.getAllConsultant(), HttpStatus.OK);
    }

    @PostMapping("/saveConsultantReq")
    public ResponseEntity<CustomizedResponse> saveConsultant(@RequestBody ConsultantDTO consultantDTO)
    {
        return new ResponseEntity<CustomizedResponse>(consultantService.saveConsultant(consultantDTO),HttpStatus.OK);
    }

    @GetMapping("/getConsultantById")
    public ResponseEntity <CustomizedResponse> getConsultantById (@RequestParam  long consultantId)
    {
        return new ResponseEntity<CustomizedResponse>(consultantService.getConsultantById(consultantId),HttpStatus.OK);
    }

    @PostMapping("/approve")
    public ResponseEntity<CustomizedResponse> consultantApproval(@RequestBody ConsultantDTO consultantDTO)
    {
        String type = "Approve";
        return new ResponseEntity<CustomizedResponse>(consultantService.consultantApproval(consultantDTO.getConsultantId(), type),HttpStatus.OK);
    }

    @PostMapping("/reject")
    public ResponseEntity<CustomizedResponse> consultantReject(@RequestBody ConsultantDTO consultantDTO)
    {
        String type = "Reject";
        return new ResponseEntity<CustomizedResponse>(consultantService.consultantApproval(consultantDTO.getConsultantId(), type),HttpStatus.OK);
    }

    @GetMapping("/getConsultantByJobId")
    public ResponseEntity<CustomizedResponse> getConsultantByJobId(@RequestParam long jobId)
    {
        return new ResponseEntity<CustomizedResponse>(consultantService.getConsultantByJobId(jobId),HttpStatus.OK);
    }

    @GetMapping("/getPendingApprovals")
    public ResponseEntity<CustomizedResponse> getPendingApprovals ()
    {
        return new ResponseEntity<CustomizedResponse>(consultantService.getPendingApprovals(),HttpStatus.OK);
    }

    @GetMapping("/getConsultantByRegNo")
    public ResponseEntity<CustomizedResponse> getConsultantByRegNo(@RequestParam String regNo)
    {
        return new ResponseEntity<CustomizedResponse>(consultantService.getConsultantByRegNo(regNo),HttpStatus.OK);
    }
}
