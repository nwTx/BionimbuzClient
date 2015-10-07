package br.unb.cic.bionimbuz.web.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unb.cic.bionimbuz.exception.ServerNotReachableException;
import br.unb.cic.bionimbuz.model.User;
import br.unb.cic.bionimbuz.rest.service.RestService;

@Named
@RequestScoped
public class SignUpBean {
	private User user = new User();
	private final RestService restService;
	
	public SignUpBean() {
		restService = new RestService();
	}
	
	public String signUp() {
		try {
			return ((restService.signUp(user)) ? "sign_up_success" : "sign_up_error");
		} catch (ServerNotReachableException e) {
			return "server_offline";
		}
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
