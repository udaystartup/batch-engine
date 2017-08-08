package com.svatva.equinox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svatva.equinox.batch.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{
	
	

}
