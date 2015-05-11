package br.unb.cic.bionimbuz.communication.rest;

import javax.ws.rs.client.Client;

import br.unb.cic.bionimbuz.communication.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.communication.rest.response.ResponseInfo;

public interface RestClient {

	public void setup(Client client, RequestInfo reqInfo);

	public void prepareTarget();

	public ResponseInfo execute();
}
