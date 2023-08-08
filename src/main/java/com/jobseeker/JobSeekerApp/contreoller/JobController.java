package com.jobseeker.JobSeekerApp.contreoller;

import com.jobseeker.JobSeekerApp.dto.JobDTO;
import com.jobseeker.JobSeekerApp.entity.Job;
import com.jobseeker.JobSeekerApp.service.JobService;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("jobController")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/saveJob")
    public ResponseEntity<CustomizedResponse>   saveJob (@RequestBody JobDTO jobDTO)
    {
        return new ResponseEntity<CustomizedResponse>(jobService.saveJob(jobDTO),HttpStatus.OK);
    }

    @GetMapping("/getAllJobs")
    public ResponseEntity<CustomizedResponse> getAllJobs()
    {
        return new ResponseEntity<CustomizedResponse>(jobService.getAllJobs(),HttpStatus.OK);
    }

    @GetMapping("/getJobById")
    public ResponseEntity<CustomizedResponse> getJobById(@RequestParam long jobId)
    {
        return new ResponseEntity<CustomizedResponse>(jobService.getJobyId(jobId),HttpStatus.OK);
    }

    @PutMapping("/updateJob")
    public ResponseEntity<CustomizedResponse> updateJob(@RequestBody Job job)
    {
        return new ResponseEntity<CustomizedResponse>(jobService.updateJob(job),HttpStatus.OK);
    }

    @PutMapping("/deleteJob")
    public ResponseEntity<CustomizedResponse> deleteJobById(@RequestParam long jobId)
    {
        return new ResponseEntity<CustomizedResponse>(jobService.deleteJobById(jobId),HttpStatus.OK);
    }

}
