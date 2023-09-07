package com.jobseeker.JobSeekerApp.contreoller;

import com.jobseeker.JobSeekerApp.dto.JobSeekerDTO;
import com.jobseeker.JobSeekerApp.service.JobSeekerService;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/jobSeekers")
public class JobSeekerController {

    @Autowired
    private JobSeekerService jobSeekerService;


    @PostMapping("/saveJobSeeker")
    public ResponseEntity<CustomizedResponse> saveJobSeeker(@RequestBody JobSeekerDTO jobSeekerDTO){
        return new ResponseEntity<CustomizedResponse>(jobSeekerService.saveJobSeeker(jobSeekerDTO), HttpStatus.OK);
    }

    @GetMapping("/getAllSeekers")
    public ResponseEntity<CustomizedResponse> getAllJobSeekers ()
    {
        return new ResponseEntity<CustomizedResponse>(jobSeekerService.getAllJobSeekers(), HttpStatus.OK);
    }

    @PutMapping("updateJobSeeker")
    public ResponseEntity<CustomizedResponse> updateJobSeeker(@RequestBody JobSeekerDTO jobSeekerDTO)
    {
        return new ResponseEntity<CustomizedResponse>(jobSeekerService.updateJobSeeker(jobSeekerDTO),HttpStatus.OK);
    }

    @DeleteMapping("deleteJobSeeker")
    public ResponseEntity<CustomizedResponse> deleteJobSeeker(@RequestParam String regNo)
    {
        return new ResponseEntity<CustomizedResponse>(jobSeekerService.deleteJobSeeker(regNo),HttpStatus.OK);
    }

    @GetMapping("/getJobSeekerByRegNo")
    public ResponseEntity<CustomizedResponse> getJobSeekerByRegNo(@RequestParam String regNo)
    {
        return new ResponseEntity<CustomizedResponse>(jobSeekerService.getJobSeekerByRegNo(regNo),HttpStatus.OK);
    }


}
