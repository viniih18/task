package com.vinicius.java.task.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Modal of Task - creation via DDL
 * @author mavti
 *
 */
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String title;

    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date inicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dtLastUpdate = new Date();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Column(nullable = false,
            columnDefinition = "BIT(1) default 0")
    private boolean done;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", nullable = false)
    private Users user;

    public Task() {
    }
    
    

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getInicio() {
		return inicio;
	}


	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}



	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}


    public String getDoneAsString() {
        return done ? "Yes" : "No";
    }


	public boolean isDone() {
		return done;
	}


	public void setDone(boolean done) {
		this.done = done;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}



	public Date getDtLastUpdate() {
		return dtLastUpdate;
	}



	public void setDtLastUpdate(Date dtLastUpdate) {
		this.dtLastUpdate = dtLastUpdate;
	}
}