package com.svatva.equinox.batch.online.ProcessPersonOnlineJob;

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
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;

import com.svatva.equinox.batch.JobCompletionNotificationListener;
import com.svatva.equinox.batch.Person;
import com.svatva.equinox.batch.PersonItemProcessor;
import com.svatva.equinox.batch.offline.ProcessPersonOfflineJob.PersonWriter;

@Configuration
@EnableBatchProcessing
public class ProcessPersonOnlineJobConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
     

    private static final Logger log = LoggerFactory.getLogger(ProcessPersonOnlineJobConfig.class);

    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<Person> ProcessPersonOnlineJobReader() {
        log.info("Online Batch - Inside  ProcessPersonOnlineJobReader");
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
    public PersonItemProcessor ProcessPersonOnlineJobProcessor() {
        log.info("Online Batch - Inside  ProcessPersonOnlineJobProcessor");
        return new PersonItemProcessor();
    }

    @Bean
    public ItemWriter<Person> ProcessPersonOnlineJobWriter() {
	log.info("Online Batch - Inside  ProcessPersonOnlineJobWriter");
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
//        writer.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
//        writer.setDataSource(dataSource);
        return new PersonWriter();
    }
	    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job ProcessPersonOnlineJob(JobCompletionNotificationListener listener) {
        log.info("Online Batch - Inside  ProcessPersonOnlineJob");
        return jobBuilderFactory.get("ProcessPersonOnlineJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(ProcessPersonOnlineJobStep1())
                .end()
                .build();
    }

    @Bean
    public Step ProcessPersonOnlineJobStep1() {
        log.info("Online Batch - Inside  ProcessPersonOnlineJobStep1");
        return stepBuilderFactory.get("ProcessPersonOnlineJobStep1")
                .<Person, Person> chunk(10)
                .reader(ProcessPersonOnlineJobReader())
                .processor(ProcessPersonOnlineJobProcessor())
                .writer(ProcessPersonOnlineJobWriter())
                .build();
    }
    // end::jobstep[]
}
