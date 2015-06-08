package br.unb.cic.bionimbuz.rest.request;

public class UploadRequest implements RequestInfo {
	private String filename;
	private String filepath;
	private String login;

	public UploadRequest(String filename, String filepath, String login) {
		this.filename = filename;
		this.filepath = filepath;
		this.login 	  = login;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
