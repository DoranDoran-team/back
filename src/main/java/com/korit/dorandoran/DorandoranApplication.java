package com.korit.dorandoran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // 스케줄링 테스트
public class DorandoranApplication {

	public static void main(String[] args) {
		SpringApplication.run(DorandoranApplication.class, args);
	}

}