package com.vinicius.java.task.bean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.vinicius.java.task.dao.TaskDao;
import com.vinicius.java.task.login.SessionUtil;
import com.vinicius.java.task.model.Priority;
import com.vinicius.java.task.model.Task;
import com.vinicius.java.task.model.Users;

/**
 * Class BEAN of Task
 * @author mavti
 *
 */
@RequestScoped
@ManagedBean
public class TaskBean {

    private Task task;
    private TaskDao taskDao;
    private List<Task> tasks;
    
    
    /**
     * Initial method
     */
    @PostConstruct
    public void init() {
    	
    	taskDao = new TaskDao();
        task = new Task();

        try {
        	String params = (String)SessionUtil.getParam("USUARIOLogado");
        	String [] param = params.split(";");
            tasks = taskDao.listByUser(param[0]);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public String inserir() {
        try {
        	String params = (String)SessionUtil.getParam("USUARIOLogado");
        	String [] param = params.split(";");
        	Users userEmail = new Users(param[0], param[1]);
        	task.setUser(userEmail);
        	taskDao.insert(task);
        	
            tasks = taskDao.listByUser(param[0]);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Task added successfully!"));
            context.getExternalContext().getFlash().setKeepMessages(true);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }

        return "home";
    }

    public String update() {
        try {
        	task.setDtLastUpdate(new Date());
        	taskDao.refresh(task);

        	String params = (String)SessionUtil.getParam("USUARIOLogado");
        	String [] param = params.split(";");
            tasks = taskDao.listByUser(param[0]);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Task edited successfully!"));
            context.getExternalContext().getFlash().setKeepMessages(true);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }

        return "/index.xhtml?faces-redirect=true";
    }

    public String excluir() {
        try {
        	System.out.println("VINICIUS EXCLUIR");
        	taskDao.delete(task.getId());

        	String params = (String)SessionUtil.getParam("USUARIOLogado");
        	String [] param = params.split(";");
            tasks = taskDao.listByUser(param[0]);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Task successfully removed!"));
            context.getExternalContext().getFlash().setKeepMessages(true);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }

        return "home";
    }

    public void selecionar() {
        try {
            task = taskDao.select(task.getId());

            if (task == null || task.getId() == 0) {
                task = new Task();

                throw new Exception("Task not found.");
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(e.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public Priority[] getPrioritys() {
        return Priority.values();
    }
    
    public Task getTask() {
        return task;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
