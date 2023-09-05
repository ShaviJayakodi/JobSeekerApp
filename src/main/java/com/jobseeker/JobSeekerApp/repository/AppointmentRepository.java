package com.jobseeker.JobSeekerApp.repository;

import com.jobseeker.JobSeekerApp.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    @Query(value = "SELECT * FROM appointment WHERE consultant_consultant_id =?2 AND DATE_FORMAT(appointment_date,'%Y/%m/%d') =DATE_FORMAT(?1,'%Y/%m/%d')" ,nativeQuery = true)
    List<Appointment> getAppointmentByConsultantAndDate(Date date, long consultantId);
}
