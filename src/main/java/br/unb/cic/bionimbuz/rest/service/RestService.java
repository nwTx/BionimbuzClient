package br.unb.cic.bionimbuz.rest.service;

import java.io.IOException;

import br.unb.cic.bionimbuz.communication.RestCommunicator;
import br.unb.cic.bionimbuz.model.User;
import br.unb.cic.bionimbuz.rest.action.Login;
import br.unb.cic.bionimbuz.rest.action.Logout;
import br.unb.cic.bionimbuz.rest.action.Upload;
import br.unb.cic.bionimbuz.rest.request.LoginRequest;
import br.unb.cic.bionimbuz.rest.request.LogoutRequest;
import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.request.UploadRequest;
import br.unb.cic.bionimbuz.rest.response.LoginResponse;
import br.unb.cic.bionimbuz.rest.response.LogoutResponse;
import br.unb.cic.bionimbuz.rest.response.UploadResponse;

public class RestService {
	private RestCommunicator restCommunicator;

	/**
	 * Constructor that initializes the REST Communicator
	 */
	public RestService() {
		restCommunicator = new RestCommunicator();
	}

	/**
	 * Fires a Login request to the server
	 * @param login
	 * @param password
	 */
	public User login(User user) throws Exception {
		RequestInfo loginRequest = new LoginRequest(user);		
		LoginResponse resp = (LoginResponse) restCommunicator.sendRequest(new Login(), loginRequest);
		
		return resp.getUser();
	}
	
	/**
	 * Communicates an user logout to the server
	 * @param user
	 * @return
	 */
	public boolean logout (User user) throws Exception {
		RequestInfo logoutRequest = new LogoutRequest(user);
		LogoutResponse resp = (LogoutResponse) restCommunicator.sendRequest(new Logout(), logoutRequest);
		
		return resp.isLogoutSuccess();
	}
	
	/**
	 * Fires an Upload request to the server
	 * @param filename
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(String filename, String filepath, String login) throws Exception {
		RequestInfo uploadRequest = new UploadRequest(filename, filepath, login);
		UploadResponse resp = (UploadResponse) restCommunicator.sendRequest(new Upload(), uploadRequest);
		
		return resp.isUploaded();
	}
}
