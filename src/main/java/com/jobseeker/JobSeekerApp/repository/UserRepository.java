package com.jobseeker.JobSeekerApp.repository;

import com.jobseeker.JobSeekerApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "SELECT * FROM User WHERE user_name = ?1",nativeQuery = true)
    User checkEmail(String email);

    @Query(value = "SELECT * FROM User WHERE reg_no = ?1", nativeQuery = true)
    User getUserByRegNo(String regNo);

    @Query(value = "SELECT * FROM User WHERE user_name = ?1 AND Status = 1",nativeQuery = true)
    User getUserByUserName(String userName);
}
