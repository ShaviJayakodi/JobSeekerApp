package com.jobseeker.JobSeekerApp.contreoller;

import com.jobseeker.JobSeekerApp.dto.AppointmentDTO;
import com.jobseeker.JobSeekerApp.dto.CheckDateDTO;
import com.jobseeker.JobSeekerApp.service.AppointmentService;
import com.jobseeker.JobSeekerApp.utils.CustomizedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/checkDate")
    public ResponseEntity<CustomizedResponse> checkDate (@RequestParam ("date") String dateString, @RequestParam("consultantId") Long consultantId)
    {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateString);
            return new ResponseEntity<CustomizedResponse>(appointmentService.checkDate(date, consultantId), HttpStatus.OK);
        }
        catch (ParseException exception)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/makeAnAppointment")
    public ResponseEntity<CustomizedResponse> makeAnAppointment(@RequestBody AppointmentDTO appointmentDTO)
    {
        return new ResponseEntity<CustomizedResponse>(appointmentService.makeAnAppointment(appointmentDTO),HttpStatus.OK);
    }

    @GetMapping("/getAppointmentByConsultant")
    public ResponseEntity<CustomizedResponse> getAppointmentByConsultantId(@RequestParam Long consultantId)
    {
        return new ResponseEntity<CustomizedResponse>(appointmentService.getAppointmentByConsultantId(consultantId),HttpStatus.OK);
    }

    @GetMapping("/getAppointmentByJObSeeker")
    public ResponseEntity<CustomizedResponse> getAppointmentByJobSeekerId(@RequestParam Long jobSeekerId)
    {
        return new ResponseEntity<CustomizedResponse>(appointmentService.getAppointmentByJobSeekerId(jobSeekerId),HttpStatus.OK);
    }


}
