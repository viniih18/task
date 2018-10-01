package com.vinicius.java.task.model;

import javax.persistence.*;
import javax.validation.constraints.Size;


/**
 * Modal of Users - creation via DDL
 * @author mavti
 *
 */
@Entity
public class Users {

    @Id
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String email;
    
    
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String password;


    public Users() {
    }
    
    public Users(String email, String password) {
    	this.email = email;

    	this.password = password;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
}