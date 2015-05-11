package br.unb.cic.bionimbuz.communication.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import br.unb.cic.bionimbuz.communication.rest.RestClient;
import br.unb.cic.bionimbuz.communication.rest.request.LoginRequest;
import br.unb.cic.bionimbuz.communication.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.communication.rest.response.ResponseInfo;
import br.unb.cic.bionimbuz.configuration.Configuration;

public class SchedRestClient implements RestClient {
	private static final String SCHED_LOGIN_URL = "/rest/sched";
	private LoginRequest loginRequest;
	private WebTarget target;
	private Configuration config;
	
	@Override
	public void setup(Client client, RequestInfo reqInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepareTarget() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResponseInfo execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
