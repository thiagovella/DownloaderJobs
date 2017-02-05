package com.goiaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:application.properties", "classpath:custom_application.properties"})
public class DownloaderJobsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DownloaderJobsApplication.class, args);
	}
}
