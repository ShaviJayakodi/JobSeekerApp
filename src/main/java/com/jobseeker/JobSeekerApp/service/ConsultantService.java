package com.jobseeker.JobSeekerApp.service;

import com.jobseeker.JobSeekerApp.dto.ConsultantDTO;
import com.jobseeker.JobSeekerApp.dto.UserDTO;
import com.jobseeker.JobSeekerApp.entity.Consultant;
import com.jobseeker.JobSeekerApp.entity.Job;
import com.jobseeker.JobSeekerApp.entity.User;
import com.jobseeker.JobSeekerApp.enums.stakeHolderValues;
import com.jobseeker.JobSeekerApp.enums.statusValue;
import com.jobseeker.JobSeekerApp.repository.ConsultantRepository;
import com.jobseeker.JobSeekerApp.repository.JobRepository;
import com.jobseeker.JobSeekerApp.repository.UserRepository;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private UserService userService;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    public CustomizedResponse saveConsultant(ConsultantDTO consultantDTO)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        Consultant consultant = new Consultant();
        User user = new User();
        Job job = new Job();
        int nextId = consultantRepository.getNextConsultantId();
        try {

            job = modelMapper.map(jobRepository.findById(consultantDTO.getJobId()).get(),Job.class);
            consultant = modelMapper.map(consultantDTO, Consultant.class);
            String regNo = commonService.generateRegNo(nextId, stakeHolderValues.CONSULTANT.code());
            consultant.setRegNo(regNo);
            consultant.setJob(job);

            consultant.setStatus(statusValue.PENDING.sts());

            if(consultantRepository.save(consultant)!= null)
            {
                user.setUserName(consultantDTO.getEmail());
                user.setRegNo(regNo);
                user.setUserType("CONSULTANT");
                user.setPassword(consultantDTO.getPassword());
                user.setStatus(statusValue.PENDING.sts());

                if(userService.saveUser(user).isSuccess()==true)
                {
                    errorStatus.add("Consultant Registration Under the Approval!");
                    customizedResponse.setStatusList(errorStatus);
                    customizedResponse.setSuccess(true);
                    customizedResponse.setResponse(consultant);
                }
                else
                {
                    consultantRepository.deleteById(consultant.getConsultantId());
                    errorStatus.add("Registration Unsuccessful.!");
                    customizedResponse.setStatusList(errorStatus);
                    customizedResponse.setSuccess(false);
                }

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

    public CustomizedResponse getConsultantById(long consultantId)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        Consultant consultant = new Consultant();
        try {
            if(consultantRepository.existsById(consultantId))
            {
                customizedResponse.setResponse(consultantRepository.getConsultantByConsultantIdAndStatus(consultantId,statusValue.ACTIVE.sts()));
                errorStatus.add("Record Found.!");
                customizedResponse.setSuccess(true);
                customizedResponse.setStatusList(errorStatus);
            }
            else
            {
                errorStatus.add("Record Not Found.!");
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

    public CustomizedResponse getConsultantByRegNo(String regNo)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        Consultant consultant = new Consultant();
        try {
            if(consultantRepository.getConsultantByConsultantIdAndStatus(regNo)!=null)
            {
                customizedResponse.setResponse(consultantRepository.getConsultantByConsultantIdAndStatus(regNo));
                errorStatus.add("Record Found.!");
                customizedResponse.setSuccess(true);
                customizedResponse.setStatusList(errorStatus);
            }
            else
            {
                errorStatus.add("Record Not Found.!");
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

    public CustomizedResponse consultantApproval(long consultantId,String type)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        int status = 0;
        if(type.equals("Approve"))
        {
           status = 1;
        }
        try
        {
            Consultant consultant = consultantRepository.getConsultantByConsultantIdAndStatus(consultantId,statusValue.PENDING.sts());
            if(consultant != null) {
                consultant.setStatus(status);
                User user = userRepository.getUserByRegNo(consultant.getRegNo());
                if (user != null) {
                    user.setStatus(status);
                }
                else
                {
                    user.setUserName(consultant.getEmail());
                    user.setRegNo(consultant.getRegNo());
                    user.setUserType("CONSULTANT");
                    user.setPassword("123@#Com");
                    user.setStatus(status);
                }
                if((consultantRepository.save(consultant) !=null) && (userRepository.save(user))!=null){
                    customizedResponse.setSuccess(true);
                    errorStatus.add("Approved!");
                    customizedResponse.setStatusList(errorStatus);
                }
                else
                {
                    customizedResponse.setSuccess(false);
                    errorStatus.add("Got error from approval. Please try again!");
                    customizedResponse.setStatusList(errorStatus);
                }
            }
            else {
                customizedResponse.setSuccess(false);
                errorStatus.add("Consultant Not Found");
                customizedResponse.setStatusList(errorStatus);
            }

        }
        catch (Exception exception)
        {
            customizedResponse.setSuccess(false);
            errorStatus.add("Error -> "+exception);
            customizedResponse.setStatusList(errorStatus);
        }
        return customizedResponse;
    }

    public CustomizedResponse getConsultantByJobId(long jobId)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try {
            List<Consultant> consultantList = modelMapper.map(consultantRepository.getConsultantByStatusAndJob(statusValue.ACTIVE.sts(),jobId),new TypeToken<List<Consultant>>(){}.getType());
            if(consultantList.size()>0)
            {
                errorStatus.add("Data Found.!");
                customizedResponse.setStatusList(errorStatus);
                customizedResponse.setResponse(consultantList);
                customizedResponse.setSuccess(true);
            }
            else
            {
                errorStatus.add("No any consultant to job.!");
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
    public CustomizedResponse getPendingApprovals()
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorList = new ArrayList<>();
        try {

            List<Consultant> pendingConsultant = modelMapper.map(consultantRepository.getPendingApprovals(statusValue.PENDING.sts()),new TypeToken<List<Consultant>>(){}.getType());
            if(pendingConsultant.size()>0)
            {
                customizedResponse.setSuccess(true);
                customizedResponse.setResponse(pendingConsultant);

            }
            else
            {
                errorList.add("No pending request for approve.!");
                customizedResponse.setSuccess(false);
                customizedResponse.setStatusList(errorList);
            }
        }
        catch (Exception exception)
        {
            errorList.add("Error => "+exception );
            customizedResponse.setSuccess(false);
            customizedResponse.setStatusList(errorList);
        }
        return customizedResponse;
    }


}
