package br.unb.cic.bionimbuz.jobcontroller;

import java.io.IOException;

import br.unb.cic.bionimbuz.communication.RestCommunicator;
import br.unb.cic.bionimbuz.communication.rest.client.LoginRestClient;
import br.unb.cic.bionimbuz.communication.rest.client.UploadRestClient;
import br.unb.cic.bionimbuz.communication.rest.request.LoginRequest;
import br.unb.cic.bionimbuz.communication.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.communication.rest.request.UploadRequest;
import br.unb.cic.bionimbuz.communication.rest.response.LoginResponse;
import br.unb.cic.bionimbuz.communication.rest.response.UploadResponse;
import br.unb.cic.bionimbuz.model.User;

public class JobController {
	private RestCommunicator restCommunicator;

	/**
	 * Constructor that initializes the REST Communicator
	 */
	public JobController() {
		restCommunicator = new RestCommunicator();
	}

	/**
	 * Fires a Login request to the server
	 * @param login
	 * @param password
	 */
	public User login(User user) {
		RequestInfo loginRequest = new LoginRequest(user);		
		LoginResponse resp = (LoginResponse) restCommunicator.sendRequest(new LoginRestClient(), loginRequest);
		
		return resp.getUser();
	}
	
	/**
	 * Fires an Upload request to the server
	 * @param filename
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(String filename, String filepath) throws IOException {
		RequestInfo uploadRequest = new UploadRequest(filename, filepath);
		UploadResponse resp = (UploadResponse) restCommunicator.sendRequest(new UploadRestClient(), uploadRequest);
		
		return resp.isUploaded();
	}
}
