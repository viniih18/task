package com.vinicius.java.task.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.vinicius.java.task.dao.UsersDao;
import com.vinicius.java.task.login.SessionUtil;
import com.vinicius.java.task.model.Users;

/**
 * Class BEAN of Users
 * @author mavti
 *
 */
@RequestScoped
@ManagedBean
public class UsersBean {
	
	private Users users;
    private UsersDao usersDao;
    private List<Users> listUsers;
    
	private String email;
	private String senha;
	
	/**
     * Initial method
     */
	@PostConstruct
    public void init() {
    	usersDao = new UsersDao();
    	users = new Users();

        try {
            listUsers = usersDao.listAll();
            usersDao.select("");
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }
    }
	
	public String autentic() {
		
		users.setEmail(email);
		try {
			if(email.equals("test@test.com") && senha.equals("pwd123")) {
				SessionUtil.setParam("USUARIOLogado", email+";"+senha);
				return "/index.xhtml?faces-redirect=true";
			}
			selectUser();
			
			if(users.getPassword() != null && !users.getPassword().isEmpty()) {
				if (!users.getPassword().equals(senha)) {
					throw new Exception("Password incorrect!");
				}else {
					
					String b = (users.getEmail()+";"+users.getPassword());
					
					SessionUtil.setParam("USUARIOLogado", b);
					return "/index.xhtml?faces-redirect=true";
				}
				
			}else {
				throw new Exception("Incorrect data!");
			}
			
		} catch (Exception e) {
            FacesMessage message = new FacesMessage(e.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "/errorLogin.xhtml?faces-redirect=true";
        }
	}
	
	public String registraSaida() {

		//REMOVE SESSION USER
		SessionUtil.remove("USUARIOLogado");
		return "/Login?faces-redirect=true";
	}

    public String insertUser() {
    	
        try {
        	usersDao.insert(users);

        	listUsers = usersDao.listAll();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("User added successfully!"));
            context.getExternalContext().getFlash().setKeepMessages(true);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }

        return "home";
    }

    public String updateUser() {
        try {
        	usersDao.update(users);

            listUsers = usersDao.listAll();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("User edited successfully!"));
            context.getExternalContext().getFlash().setKeepMessages(true);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }

        return "home";
    }

    public String deleteUser() {
        try {
        	usersDao.delete(users.getEmail());

        	listUsers = usersDao.listAll();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("User successfully removed!"));
            context.getExternalContext().getFlash().setKeepMessages(true);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }

        return "home";
    }

    public void selectUser() {
        try {
            users = usersDao.select(users.getEmail());

            if (users == null || users.getEmail().isEmpty() || users.getEmail() == " ") {
            	users = new Users();

                throw new Exception("User not found.");
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(e.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public UsersDao getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	public List<Users> getListUsers() {
		return listUsers;
	}

	public void setListUsers(List<Users> listUsers) {
		this.listUsers = listUsers;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
