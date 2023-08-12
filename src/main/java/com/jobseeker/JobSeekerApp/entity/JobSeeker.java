package com.jobseeker.JobSeekerApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSeeker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private int status;
}
