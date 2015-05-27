package br.unb.cic.bionimbuz.web.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.unb.cic.bionimbuz.jobcontroller.JobController;
import br.unb.cic.bionimbuz.model.User;

@Named
@SessionScoped
public class SessionBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private final JobController jobController;
	private User loggedUser = new User();
	
	private User user = new User();
	
	public SessionBean() {
		jobController = new JobController();
	}
	
	/**
	 * 
	 * @return redirectView
	 */
	public String login() {
		User responseUser = jobController.login(user);

		if (responseUser.getCpf() != null) {
			loggedUser = responseUser;
			
			return "success";
		} else {
			user = new User();
			
			return "login?faces-redirect=true&error=true";			
		}
		
	}

	/**
	 * Invalidates user session
	 * @return
	 */
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "logout";
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

}
