package br.unb.cic.bionimbuz.rest.action;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.unb.cic.bionimbuz.rest.request.DeleteFileRequest;
import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.response.DeleteFileResponse;

public class DeleteFile extends Action {
	private static final String REST_DELETE_FILE_URL = "/rest/file/"; 
	private Long fileId;
	
	@Override
	public void setup(Client client, RequestInfo reqInfo) {
		System.out.println("Delete File REST client initialized...");

		this.target = client.target(appConfiguration.getBionimbuzAddress());
		this.request = (DeleteFileRequest) reqInfo;
		this.fileId = ((DeleteFileRequest) request).getFileInfo().getId();
	}

	@Override
	public void prepareTarget() {
		// Path: /rest/file/{fileId}
		System.out.println(this.fileId);
		target = target.path(REST_DELETE_FILE_URL + this.fileId);
		
		
		System.out.println("Dispatching request to URI (POST): " + target.getUri());
	}

	@Override
	public DeleteFileResponse execute() {
		Response response = target
				.request(MediaType.APPLICATION_JSON)
				.delete();
		
		System.out.println(response.getStatus());
		
		return null;
	}

}
