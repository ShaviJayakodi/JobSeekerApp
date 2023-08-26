package com.jobseeker.JobSeekerApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
    public long adminId;
    public String regNo;
    public String firstName;
    public String lastName;
    public String email;
    public String passWord;
    public int isFirstLogin;
    public int status;
}
