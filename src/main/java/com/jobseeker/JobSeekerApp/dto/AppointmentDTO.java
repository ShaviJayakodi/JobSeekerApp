package com.jobseeker.JobSeekerApp.dto;

import com.jobseeker.JobSeekerApp.entity.Consultant;
import com.jobseeker.JobSeekerApp.entity.Job;
import com.jobseeker.JobSeekerApp.entity.JobSeeker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private long appointmentId;
    private String appointmentDes;
    private String appointmentTitle;
    private Date appointmentDate;
    private int status;
    private long consultantId;
    private long jobSeekerId;
    private long jobId;

}
