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

    @GetMapping("/getAdminByRegNo")
    public ResponseEntity<CustomizedResponse> getAdminByRegNo(@RequestParam String regNo)
    {
        return new ResponseEntity<CustomizedResponse>(adminService.getAdminByRegNo(regNo),HttpStatus.OK);
    }

    @GetMapping("/getAllAdmin")
    public ResponseEntity<CustomizedResponse> getAllAdmin()
    {
        return new ResponseEntity<CustomizedResponse>(adminService.getAllAdmin(),HttpStatus.OK);
    }

    @PutMapping("/updateAdmin")
    public ResponseEntity<CustomizedResponse> updateAdmin(@RequestBody AdminDTO adminDTO)
    {
        return new ResponseEntity<CustomizedResponse>(adminService.updateAdmin(adminDTO),HttpStatus.OK);
    }

    @PutMapping("/deleteAdmin")
    public ResponseEntity<CustomizedResponse> deleteAdmin(@RequestParam String regNo)
    {
        return new ResponseEntity<CustomizedResponse>(adminService.deleteAdmin(regNo),HttpStatus.OK);
    }




}
