package com.svatva.equinox.batch.offline.ProcessPersonOfflineJob;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;

import com.svatva.equinox.batch.Person;


public class PersonWriter implements ItemWriter<Person>{
	
	private static final Logger log = LoggerFactory.getLogger(PersonWriter.class);

	@Override
	public void write(List<? extends Person> person) throws Exception {
		

		System.out.println(person.toString());
		
	}
	

}
