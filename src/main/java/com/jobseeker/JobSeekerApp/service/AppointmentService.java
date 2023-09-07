package com.jobseeker.JobSeekerApp.service;

import com.jobseeker.JobSeekerApp.dto.AppointmentDTO;
import com.jobseeker.JobSeekerApp.entity.Appointment;
import com.jobseeker.JobSeekerApp.enums.statusValue;
import com.jobseeker.JobSeekerApp.repository.AppointmentRepository;
import com.jobseeker.JobSeekerApp.repository.ConsultantRepository;
import com.jobseeker.JobSeekerApp.repository.JobRepository;
import com.jobseeker.JobSeekerApp.repository.JobSeekerRepository;
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

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private JobRepository jobRepository;

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

    public CustomizedResponse makeAnAppointment(AppointmentDTO appointmentDTO)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        Appointment appointment = new Appointment();
        try
        {
            appointment = modelMapper.map(appointmentDTO,Appointment.class);
            appointment.setConsultant(consultantRepository.getById(appointmentDTO.getConsultantId()));
            appointment.setJobSeeker(jobSeekerRepository.getById(appointmentDTO.getJobSeekerId()));
            appointment.setJob(jobRepository.getById(appointmentDTO.getJobId()));
            appointment.setStatus(statusValue.ACTIVE.sts());

            if(appointmentRepository.save(appointment)!=null)
            {
                customizedResponse.setSuccess(true);
                errorStatus.add("Appointment Added.!");
                customizedResponse.setStatusList(errorStatus);

            }
            else
            {
                errorStatus.add("Unsuccessful. Please try again!");
                customizedResponse.setSuccess(false);
                customizedResponse.setStatusList(errorStatus);
            }
        }
        catch (Exception exception)
        {
            errorStatus.add("Error => "+exception);
            customizedResponse.setStatusList(errorStatus);
            customizedResponse.setSuccess(false);
        }
        return customizedResponse;
    }

    public CustomizedResponse getAppointmentByJobSeekerId(long jobSeekerId)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try {
            List<Appointment> appointmentList = modelMapper.map(appointmentRepository.getAppointmentByJobSeekerId(jobSeekerId),new TypeToken<List<Appointment>>(){}.getType());
            if(appointmentList.size()>0)
            {
                customizedResponse.setResponse(appointmentList);
                customizedResponse.setSuccess(true);
            }
            else
            {
                errorStatus.add("No any appointments.!");
                customizedResponse.setSuccess(false);
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

    public CustomizedResponse getAppointmentByConsultantId(long consultantId)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try {
            List<Appointment> appointmentList = modelMapper.map(appointmentRepository.getAppointmentByConsultantId(consultantId),new TypeToken<List<Appointment>>(){}.getType());
            if(appointmentList.size()>0)
            {
                customizedResponse.setResponse(appointmentList);
                customizedResponse.setSuccess(true);
            }
            else
            {
                errorStatus.add("No any appointments.!");
                customizedResponse.setSuccess(false);
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

    public CustomizedResponse deleteAppointment(long appointmentId)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try {
            if(appointmentRepository.existsById(appointmentId))
            {
                Appointment appointment = appointmentRepository.getById(appointmentId);
                appointment.setStatus(statusValue.DEACTIVE.sts());

                if(appointmentRepository.save(appointment)!=null)
                {
                    errorStatus.add("Successfully deleted!");
                    customizedResponse.setStatusList(errorStatus);
                    customizedResponse.setSuccess(true);
                }
                else
                {
                    errorStatus.add("Delete Error. Please try again.!");
                    customizedResponse.setStatusList(errorStatus);
                    customizedResponse.setSuccess(false);
                }
            }
            else
            {
                errorStatus.add("Not Found.!");
                customizedResponse.setStatusList(errorStatus);
                customizedResponse.setSuccess(false);
            }
        }
        catch (Exception exception)
        {
            errorStatus.add("Error => "+exception);
            customizedResponse.setStatusList(errorStatus);
            customizedResponse.setSuccess(false);
        }
        return customizedResponse;
    }

    public CustomizedResponse getAllPendingAppointments()
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try {
            List<Appointment> appointmentList = modelMapper.map(appointmentRepository.getAllPendingAppointments(),new TypeToken<List<Appointment>>(){}.getType());
            if(appointmentList.size()>0)
            {
                customizedResponse.setSuccess(true);
                customizedResponse.setResponse(appointmentList);
            }
            else
            {
                errorStatus.add("No any pending appointments.!");
                customizedResponse.setStatusList(errorStatus);
                customizedResponse.setSuccess(false);
            }
        }
        catch (Exception exception)
        {
            errorStatus.add("Error => "+exception);
            customizedResponse.setStatusList(errorStatus);
            customizedResponse.setSuccess(false);
        }


        return customizedResponse;
    }


    //todo
    /*public CustomizedResponse updateAppointment(long appointmentId)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try {
            if(appointmentRepository.existsById(appointmentId))
            {
                Appointment appointment = appointmentRepository.getById(appointmentId);
                appointment.setStatus(statusValue.DEACTIVE.sts());

                if(appointmentRepository.save(appointment)!=null)
                {
                    errorStatus.add("Successfully deleted!");
                    customizedResponse.setStatusList(errorStatus);
                    customizedResponse.setSuccess(true);
                }
                else
                {
                    errorStatus.add("Delete Error. Please try again.!");
                    customizedResponse.setStatusList(errorStatus);
                    customizedResponse.setSuccess(false);
                }
            }
            else
            {
                errorStatus.add("Not Found.!");
                customizedResponse.setStatusList(errorStatus);
                customizedResponse.setSuccess(false);
            }

        }
        catch (Exception exception)
        {
            errorStatus.add("Error => "+exception);
            customizedResponse.setStatusList(errorStatus);
            customizedResponse.setSuccess(false);
        }
        return customizedResponse;
    }*/



}
