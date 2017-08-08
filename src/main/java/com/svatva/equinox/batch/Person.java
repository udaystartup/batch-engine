package com.svatva.equinox.batch;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Person {

	private static final long serialVersionUID = 1L;
	
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "person_id")
   private Long id;

   @Size(max = 20)
   @Column(name = "last_name", length = 20)
   private String lastName;
   
   @Size(max = 20)
   @Column(name = "first_name", length = 20)
   private String firstName;

    public Person() {

    }

	public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "firstName: " + firstName + ", lastName: " + lastName;
    }

}
