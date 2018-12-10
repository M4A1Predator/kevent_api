package com.gamitology.kevent.kevent;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class KeventApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeventApplication.class, args);
	}

	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));
		System.out.println("Spring boot application running in UTC timezone :"+new Date());   // It will print UTC timezone
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        return jacksonObjectMapperBuilder ->
				jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone("Asia/Bangkok"));
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
