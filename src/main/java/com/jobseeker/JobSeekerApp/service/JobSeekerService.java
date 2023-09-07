package com.jobseeker.JobSeekerApp.service;

import com.jobseeker.JobSeekerApp.dto.JobSeekerDTO;
import com.jobseeker.JobSeekerApp.entity.JobSeeker;
import com.jobseeker.JobSeekerApp.entity.User;
import com.jobseeker.JobSeekerApp.enums.stakeHolderValues;
import com.jobseeker.JobSeekerApp.enums.statusValue;
import com.jobseeker.JobSeekerApp.repository.JobSeekerRepository;
import com.jobseeker.JobSeekerApp.repository.UserRepository;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobSeekerService {

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserRepository userRepository;


    public CustomizedResponse saveJobSeeker(JobSeekerDTO jobSeekerDTO)
    {
        CustomizedResponse customizedResponse =  new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        int nextId = jobSeekerRepository.getNextJobSeekerId();
        User user = new User();
        String regNo = commonService.generateRegNo(nextId, stakeHolderValues.SEEKER.code());
        try {
                JobSeeker jobSeeker = modelMapper.map(jobSeekerDTO,JobSeeker.class);
                jobSeeker.setRegNo(regNo);
                jobSeeker.setStatus(statusValue.ACTIVE.sts());

                if(jobSeekerRepository.save(jobSeeker) != null)
                {
                    user.setUserType("JOBSEEKER");
                    user.setUserName(jobSeeker.getEmail());
                    user.setRegNo(jobSeeker.getRegNo());
                    user.setPassword(jobSeekerDTO.getPassword());
                    user.setStatus(statusValue.ACTIVE.sts());
                    if(userRepository.save(user) != null)
                    {
                        errorStatus.add("Job Seeker Registered Successfully.!");
                        customizedResponse.setStatusList(errorStatus);
                        customizedResponse.setSuccess(true);
                        customizedResponse.setResponse(jobSeeker);
                    }
                    else
                    {
                        jobSeekerRepository.delete(jobSeeker);
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
            customizedResponse.setStatusList(errorStatus);
            customizedResponse.setSuccess(false);
        }

        return customizedResponse;
    }
    public CustomizedResponse getAllJobSeekers()
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try
        {
            List<JobSeeker> jobSeekers = modelMapper.map(jobSeekerRepository.getAllJobSeekerByStatus(),new TypeToken<List<JobSeeker>>(){}.getType());
            if(jobSeekers!=null && jobSeekers.size()>0)
            {
                errorStatus.add("Job Seekers Found.!");
                customizedResponse.setResponse(jobSeekers);
                customizedResponse.setSuccess(true);
                customizedResponse.setStatusList(errorStatus);
            }
            else {
                errorStatus.add("Job Seekers Not Found.!");
                customizedResponse.setSuccess(false);
                customizedResponse.setStatusList(errorStatus);
            }

        }
        catch (Exception exception)
        {
            errorStatus.add("Error - > " +exception);
            customizedResponse.setSuccess(false);
            customizedResponse.setStatusList(errorStatus);

        }
        return customizedResponse;
    }
    public CustomizedResponse updateJobSeeker(JobSeekerDTO jobSeekerDTO)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();

        try
        {
            if(jobSeekerRepository.existsById(jobSeekerDTO.getJobSeekerId()))
            {
                JobSeeker jobSeeker = modelMapper.map(jobSeekerDTO,JobSeeker.class);
                jobSeeker.setStatus(statusValue.ACTIVE.sts());
                if(jobSeekerRepository.save(jobSeeker)!=null)
                {
                    errorStatus.add("Successfully Updates.!");
                    customizedResponse.setSuccess(true);
                    customizedResponse.setStatusList(errorStatus);
                    customizedResponse.setResponse(jobSeeker);
                }
                else
                {
                    errorStatus.add("Update Error.!");
                    customizedResponse.setSuccess(false);
                    customizedResponse.setStatusList(errorStatus);
                }
            }
            else
            {
                errorStatus.add("Job seeker no found.!");
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

    public CustomizedResponse deleteJobSeeker(String regNo)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try {
            if(jobSeekerRepository.getJobSeekerByRegNo(regNo)!=null)
            {
                JobSeeker jobSeeker = jobSeekerRepository.getJobSeekerByRegNo(regNo);
                jobSeeker.setStatus(statusValue.DEACTIVE.sts());
                if(jobSeekerRepository.save(jobSeeker)!=null)
                {
                    User user = userRepository.getUserByRegNo(regNo);
                    user.setStatus(statusValue.DEACTIVE.sts());
                    userRepository.save(user);

                    errorStatus.add("Successfully Deleted.!");
                    customizedResponse.setSuccess(true);
                    customizedResponse.setStatusList(errorStatus);
                }
                else {
                    errorStatus.add("Delete Error.!");
                    customizedResponse.setSuccess(false);
                    customizedResponse.setStatusList(errorStatus);
                }
            }
            else
            {
                errorStatus.add("Job seeker no found.!");
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

    public CustomizedResponse getJobSeekerByRegNo(String regNo)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try
        {
            JobSeeker jobSeeker = jobSeekerRepository.getJobSeekerByRegNo(regNo);
            if(jobSeeker!=null)
            {
                customizedResponse.setSuccess(true);
                customizedResponse.setStatusList(errorStatus);
                customizedResponse.setResponse(jobSeeker);
            }
            else
            {
                errorStatus.add("Job seeker not found.!");
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

}
