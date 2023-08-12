package com.jobseeker.JobSeekerApp.service;

import org.springframework.stereotype.Service;


import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class CommonService {

    public String generateRegNo(int nextId, String code) {
        String regNo = "";
        LocalDateTime currantDate = LocalDateTime.now();
        LocalDate date1 = currantDate.toLocalDate();
        int year1 = date1.getYear();
        int year = Integer.parseInt((Integer.toString(year1)).substring(2, 4));
        String pattern = "0000";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String format = decimalFormat.format(nextId + 1);
        regNo = Integer.toString(year) + code + format;
        return regNo;
    }
}
