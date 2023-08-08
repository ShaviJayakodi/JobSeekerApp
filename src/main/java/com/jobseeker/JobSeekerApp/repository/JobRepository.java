package com.jobseeker.JobSeekerApp.repository;

import com.jobseeker.JobSeekerApp.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository <Job , Long> {
    @Query(value = "SELECT * FROM Job WHERE status = 1",nativeQuery = true)
    public List<Job> getJobByStatus();

    @Query(value = "SELECT * FROM Job WHERE jobId= ?1 AND status = 1",nativeQuery = true)
    public Job getJobByJobId(long jobId);
}
