package com.jobseeker.JobSeekerApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {
    private long jobId;
    private String jobTitle;
    private int status;
}
