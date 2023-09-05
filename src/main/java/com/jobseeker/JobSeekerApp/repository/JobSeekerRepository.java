package com.jobseeker.JobSeekerApp.repository;

import com.jobseeker.JobSeekerApp.entity.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker , Long> {

    @Query(value = "SELECT * FROM job_seeker WHERE status = 1" , nativeQuery = true)
    List<JobSeeker> getAllJobSeekerByStatus();

    @Query(value ="SELECT COALESCE(MAX(job_seeker_id), 0) FROM job_seeker",nativeQuery = true)
    int getNextJobSeekerId();


    @Query(value = "SELECT * FROM job_seeker WHERE reg_no = ?1 AND status = 1" , nativeQuery = true)
    JobSeeker getJobSeekerByRegNo(String regNo);


}
