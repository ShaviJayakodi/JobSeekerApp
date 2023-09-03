package com.jobseeker.JobSeekerApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerDTO {
    private long jobSeekerId;
    private String regNo;
    private String title;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dob;
    private String streetName;
    private String city;
    private String country;
    private String civilStatus;
    private String tel1;
    private String tel2;
    private String email;
    private String password;
    private int status;
}
