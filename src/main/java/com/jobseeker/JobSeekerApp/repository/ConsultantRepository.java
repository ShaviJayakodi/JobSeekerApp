package com.jobseeker.JobSeekerApp.repository;

import com.jobseeker.JobSeekerApp.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant,Long> {

    @Query(value ="SELECT COALESCE(MAX(consultant_id), 0) FROM consultant",nativeQuery = true)
    int getNextConsultantId();

    @Query(value = "SELECT * FROM consultant WHERE status = 1" , nativeQuery = true)
    List<Consultant> getConsultantByStatus();

    @Query(value = "SELECT * FROM consultant WHERE status = ?2 AND consultant_id=?1", nativeQuery = true)
    Consultant getConsultantByConsultantIdAndStatus(long id, int status);

    @Query(value = "SELECT * FROM consultant WHERE status = 1 AND reg_No=?1", nativeQuery = true)
    Consultant getConsultantByConsultantIdAndStatus(String regNo);
}
