package com.gamitology.kevent.kevent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication(exclude = {JacksonAutoConfiguration.class})
public class KeventApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeventApplication.class, args);
	}

	private static final Logger logger = LogManager.getLogger(KeventApplication.class);

	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));
		System.out.println("Spring boot application running in UTC timezone :"+new Date());   // It will print UTC timezone
	}

//	@Bean
//	public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
//        return jacksonObjectMapperBuilder -> {
//			jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone("Asia/Bangkok"));
////			jacksonObjectMapperBuilder.dateFormat(new ISO8601DateFormat());
//		};
//	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
