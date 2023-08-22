package com.jobseeker.JobSeekerApp.service;

import com.jobseeker.JobSeekerApp.dto.UserDTO;
import com.jobseeker.JobSeekerApp.entity.User;
import com.jobseeker.JobSeekerApp.repository.UserRepository;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    public CustomizedResponse saveUser(User user) {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        if(userRepository.save(user) != null)
        {
            customizedResponse.setSuccess(true);
        }
        else {
            customizedResponse.setSuccess(false);
        }
        return customizedResponse;
    }

    public boolean checkEmailAddress(String email)
    {
        boolean isValid = false;
        User user = userRepository.checkEmail(email);
        if(user!=null)
        {
            isValid = false;
        }
        else
        {
            isValid =true;
        }
        return isValid;
    }

    public CustomizedResponse getUserByRegNo(String regNo)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus =new ArrayList<>();
        try
        {
            User user = userRepository.getUserByRegNo(regNo);
            if(user != null)
            {
                customizedResponse.setResponse(user);
                customizedResponse.setSuccess(true);
            }
            else
            {
                errorStatus.add("User Not Found!");
                customizedResponse.setSuccess(false);
            }
        }
        catch (Exception exception)
        {
            errorStatus.add("Error -> "+exception);
            customizedResponse.setSuccess(false);
        }
        return customizedResponse;
    }




}
