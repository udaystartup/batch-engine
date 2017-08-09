package com.svatva.equinox.batch.offline.ProcessPersonOfflineJob;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;

import com.svatva.equinox.batch.JobCompletionNotificationListener;
import com.svatva.equinox.batch.Person;
import com.svatva.equinox.batch.PersonItemProcessor;
import com.svatva.equinox.repository.PersonRepository;

@Configuration
@EnableBatchProcessing
public class ProcessPersonOfflineJobConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private static final Logger log = LoggerFactory.getLogger(ProcessPersonOfflineJobConfig.class);

    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<Person> ProcessPersonOfflineJobReader() {
        log.info("Offline Batch - Inside  ProcessPersonOfflineJobReader");
        FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
        reader.setResource(new ClassPathResource("sample-data.csv"));
        reader.setLineMapper(new DefaultLineMapper<Person>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "firstName", "lastName" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }});
        }});
        return reader;
    }

    @Bean
    public PersonItemProcessor ProcessPersonOfflineJobProcessor() {
        log.info("Offline Batch - Inside  ProcessPersonOfflineJobProcessor");
        return new PersonItemProcessor();
    }
    
   

    @Bean
    public ItemWriter<Person> ProcessPersonOfflineJobWriter() {
    	
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
//        writer.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
//        writer.setDataSource(dataSource);
        return new PersonWriter();
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job ProcessPersonOfflineJob(JobCompletionNotificationListener listener) {
        log.info("Offline Batch - Inside  ProcessPersonOfflineJob");
        return jobBuilderFactory.get("ProcessPersonOfflineJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(ProcessPersonOfflineJobStep1())
                .end()
                .build();
    }

    @Bean
    public Step ProcessPersonOfflineJobStep1() {
        log.info("Offline Batch - Inside  ProcessPersonOfflineJobStep1");
        return stepBuilderFactory.get("ProcessPersonOfflineJobStep1")
                .<Person, Person> chunk(10)
                .reader(ProcessPersonOfflineJobReader())
                .processor(ProcessPersonOfflineJobProcessor())
                .writer(ProcessPersonOfflineJobWriter())
                .build();
    }
    // end::jobstep[]
}
