package br.unb.cic.bionimbuz.rest.action;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import br.unb.cic.bionimbuz.configuration.Configuration;
import br.unb.cic.bionimbuz.rest.request.LoginRequest;
import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.response.ResponseInfo;

public class Schedule extends Action {
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
