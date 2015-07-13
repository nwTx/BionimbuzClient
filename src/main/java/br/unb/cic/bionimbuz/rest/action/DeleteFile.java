package br.unb.cic.bionimbuz.rest.action;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import br.unb.cic.bionimbuz.rest.request.DeleteFileRequest;
import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.response.DeleteFileResponse;

public class DeleteFile extends Action {
	private static final String REST_DELETE_FILE_URL = "/rest/file/"; 
	
	@Override
	public void setup(Client client, RequestInfo reqInfo) {
		System.out.println("Login REST client initialized...");

		this.target = client.target(appConfiguration.getBionimbuzAddress());
		this.request = (DeleteFileRequest) reqInfo;
	}

	@Override
	public void prepareTarget() {
		target = target.path(REST_DELETE_FILE_URL);
		
		System.out.println("Dispatching request to URI (POST): " + target.getUri());
	}

	@Override
	public DeleteFileResponse execute() {
		DeleteFileResponse response = target
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(request, MediaType.APPLICATION_JSON), DeleteFileResponse.class);
		
		return response;
	}

}
