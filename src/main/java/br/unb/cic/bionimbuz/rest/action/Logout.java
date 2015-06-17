package br.unb.cic.bionimbuz.rest.action;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import br.unb.cic.bionimbuz.configuration.Configuration;
import br.unb.cic.bionimbuz.configuration.ConfigurationLoader;
import br.unb.cic.bionimbuz.rest.request.LogoutRequest;
import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.response.LogoutResponse;

public class Logout extends Action {
	private static final String REST_LOGOUT_URL = "/rest/logout";
	private LogoutRequest logoutRequest;
	private WebTarget target;
	private Configuration config;

	@Override
	public void setup(Client client, RequestInfo reqInfo) {
		System.out.println("Logout REST client initialized...");

		this.config = ConfigurationLoader.readConfiguration();
		this.target = client.target(config.getBionimbuzAddress());
		this.logoutRequest = (LogoutRequest) reqInfo;
	}

	@Override
	public void prepareTarget() {
		target = target.path(REST_LOGOUT_URL);
		
		System.out.println("Dispatching request to URI (POST): " + target.getUri());
	}

	@Override
	public LogoutResponse execute() {
		LogoutResponse response = target
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(logoutRequest, MediaType.APPLICATION_JSON), LogoutResponse.class);
		
		return response;
	}

}
