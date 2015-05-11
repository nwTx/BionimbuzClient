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

public class JobController {
	private RestCommunicator restCommunicator;

	/**
	 * Constructor that initializes the REST Communicator
	 */
	public JobController() {
		restCommunicator = new RestCommunicator();
	}

	/**
	 * 
	 * @param login
	 * @param password
	 */
	public boolean login(String login, String password) {
		RequestInfo loginRequest = new LoginRequest(login, password);		
		LoginResponse resp = (LoginResponse) restCommunicator.sendRequest(new LoginRestClient(), loginRequest);

		return resp.isAuthorized();
	}
	
	public boolean uploadFile(String filename, String filepath) throws IOException {
		RequestInfo uploadRequest = new UploadRequest(filename, filepath);
		UploadResponse resp = (UploadResponse) restCommunicator.sendRequest(new UploadRestClient(), uploadRequest);
		
		return resp.isUploaded();
	}
}
