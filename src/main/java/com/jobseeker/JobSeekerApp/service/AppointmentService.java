package com.jobseeker.JobSeekerApp.service;

import com.jobseeker.JobSeekerApp.entity.Appointment;
import com.jobseeker.JobSeekerApp.repository.AppointmentRepository;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ModelMapper modelMapper;


    public CustomizedResponse checkDate(Date date, long consultantId)
    {

        String dd = "2023-06-23";
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        LocalDate inputDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        try {
            LocalDate today = LocalDate.now();
            if(inputDate.isEqual(today) || inputDate.isAfter(today))
            {
                List<Appointment> appointmentList = modelMapper.map(appointmentRepository.getAppointmentByConsultantAndDate(date,consultantId),new TypeToken<List<Appointment>>(){}.getType());
                if(appointmentList.size()>=10)
                {
                    customizedResponse.setSuccess(false);
                    errorStatus.add("Date is full. Select another date.!");
                    customizedResponse.setStatusList(errorStatus);
                }
                else
                {
                    customizedResponse.setSuccess(true);
                }
            }
            else
            {
                customizedResponse.setSuccess(false);
                errorStatus.add("Please choose a future date or today's date. The selected date is not valid.");
                customizedResponse.setStatusList(errorStatus);
            }

        }
        catch (Exception exception)
        {
            errorStatus.add("Error => "+exception);
            customizedResponse.setSuccess(false);
            customizedResponse.setStatusList(errorStatus);
        }

        return customizedResponse;
    }
}
