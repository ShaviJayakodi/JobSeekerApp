package com.jobseeker.JobSeekerApp.repository;

import com.jobseeker.JobSeekerApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "SELECT * FROM User WHERE userName = ?1",nativeQuery = true)
    User checkEmail(String email);

    @Query(value = "SELECT * FROM User WHERE regNo = ?1", nativeQuery = true)
    User getUserByRegNo(String regNo);
}
