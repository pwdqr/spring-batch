package com.example.demo.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // log 사용을 위한 lombok 어노테이션
@RequiredArgsConstructor // 생성자 DI를 위한 lombok 어노테이션
@Configuration
public class SimpleJobConfiguration {
	private final JobBuilderFactory jobBuilderFactory; // 생성자 DI
	private final StepBuilderFactory stepBuilderFactory; // 생성자 DI
	
	/*
	 * Spring Batch에서 Job은 하나의 배치 작업 단위를 의미하며
	 * Job안에는 여러 Step이 존재할 수 있고 Step안에 Tasklet 또는 Reader & Processor & Writer 묶음이 존재한다.
	 */
	@Bean
	public Job simpleJob() {
		return jobBuilderFactory.get("simpleJob") // simpleJob이란 이름의 Batch Job생성. job의 이름은 별도로 지정하지 않고 builder를 통해 지정.
				.start(simpleStep1())
				.build();
	}
	
	@Bean
	public Step simpleStep1() {
		return stepBuilderFactory.get("simpleStep1") // simpleStep1이라는 이름의 Batch Step 생성. builder를 통해 이름 지정.
				.tasklet((contribution, chunkContext) -> { // Step안에서 수행될 기능 명시. Tasklet은 Step안에서 단일로 수행될 커스텀한 기능들을 선언할 때 사용.
					log.info(">>>>> This is Step1"); // Batch가 수행되면 This is Step1 출력.
					return RepeatStatus.FINISHED;
				}).build();
	}
}
