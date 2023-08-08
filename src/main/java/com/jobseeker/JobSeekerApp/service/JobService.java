package com.jobseeker.JobSeekerApp.service;

import com.jobseeker.JobSeekerApp.dto.JobDTO;
import com.jobseeker.JobSeekerApp.entity.Job;
import com.jobseeker.JobSeekerApp.enums.statusValue;
import com.jobseeker.JobSeekerApp.repository.JobRepository;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ModelMapper modelMapper;
    //save job
    public CustomizedResponse saveJob(JobDTO jobDTO)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        Job job = new Job();
        JobDTO dto = new JobDTO();
        List<String> errorStatus = new ArrayList<>();
        try
        {
            job.setJobTitle(jobDTO.getJobTitle());
            job.setStatus(statusValue.ACTIVE.sts());
            if(jobRepository.save(job) != null)
            {
                errorStatus.add("Successfully Added.!");
                customizedResponse.setResponse(job);
                customizedResponse.setSuccess(true);
                customizedResponse.setStatusList(errorStatus);
            }
            else
            {
                errorStatus.add("Save Error.!");
                customizedResponse.setStatusList(errorStatus);
                customizedResponse.setSuccess(false);
            }
        }
        catch (Exception ex)
        {
            errorStatus.add("Error -> " +ex.toString());
            customizedResponse.setSuccess(false);
            customizedResponse.setStatusList(errorStatus);
        }
        return  customizedResponse;
    }

    //Get all jobs
    public CustomizedResponse getAllJobs()
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
       try {
           List<Job> jobs = modelMapper.map(jobRepository.getJobByStatus(), new TypeToken<List<Job>>() {
           }.getType());
           if (jobs.size() > 0) {
               errorStatus.add("Jobs Found.!");
               customizedResponse.setSuccess(true);
               customizedResponse.setResponse(jobs);
               customizedResponse.setStatusList(errorStatus);
           } else {
               errorStatus.add("Jobs Not Found.!");
               customizedResponse.setSuccess(false);
               customizedResponse.setStatusList(errorStatus);
           }
       }
       catch (Exception ex)
       {
           errorStatus.add("Error -> "+ex.toString());
           customizedResponse.setStatusList(errorStatus);
           customizedResponse.setSuccess(false);
       }
        return customizedResponse;
    }

    //get job by unique id

    public CustomizedResponse getJobyId(long jobId)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        JobDTO job = new JobDTO();
        try {
            if (jobRepository.getJobByJobId(jobId) != null) {
                job = modelMapper.map(jobRepository.getJobByJobId(jobId),JobDTO.class);
                errorStatus.add("Job Found.!");
                customizedResponse.setSuccess(true);
                customizedResponse.setResponse(job);
                customizedResponse.setStatusList(errorStatus);
            }
            else {
                errorStatus.add("Job Id Does Not Exist: " + jobId);
                customizedResponse.setStatusList(errorStatus);
                customizedResponse.setSuccess(false);

            }
        }
        catch (Exception ex)
        {
            errorStatus.add("Error -> "+ex.toString());
            customizedResponse.setStatusList(errorStatus);
            customizedResponse.setSuccess(false);
        }

        return customizedResponse;

    }

    public CustomizedResponse updateJob(Job job)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try{
            if(jobRepository.existsById(job.getJobId())) {
                if (jobRepository.save(job) != null) {
                    errorStatus.add("Successfully Updated.!");
                    customizedResponse.setSuccess(true);
                    customizedResponse.setResponse(job);
                    customizedResponse.setStatusList(errorStatus);
                } else {
                    errorStatus.add("Update Unsuccessful.!");
                    customizedResponse.setSuccess(false);
                    customizedResponse.setStatusList(errorStatus);
                }
            }
            else {
                errorStatus.add("Job Id Does Not Exist: " + job.getJobId());
                customizedResponse.setSuccess(false);
                customizedResponse.setStatusList(errorStatus);
            }
        }
        catch (Exception ex)
        {
            errorStatus.add("Error -> "+ex.toString());
            customizedResponse.setSuccess(false);
            customizedResponse.setStatusList(errorStatus);
        }
    return customizedResponse;
    }

    public CustomizedResponse deleteJobById(long jobId)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try {
            if(jobRepository.getJobByJobId(jobId)!=null)
            {
                Job job = jobRepository.getJobByJobId(jobId);
                Job updateJob = new Job();
                updateJob.setJobId(job.getJobId());
                updateJob.setJobTitle(job.getJobTitle());
                updateJob.setStatus(statusValue.DEACTIVE.sts());

                if(jobRepository.save(updateJob)!=null)
                {
                    errorStatus.add("Successfully Deleted.!");
                    customizedResponse.setSuccess(true);
                    customizedResponse.setStatusList(errorStatus);
                }
                else
                {
                    errorStatus.add("Delete Unsuccessful.!");
                    customizedResponse.setSuccess(false);
                    customizedResponse.setStatusList(errorStatus);
                }
            }
            else {
                errorStatus.add("Job Id Does Not Exist: "+jobId);
                customizedResponse.setSuccess(false);
                customizedResponse.setStatusList(errorStatus);
            }
        }
        catch (Exception ex)
        {
            errorStatus.add("Error -> "+ex.toString());
            customizedResponse.setSuccess(false);
            customizedResponse.setStatusList(errorStatus);
        }
        return customizedResponse;
    }
}
