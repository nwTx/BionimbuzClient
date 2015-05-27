package br.unb.cic.bionimbuz.communication.rest.request;

import br.unb.cic.bionimbuz.model.User;

/**
 * Class that defines a login request to the BioNimbuZ core
 * 
 * @author usuario
 * 
 */
public class LoginRequest implements RequestInfo {
	private User user;

	public LoginRequest(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginRequest other = (LoginRequest) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LoginRequest [user=" + user + "]";
	}

}
