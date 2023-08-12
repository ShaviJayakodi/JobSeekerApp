package com.jobseeker.JobSeekerApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consultant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long consultantId;
    private String regNo;private String title;
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
    private String description;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Job job;
    private int status;


}
