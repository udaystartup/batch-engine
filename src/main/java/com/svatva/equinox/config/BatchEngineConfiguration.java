package com.svatva.equinox.config;


import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.transaction.PlatformTransactionManager;

import com.svatva.equinox.config.DatabaseConfiguration;

@Configuration
@EnableBatchProcessing(modular=true)
public class BatchEngineConfiguration {

@Autowired
public JobRepository jobRepository;

@Autowired
public JobLauncher jobLauncher;

@Autowired
public JobRegistry jobRegistry;

@Autowired
public JobOperator jobOperator;

@Autowired
public JobExplorer jobExplorer;

@Autowired
public PlatformTransactionManager transactionManager;

@Autowired
public JobBuilderFactory jobBuilders;

@Autowired
public StepBuilderFactory stepBuilders;

@Autowired
private DatabaseConfiguration databaseConfiguration;




//private JobRepository getJobRepository() throws Exception {
//        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
//        factory.setDataSource();
//        factory.setTransactionManager(getTransactionManager());
//        factory.afterPropertiesSet();
//        return (JobRepository) factory.getObject();
//}

//private PlatformTransactionManager getTransactionManager() {
//        return new ResourcelessTransactionManager();
//}

// public JobLauncher getJobLauncher() throws Exception {
//        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
//        jobLauncher.setJobRepository(getJobRepository());
//        jobLauncher.afterPropertiesSet();
//        return jobLauncher;
//    }

}
