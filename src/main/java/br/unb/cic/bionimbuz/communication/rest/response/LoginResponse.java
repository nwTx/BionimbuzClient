package br.unb.cic.bionimbuz.communication.rest.response;

public class LoginResponse implements ResponseInfo {
	private boolean isAuthorized;

	public LoginResponse() {
	}

	public LoginResponse(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}

	public boolean isAuthorized() {
		return isAuthorized;
	}

	public void setAuthorized(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isAuthorized ? 1231 : 1237);
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
		LoginResponse other = (LoginResponse) obj;
		if (isAuthorized != other.isAuthorized)
			return false;
		return true;
	}

}
