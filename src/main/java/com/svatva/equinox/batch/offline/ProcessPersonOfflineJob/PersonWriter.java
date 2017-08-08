package com.svatva.equinox.batch.offline.ProcessPersonOfflineJob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JpaItemWriter;

import com.svatva.equinox.batch.Person;


public class PersonWriter extends JpaItemWriter<Person>{
	
	private static final Logger log = LoggerFactory.getLogger(PersonWriter.class);
	

}
