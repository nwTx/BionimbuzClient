package br.unb.cic.bionimbuz.communication;

import br.unb.cic.bionimbuz.communication.rest.RestClient;
import br.unb.cic.bionimbuz.communication.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.communication.rest.response.ResponseInfo;

public interface Communicator {

	public ResponseInfo sendRequest(RestClient restClient, RequestInfo requestInfo);

}
