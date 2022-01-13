package com.example.springbatchdemo.config;

import com.example.springbatchdemo.exception.CustomRetryException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Configuration
@EnableBatchProcessing
public class JobConfiguration7 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job itemReaderDemoJob() {
        return jobBuilderFactory.get("itemReaderDemoJob")
                .start(itemReaderDemoStep())
                .build();
    }

    private Step itemReaderDemoStep() {
        return stepBuilderFactory.get("itemReaderDemoStep")
                .<String, String>chunk(2)//意思是每一批处理2个数据
                .reader(itemReaderDemoRead())
                .processor(change())
                .writer(list -> {
                    for (String item: list) {
                        System.out.println(item);
                    }
                })
                .faultTolerant()//容错的
                .retry(CustomRetryException.class)//当reader, processor, writer throw CustomRetryException
                .retryLimit(3)//尝试多少次
                .build();
    }

    private Function<String, String> change() {
        return str -> str + " by function";
    }

    private MyReader itemReaderDemoRead() {
        List<String> data = Arrays.asList("cat", "dog", "pig", "duck");
        return new MyReader(data);
    }
}
