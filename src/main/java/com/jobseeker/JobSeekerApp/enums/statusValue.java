package com.jobseeker.JobSeekerApp.enums;

public enum statusValue {
    ACTIVE(1),
    PENDING(2),
    DEACTIVE (0),

    COMPLETE (3);

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
