package com.jobseeker.JobSeekerApp.service;

import com.jobseeker.JobSeekerApp.dto.AdminDTO;
import com.jobseeker.JobSeekerApp.entity.Admin;
import com.jobseeker.JobSeekerApp.entity.User;
import com.jobseeker.JobSeekerApp.enums.stakeHolderValues;
import com.jobseeker.JobSeekerApp.enums.statusValue;
import com.jobseeker.JobSeekerApp.repository.AdminRepository;
import com.jobseeker.JobSeekerApp.repository.UserRepository;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminService implements CommandLineRunner {
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

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Code to save the first admin object during application startup
        if (adminRepository.count() == 0) {
            AdminDTO admin = new AdminDTO();
            admin.setEmail("admin@email.com");
            admin.setFirstName("Super");
            admin.setLastName("Admin");
            admin.setPassWord("123@#Com");
            saveAdmin(admin);
        }
    }
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

    public CustomizedResponse getAdminByRegNo(String regNo)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try
        {
            Admin admin = adminRepository.getAdminByRegNo(regNo);
            if(admin!=null)
            {
                customizedResponse.setSuccess(true);
                customizedResponse.setResponse(admin);
            }
            else
            {
                errorStatus.add("Admin not found.!");
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

    public CustomizedResponse getAllAdmin()
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try
        {
            List<Admin> adminList = modelMapper.map(adminRepository.getAllAdmin(),new TypeToken<List<Admin>>(){}.getType());
            if(adminList.size()>0)
            {
                customizedResponse.setSuccess(true);
                customizedResponse.setResponse(adminList);
            }
            else
            {
                errorStatus.add("No any admin found.!");
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


    public CustomizedResponse updateAdmin(AdminDTO adminDTO)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try {
            if(adminRepository.existsById(adminDTO.getAdminId()))
            {
                Admin admin = modelMapper.map(adminDTO,Admin.class);
                admin.setStatus(statusValue.ACTIVE.sts());
                if(adminRepository.save(admin)!=null)
                {
                    customizedResponse.setSuccess(true);
                    customizedResponse.setResponse(admin);
                }
                else
                {
                    errorStatus.add("Update Failed.!");
                    customizedResponse.setSuccess(false);
                    customizedResponse.setStatusList(errorStatus);
                }

            }
            else
            {
                errorStatus.add("Admin Not Found.!");
                customizedResponse.setSuccess(false);
                customizedResponse.setStatusList(errorStatus);
            }

        }
        catch (Exception exception) {

            errorStatus.add("Error => "+exception);
            customizedResponse.setSuccess(false);
            customizedResponse.setStatusList(errorStatus);
        }
        return customizedResponse;

    }

    public CustomizedResponse deleteAdmin(String regNo) {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        List<String> errorStatus = new ArrayList<>();
        try
        {
            Admin admin =adminRepository.getAdminByRegNo(regNo);
            if(admin!=null)
            {
                admin.setStatus(statusValue.DEACTIVE.sts());
                if(adminRepository.save(admin)!=null)
                {
                    User user = userRepository.getUserByRegNo(regNo);
                    user.setStatus(statusValue.DEACTIVE.sts());
                    userRepository.save(user);
                    errorStatus.add("Update Failed.!");
                    customizedResponse.setSuccess(false);
                    customizedResponse.setStatusList(errorStatus);
                }
                else
                {
                    errorStatus.add("Delete Failed.!");
                    customizedResponse.setSuccess(false);
                    customizedResponse.setStatusList(errorStatus);
                }
            }
            else
            {
                errorStatus.add("Admin Not Found.!");
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
        return  customizedResponse;
    }


}
