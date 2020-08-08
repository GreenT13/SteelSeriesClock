package com.apon.steelseriesengineapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SteelSeriesEngineAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SteelSeriesEngineAppApplication.class, args);
	}

}
