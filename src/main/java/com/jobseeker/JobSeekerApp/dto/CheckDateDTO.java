package com.jobseeker.JobSeekerApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckDateDTO {
    private Date date;
    private long consultantId;
}
