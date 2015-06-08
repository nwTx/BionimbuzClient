package br.unb.cic.bionimbuz.rest.action;

import javax.ws.rs.client.Client;

import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.response.ResponseInfo;

public interface Action {

	public void setup(Client client, RequestInfo reqInfo);

	public void prepareTarget();

	public ResponseInfo execute() throws Exception;
}
