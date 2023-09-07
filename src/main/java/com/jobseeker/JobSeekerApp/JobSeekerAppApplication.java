package com.jobseeker.JobSeekerApp;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JobSeekerAppApplication {

	public static void main(String[] args) {


		SpringApplication.run(JobSeekerAppApplication.class, args);
		System.out.println("System Successfully Started.");

	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
