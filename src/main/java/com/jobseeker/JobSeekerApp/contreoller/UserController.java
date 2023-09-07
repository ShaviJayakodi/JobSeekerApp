package com.jobseeker.JobSeekerApp.contreoller;

import com.jobseeker.JobSeekerApp.dto.UserDTO;
import com.jobseeker.JobSeekerApp.entity.User;
import com.jobseeker.JobSeekerApp.service.UserService;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<CustomizedResponse> loginUser (@RequestBody User user)
    {
        return new ResponseEntity<CustomizedResponse>(userService.loginUser(user), HttpStatus.OK);
    }
    public ResponseEntity<CustomizedResponse> saveUser(@RequestBody UserDTO userDTO)
    {
        return null;
    }

    @PutMapping("/changePassword")
    public ResponseEntity<CustomizedResponse> changePassword (@RequestBody UserDTO userDTO)
    {
        return new ResponseEntity<CustomizedResponse>(userService.changePassword(userDTO),HttpStatus.OK);
    }

    @GetMapping("/checkEmailAddress")
    public ResponseEntity<CustomizedResponse> checkEmailAddress(String email)
    {
        return new ResponseEntity<CustomizedResponse>(userService.checkValidEmail(email),HttpStatus.OK);
    }

}
