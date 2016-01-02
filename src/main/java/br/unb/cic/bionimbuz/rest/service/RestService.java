package br.unb.cic.bionimbuz.rest.service;

import java.io.IOException;

import br.unb.cic.bionimbuz.communication.RestCommunicator;
import br.unb.cic.bionimbuz.exception.ServerNotReachableException;
import br.unb.cic.bionimbuz.model.UploadedFileInfo;
import br.unb.cic.bionimbuz.model.User;
import br.unb.cic.bionimbuz.model.Workflow;
import br.unb.cic.bionimbuz.rest.action.DeleteFile;
import br.unb.cic.bionimbuz.rest.action.Login;
import br.unb.cic.bionimbuz.rest.action.Logout;
import br.unb.cic.bionimbuz.rest.action.SignUp;
import br.unb.cic.bionimbuz.rest.action.StartWorkflow;
import br.unb.cic.bionimbuz.rest.action.Upload;
import br.unb.cic.bionimbuz.rest.request.DeleteFileRequest;
import br.unb.cic.bionimbuz.rest.request.LoginRequest;
import br.unb.cic.bionimbuz.rest.request.LogoutRequest;
import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.request.SignUpRequest;
import br.unb.cic.bionimbuz.rest.request.StartWorkflowRequest;
import br.unb.cic.bionimbuz.rest.request.UploadRequest;
import br.unb.cic.bionimbuz.rest.response.DeleteFileResponse;
import br.unb.cic.bionimbuz.rest.response.LoginResponse;
import br.unb.cic.bionimbuz.rest.response.LogoutResponse;
import br.unb.cic.bionimbuz.rest.response.SignUpResponse;
import br.unb.cic.bionimbuz.rest.response.StartWorkflowResponse;
import br.unb.cic.bionimbuz.rest.response.UploadResponse;

public class RestService {

    private final RestCommunicator restCommunicator;

    /**
     * Constructor that initializes the REST Communicator
     */
    public RestService() {
        restCommunicator = new RestCommunicator();
    }

    /**
     * Fires a Login request to the server
     *
     * @param user
     * @return
     * @throws ServerNotReachableException
     */
    public User login(User user) throws ServerNotReachableException {
        RequestInfo loginRequest = new LoginRequest(user);
        LoginResponse resp = (LoginResponse) restCommunicator.sendRequest(new Login(), loginRequest);

        return resp.getUser();
    }

    /**
     * Communicates an user logout to the server
     *
     * @param user
     * @return
     * @throws ServerNotReachableException
     */
    public boolean logout(User user) throws ServerNotReachableException {
        RequestInfo logoutRequest = new LogoutRequest(user);
        LogoutResponse resp = (LogoutResponse) restCommunicator.sendRequest(new Logout(), logoutRequest);

        return resp.isLogoutSuccess();
    }

    /**
     * Fires an Upload request to the server
     *
     * @param fileInfo
     * @return
     * @throws IOException
     */
    public boolean uploadFile(UploadedFileInfo fileInfo) throws Exception {
        RequestInfo uploadRequest = new UploadRequest(fileInfo);
        UploadResponse resp = (UploadResponse) restCommunicator.sendRequest(new Upload(), uploadRequest);

        if (!resp.isUploaded()) {
            throw new Exception("Failed to send file");
        }
        
        return true;
    }

    /**
     * Requests a file exclusion by the server
     *
     * @param fileInfo
     * @return
     * @throws ServerNotReachableException
     */
    public boolean deleteFile(UploadedFileInfo fileInfo) throws Exception {
        RequestInfo deleteFileRequest = new DeleteFileRequest(fileInfo);
        DeleteFileResponse response = (DeleteFileResponse) restCommunicator.sendRequest(new DeleteFile(), deleteFileRequest);

        return response.isDeleted();
    }

    /**
     * Requests an user signup
     *
     * @param user
     * @return
     * @throws ServerNotReachableException
     */
    public boolean signUp(User user) throws ServerNotReachableException {
        RequestInfo signUpRequest = new SignUpRequest(user);
        SignUpResponse response = (SignUpResponse) restCommunicator.sendRequest(new SignUp(), signUpRequest);

        return response.isAdded();
    }
    
    public boolean startWorkflow(Workflow workflow) throws ServerNotReachableException {
        RequestInfo startWorkflowRequest = new StartWorkflowRequest(workflow);
        StartWorkflowResponse response = (StartWorkflowResponse) restCommunicator.sendRequest(new StartWorkflow(), startWorkflowRequest);
        
        return response.isWorkflowProcessed();
    }
}
