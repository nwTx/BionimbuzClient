package br.unb.cic.bionimbuz.rest.service;

import java.io.IOException;

import br.unb.cic.bionimbuz.communication.RestCommunicator;
import br.unb.cic.bionimbuz.exception.ServerNotReachableException;
import br.unb.cic.bionimbuz.info.FileInfo;
import br.unb.cic.bionimbuz.model.User;
import br.unb.cic.bionimbuz.rest.action.DeleteFile;
import br.unb.cic.bionimbuz.rest.action.Login;
import br.unb.cic.bionimbuz.rest.action.Logout;
import br.unb.cic.bionimbuz.rest.action.Upload;
import br.unb.cic.bionimbuz.rest.request.DeleteFileRequest;
import br.unb.cic.bionimbuz.rest.request.LoginRequest;
import br.unb.cic.bionimbuz.rest.request.LogoutRequest;
import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.request.UploadRequest;
import br.unb.cic.bionimbuz.rest.response.DeleteFileResponse;
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
	 * @throws Exception 
	 */
	public User login(User user) throws ServerNotReachableException {
		RequestInfo loginRequest = new LoginRequest(user);		
		LoginResponse resp = (LoginResponse) restCommunicator.sendRequest(new Login(), loginRequest);
		
		return resp.getUser();
	}
	
	/**
	 * Communicates an user logout to the server
	 * @param user
	 * @return
	 * @throws ServerNotReachableException 
	 */
	public boolean logout (User user) throws ServerNotReachableException {
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
	public boolean uploadFile(FileInfo fileInfo) throws Exception {
		RequestInfo uploadRequest = new UploadRequest(fileInfo);
		UploadResponse resp = (UploadResponse) restCommunicator.sendRequest(new Upload(), uploadRequest);
		
		return resp.isUploaded();
	}
	
	public boolean deleteFile(FileInfo fileInfo) throws ServerNotReachableException {
		RequestInfo deleteFileRequest = new DeleteFileRequest(fileInfo);
		DeleteFileResponse response = (DeleteFileResponse) restCommunicator.sendRequest(new DeleteFile(), deleteFileRequest);
		
		return response.isDeleted();
	}
}
