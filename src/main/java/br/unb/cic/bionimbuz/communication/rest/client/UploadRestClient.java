package br.unb.cic.bionimbuz.communication.rest.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

import br.unb.cic.bionimbuz.communication.rest.RestClient;
import br.unb.cic.bionimbuz.communication.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.communication.rest.request.UploadRequest;
import br.unb.cic.bionimbuz.communication.rest.response.UploadResponse;
import br.unb.cic.bionimbuz.configuration.Configuration;
import br.unb.cic.bionimbuz.configuration.ConfigurationLoader;

public class UploadRestClient implements RestClient {
	private static final String REST_UPLOAD_URL = "/rest/upload";
	private UploadRequest uploadRequest;
	private WebTarget target;
	private Configuration config;
	
	@Override
	public void setup(Client client, RequestInfo requestInfo) {
		System.out.println("Upload REST client initialized...");
		
		this.config = ConfigurationLoader.readConfiguration();
		this.target = client.target(config.getBionimbuzAddress());
		this.uploadRequest = (UploadRequest) requestInfo;
	}

	@Override
	public void prepareTarget() {
		target = target.path(REST_UPLOAD_URL);
			
		System.out.println("Dispatching request to URI: " + target.getUri());
	}

	@Override
	public UploadResponse execute() {		
	    MultipartFormDataOutput multipart = new MultipartFormDataOutput();
	    
	    try {
			multipart.addFormData("file", new FileInputStream(new File(uploadRequest.getFilepath())), MediaType.MULTIPART_FORM_DATA_TYPE);
			multipart.addFormData("filename", uploadRequest.getFilename(), MediaType.TEXT_PLAIN_TYPE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	    GenericEntity<MultipartFormDataOutput> entity = new GenericEntity<MultipartFormDataOutput>(multipart) {};

	    UploadResponse response = target.request().post(Entity.entity(entity, MediaType.MULTIPART_FORM_DATA), UploadResponse.class);
	    
	    return response;
	}
}
