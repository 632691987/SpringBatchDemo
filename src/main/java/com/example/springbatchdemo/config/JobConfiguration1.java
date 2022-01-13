package com.example.springbatchdemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

//@Configuration
//@EnableBatchProcessing
@Profile("nonuse1")
public class JobConfiguration1 {

    //注入创建任务对象的那个对象
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    //任务的执行由 step 决定
    //注入创建 step 对象的对象
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //创建任务对象
    @Bean
    public Job helloWorldJob() {// 这个 job 就是运行 step1的 功能
        return jobBuilderFactory.get("helloWorldJob")
                .start(step1())
                .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Hello world");
                        return RepeatStatus.FINISHED; // 只有它正常结束了，下一步的 step 才能正常运行
                    }
                }).build();
    }

}
