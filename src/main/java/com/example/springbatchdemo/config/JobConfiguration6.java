package com.example.springbatchdemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration
//@EnableBatchProcessing
@Profile("nonuse1")
public class JobConfiguration6 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobLauncher jobLauncher;

    @Bean
    public Step childJobStepA1() {
        return stepBuilderFactory.get("childJobStepA1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("childJobStepA1");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step childJobStepA2() {
        return stepBuilderFactory.get("childJobStepA2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("childJobStepA2");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step childJobStepA3() {
        return stepBuilderFactory.get("childJobStepA3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("childJobStepA3");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    private Job childJobAOne() {
        return jobBuilderFactory.get("childJobAOne")
                .start(childJobStepA1())
                .build();
    }

    private Job childJobATwo() {
        return jobBuilderFactory.get("childJobATwo")
                .start(childJobStepA2())
                .next(childJobStepA3())
                .build();
    }

    @Bean
    public Job parentJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return jobBuilderFactory.get("parentJob")
                .start(childJobA1(jobRepository, platformTransactionManager))
                .next(childJobA2(jobRepository, platformTransactionManager))
                .build();
    }

    private Step childJobA2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobStepBuilder(new StepBuilder("childJobA2"))
                .job(childJobATwo())
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }

    private Step childJobA1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobStepBuilder(new StepBuilder("childJobA1"))
                .job(childJobAOne())
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }
}
