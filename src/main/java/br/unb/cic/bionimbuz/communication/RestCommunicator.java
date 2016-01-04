package br.unb.cic.bionimbuz.communication;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import br.unb.cic.bionimbuz.exception.ServerNotReachableException;
import br.unb.cic.bionimbuz.rest.action.Action;
import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.response.ResponseInfo;

/**
 * Executes the REST action. Send the request to the server and wait for a
 * response
 *
 * @author monstrim
 *
 */
public class RestCommunicator implements Communicator {

    /**
     * Send a request to the Bionimbuz main server to execute a requested
     * operation and send back a ResponseInfo object
     *
     * @param action
     * @param requestInfo
     * @return
     * @throws ServerNotReachableException
     */
    @Override
    public ResponseInfo sendRequest(Action action, RequestInfo requestInfo) throws ServerNotReachableException {
        Client client = ClientBuilder.newClient();

        action.setup(client, requestInfo);
        action.prepareTarget();
        action.ping();
        ResponseInfo response = action.execute();

        return response;
    }

}
