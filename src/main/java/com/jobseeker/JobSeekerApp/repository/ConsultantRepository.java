package com.jobseeker.JobSeekerApp.repository;

import com.jobseeker.JobSeekerApp.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant,Long> {

    @Query(value ="SELECT COALESCE(MAX(job_seeker_id), 0) + 1 FROM job_seeker",nativeQuery = true)
    int getNextConsultantId();

    @Query(value = "SELECT * FROM job_seeker WHERE status = 1" , nativeQuery = true)
    List<Consultant> getConsultantByStatus();
}
