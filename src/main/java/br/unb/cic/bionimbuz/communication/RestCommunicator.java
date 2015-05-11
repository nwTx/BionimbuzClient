package br.unb.cic.bionimbuz.communication;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import br.unb.cic.bionimbuz.communication.rest.RestClient;
import br.unb.cic.bionimbuz.communication.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.communication.rest.response.ResponseInfo;

public class RestCommunicator implements Communicator{
	
	@Override
	public ResponseInfo sendRequest(RestClient restClient, RequestInfo requestInfo) {
		Client client = ClientBuilder.newClient();
		
		restClient.setup(client, requestInfo);
		restClient.prepareTarget();
		ResponseInfo response = restClient.execute();

		return response;
	}

}
