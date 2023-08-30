package com.jobseeker.JobSeekerApp.repository;

import com.jobseeker.JobSeekerApp.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin , Long> {

    @Query(value ="SELECT COALESCE(MAX(admin_id), 0) FROM admin",nativeQuery = true)
    int getNextAdminId();

}