package com.jobseeker.JobSeekerApp.contreoller;

import com.jobseeker.JobSeekerApp.dto.AdminDTO;
import com.jobseeker.JobSeekerApp.service.AdminService;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/saveAdmin")
    public ResponseEntity<CustomizedResponse> saveAdmin(@RequestBody AdminDTO adminDTO)
    {
        return new ResponseEntity<CustomizedResponse>(adminService.saveAdmin(adminDTO), HttpStatus.OK);
    }
}
