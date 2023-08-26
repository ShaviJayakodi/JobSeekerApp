package com.jobseeker.JobSeekerApp.service;

import com.jobseeker.JobSeekerApp.dto.AdminDTO;
import com.jobseeker.JobSeekerApp.entity.Admin;
import com.jobseeker.JobSeekerApp.entity.User;
import com.jobseeker.JobSeekerApp.enums.stakeHolderValues;
import com.jobseeker.JobSeekerApp.enums.statusValue;
import com.jobseeker.JobSeekerApp.repository.AdminRepository;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private EmailService emailService;

    public CustomizedResponse saveAdmin(AdminDTO adminDTO) {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try {
            int nextId = adminRepository.getNextAdminId();
            Admin admin = new Admin();
            User user = new User();
            boolean isValidEmail = userService.checkEmailAddress(adminDTO.getEmail());
            if (isValidEmail) {
                admin=modelMapper.map(adminDTO,Admin.class);
                admin.setRegNo(commonService.generateRegNo(nextId, stakeHolderValues.ADMIN.code()));
                admin.setIsFirstLogin(statusValue.ACTIVE.sts());
                admin.setStatus(statusValue.ACTIVE.sts());
                if (adminRepository.save(admin) != null) {
                    //user record saving
                    user.setUserType("ADMIN");
                    user.setUserName(adminDTO.getEmail());
                    user.setPassword(adminDTO.getPassWord());
                    user.setRegNo(admin.getRegNo());
                    user.setStatus(statusValue.ACTIVE.sts());
                    userService.saveUser(user);

                    //sending email
                    emailService.newAdminMail(user);
                    customizedResponse.setResponse(admin);
                    customizedResponse.setSuccess(true);
                    errorStatus.add("Admin Registered.! Sent a Mail to New Admin.!");
                    customizedResponse.setStatusList(errorStatus);
                } else {
                    customizedResponse.setSuccess(false);
                    errorStatus.add("Registration Error");
                    customizedResponse.setStatusList(errorStatus);
                }
            } else {
                customizedResponse.setSuccess(false);
                errorStatus.add("Email Address is Already Exist.!");
                customizedResponse.setStatusList(errorStatus);
            }
    }
        catch(Exception exception)
        {
            customizedResponse.setSuccess(false);
            errorStatus.add("Error -> "+exception);
            customizedResponse.setStatusList(errorStatus);
        }
        return customizedResponse;
    }
}
