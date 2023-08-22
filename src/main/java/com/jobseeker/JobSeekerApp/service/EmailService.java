package com.jobseeker.JobSeekerApp.service;

import com.jobseeker.JobSeekerApp.dto.MailRegisterDTO;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public CustomizedResponse registerMail(MailRegisterDTO mailRegisterDTO)
    {
        CustomizedResponse customizedResponse = new CustomizedResponse();
        try
        {
            String subject = "Register to Job Seeker Application";
            String fromMail = "jobseekerwebmail@gmail.com";
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            if(mailRegisterDTO.getType().equals("jobseeker"))
            {
                System.out.println("jobseeker");
                simpleMailMessage.setTo(mailRegisterDTO.getEmail());
                simpleMailMessage.setSubject(subject);
                simpleMailMessage.setText( "<html>"
                        + "<head>"
                        + "<style>"
                        + "  body { font-family: Arial, sans-serif; background-color: #f5f5f5; }"
                        + "  .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; border-radius: 5px; box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1); }"
                        + "  .button { display: inline-block; background-color: #007bff; color: #ffffff; padding: 10px 20px; text-decoration: none; border-radius: 5px; }"
                        + "</style>"
                        + "</head>"
                        + "<body>"
                        + "  <div class='container'>"
                        + "    <h1>Hello there!</h1>"
                        + "    <p>Your message goes here.</p>"
                        + "    <a href='http://localhost:3000/consultant' class='button'>Click here to explore</a>"
                        + "  </div>"
                        + "</body>"
                        + "</html>");
                simpleMailMessage.setFrom(fromMail);
                javaMailSender.send(simpleMailMessage);
                System.out.println(simpleMailMessage);
                customizedResponse.setResponse(simpleMailMessage);
            }
            else if(mailRegisterDTO.getType().equals("consultant"))
            {
                System.out.println("consultant");
                simpleMailMessage.setTo(mailRegisterDTO.getEmail());
                simpleMailMessage.setSubject(subject);
                simpleMailMessage.setText(   "<html>"
                        + "<head>"
                        + "<style>"
                        + "  body { margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f5f5f5; }"
                        + "  .container { padding: 20px; text-align: center; }"
                        + "  .button { display: inline-block; background-color: #007bff; color: #ffffff; padding: 10px 20px; text-decoration: none; border-radius: 5px; }"
                        + "</style>"
                        + "</head>"
                        + "<body>"
                        + "  <div class='container'>"
                        + "    <h2>Hello there!</h2>"
                        + "    <p>Your message goes here.</p>"
                        + "    <a href='http://localhost:3000/consultant' class='button' onclick='saveEmailToLocal();'>Go to Consultant Page</a>"
                        + "  </div>"
                        + "</body>"
                        + "<script>"
                        + "  function saveEmailToLocal() {"
                        + "    var email = '" + mailRegisterDTO.getEmail() + "';"
                        + "    localStorage.setItem('userEmail', email);"
                        + "  }"
                        + "</script>"
                        + "</html>");
                simpleMailMessage.setFrom(fromMail);
                javaMailSender.send(simpleMailMessage);
                System.out.println(simpleMailMessage);
                customizedResponse.setResponse(simpleMailMessage);
            }


        }
        catch (Exception exception)
        {

        }
        return customizedResponse;
    }
}
