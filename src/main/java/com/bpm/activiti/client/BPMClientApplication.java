package com.bpm.activiti.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import com.bpm.activiti.client.config.WebSecurityConfig;

@SpringBootApplication
@Import(WebSecurityConfig.class)
public class BPMClientApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BPMClientApplication.class, args);
	}
	
	  @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(BPMClientApplication.class);
	    }

	    
}
