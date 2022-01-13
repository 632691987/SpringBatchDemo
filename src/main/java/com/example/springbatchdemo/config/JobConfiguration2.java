package com.example.springbatchdemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Configuration
//@EnableBatchProcessing
@Profile("nonuse1")
public class JobConfiguration2 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobDemoJob() {
//        return jobBuilderFactory.get("jobDemoJob")
//                .start(step1())
//                .next(step2())
//                .next(step3())
//                .build();
        return jobBuilderFactory.get("jobDemoJob")
                .start(step1()).on("COMPLETED").to(step2())
                .from(step2()).on("COMPLETED").to(step3())//fail(), stopAndRestart()
                .from(step3()).end()
                .build();
    }

    private Step step3() {
        return stepBuilderFactory.get("step3").tasklet((contribution, chunkContext) -> {
            System.out.println("step 3");
            return RepeatStatus.FINISHED;
        }).build();
    }

    private Step step2() {
        return stepBuilderFactory.get("step2").tasklet((contribution, chunkContext) -> {
            System.out.println("step 2");
            return RepeatStatus.FINISHED;
        }).build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1").tasklet((contribution, chunkContext) -> {
            System.out.println("step 1");
            return RepeatStatus.FINISHED;
        }).build();
    }

}
