package com.jobseeker.JobSeekerApp.service;

import com.jobseeker.JobSeekerApp.dto.ConsultantDTO;
import com.jobseeker.JobSeekerApp.entity.Consultant;
import com.jobseeker.JobSeekerApp.enums.stakeHolderValues;
import com.jobseeker.JobSeekerApp.enums.statusValue;
import com.jobseeker.JobSeekerApp.repository.ConsultantRepository;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultantService {

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ModelMapper modelMapper;

    public CustomizedResponse saveConsultant(ConsultantDTO consultantDTO)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        Consultant consultant = new Consultant();
        int nextId = consultantRepository.getNextConsultantId();
        try {
            consultant = modelMapper.map(consultantDTO, Consultant.class);
            String regNo = commonService.generateRegNo(nextId, stakeHolderValues.CONSULTANT.code());
            consultant.setRegNo(regNo);
            consultant.setStatus(statusValue.ACTIVE.sts());
            if(consultantRepository.save(consultant)!= null)
            {
                errorStatus.add("Consultant Registered Successfully.!");
                customizedResponse.setStatusList(errorStatus);
                customizedResponse.setSuccess(true);
                customizedResponse.setResponse(consultant);
            }
            else
            {
                errorStatus.add("Registration Unsuccessful.!");
                customizedResponse.setStatusList(errorStatus);
                customizedResponse.setSuccess(false);
            }
        }
        catch (Exception exception)
        {
            errorStatus.add("Error -> "+exception);
            customizedResponse.setSuccess(false);
            customizedResponse.setStatusList(errorStatus);
        }

        return customizedResponse;
    }

    public CustomizedResponse getAllConsultant()
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try {
            List<Consultant> consultantList = modelMapper.map(consultantRepository.getConsultantByStatus(), new TypeToken<List<Consultant>>(){}.getType());
            if(consultantList!=null && consultantList.size()>0)
            {
                errorStatus.add("Data Found.!");
                customizedResponse.setStatusList(errorStatus);
                customizedResponse.setResponse(consultantList);
                customizedResponse.setSuccess(true);
            }
            else
            {
                errorStatus.add("Data Not Found.!");
                customizedResponse.setSuccess(false);
                customizedResponse.setStatusList(errorStatus);
            }
        }
        catch (Exception exception)
        {
            errorStatus.add("Error -> "+exception);
            customizedResponse.setSuccess(false);
            customizedResponse.setStatusList(errorStatus);
        }

        return customizedResponse;
    }


}
