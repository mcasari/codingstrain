
package com.example.batch.config;

import com.example.batch.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);

    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personReader")
                .resource(new ClassPathResource("sample.csv"))
                .delimited()
                .names("name", "email")
                .linesToSkip(1)
                .targetType(Person.class)
                .build();
    }

    @Bean
    public ItemProcessor<Person, Person> processor() {
        return person -> {
            person.setName(person.getName().toUpperCase());
            return person;
        };
    }

    @Bean
    public ItemWriter<Person> writer() {
        return items -> {
            for (Person p : items) {
                log.info("Processed: {} - {}", p.getName(), p.getEmail());
            }
        };
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .<Person, Person>chunk(5, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }
}
