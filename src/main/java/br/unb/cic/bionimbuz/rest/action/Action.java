package br.unb.cic.bionimbuz.rest.action;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import br.unb.cic.bionimbuz.configuration.Configuration;
import br.unb.cic.bionimbuz.configuration.ConfigurationLoader;
import br.unb.cic.bionimbuz.exception.ServerNotReachableException;
import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.response.ResponseInfo;

public abstract class Action {
	private final String PING_URL = "/rest/ping";
	
	public abstract void setup(Client client, RequestInfo reqInfo);

	public abstract void prepareTarget();

	public abstract ResponseInfo execute();
	
	/**
	 * Ping server 
	 * @return
	 * @throws ServerNotReachableException
	 */
	public boolean ping() throws ServerNotReachableException {
		// Creates client
		Client client = ClientBuilder.newClient();

		Configuration config = ConfigurationLoader.readConfiguration();
		WebTarget target = client.target(config.getBionimbuzAddress());
		
		// Fires a GET request
		Response response = target
				.path(PING_URL)
				.request()
				.get(Response.class);
		
		// If Response is not 200, throws an Exception
		if (response.getStatus() != 200) {
				throw new ServerNotReachableException("Server is not reachable (Response not equal 200)");
		}
		
		client.close();
		return false;
	}
}
