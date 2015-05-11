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
	private boolean loginError;

	private User user = new User();
	
	public SessionBean() {
		jobController = new JobController();
	}
	
	/**
	 * 
	 * @return redirectView
	 */
	public String login() {
		boolean serverResponse = jobController.login(user.getLogin(), user.getPassword());

		if (serverResponse) {
			loginError = false;
			return "success";
		} else {
			loginError = true;
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

	public boolean isLoginError() {
		return loginError;
	}

	public void setLoginError(boolean loginError) {
		this.loginError = loginError;
	}

}
