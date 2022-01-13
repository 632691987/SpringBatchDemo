package com.example.springbatchdemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

//@Configuration
//@EnableBatchProcessing
@Profile("nonuse1")
public class JobConfiguration5 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    // 创建 Step
    private Step deciderDemoStep1() {
        return stepBuilderFactory.get("deciderDemoStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("odd");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    private Step deciderDemoStep2() {
        return stepBuilderFactory.get("deciderDemoStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("even");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    private Step deciderDemoStep3() {
        return stepBuilderFactory.get("deciderDemoStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("odd");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    // 决策器
    @Bean
    public JobExecutionDecider myDecider() {
        return new MyDecider();
    }

    /**
     * 由决策器的返回值决定运行哪个一 step
     */
    @Bean
    public Job deciderDemoJob() {
        return jobBuilderFactory.get("deciderDemoJob")
                .start(deciderDemoStep1())
                .next(myDecider())
                .from(myDecider()).on("even").to(deciderDemoStep2())
                .from(myDecider()).on("odd").to(deciderDemoStep3())
                .from(deciderDemoStep3()).on("*").to(myDecider())// 这样是为了清空决策器，否则回循环运行 step
                .end().build();
    }
}
