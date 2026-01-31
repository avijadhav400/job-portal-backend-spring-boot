package com.jobportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication
public class JobPortalV2Application {

	public static void main(String[] args) {

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			System.out.println(encoder.encode("admin123"));
			System.out.println(encoder.encode("recruiter123"));
			System.out.println(encoder.encode("seeker123"));


		SpringApplication.run(JobPortalV2Application.class, args);
	}

}
