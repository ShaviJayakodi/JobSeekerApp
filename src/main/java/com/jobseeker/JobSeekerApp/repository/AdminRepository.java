package com.jobseeker.JobSeekerApp.repository;

import com.jobseeker.JobSeekerApp.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.xml.bind.ValidationEvent;
import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin , Long> {

    @Query(value ="SELECT COALESCE(MAX(admin_id), 0) FROM admin",nativeQuery = true)
    int getNextAdminId();

    @Query(value = "SELECT * FROM admin WHERE reg_no = ?1 AND status = 1", nativeQuery = true)
    Admin getAdminByRegNo(String regNo);

    @Query(value = "SELECT * FROM admin WHERE status = 1" , nativeQuery = true)
    List<Admin> getAllAdmin();



}
