package com.jobseeker.JobSeekerApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long appointmentId;
    private String appointmentDes;
    private String appointmentTitle;
    private Date appointmentDate;
    private int status;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Consultant consultant;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private JobSeeker jobSeeker;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Job job;


}
