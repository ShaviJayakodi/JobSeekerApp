package com.jobseeker.JobSeekerApp.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public enum statusValue {
    ACTIVE(1),
    DEACTIVE(0);
    private int sts;


    statusValue(int sts)
    {
        this.sts=sts;

    }
    public int sts()
    {
        return sts;
    }
}
