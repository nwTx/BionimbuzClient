package br.unb.cic.bionimbuz.communication;

import br.unb.cic.bionimbuz.configuration.ApplicationConfiguration;
import br.unb.cic.bionimbuz.configuration.ConfigurationRepository;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import br.unb.cic.bionimbuz.exception.ServerNotReachableException;
import br.unb.cic.bionimbuz.rest.action.Action;
import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.response.ResponseInfo;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Executes the REST action. Send the request to the server and wait for a
 * response
 *
 * @author monstrim
 *
 */
public class RestCommunicator implements Communicator {

    private final String PING_URL = "/rest/ping";

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

    /**
     * Ping server
     *
     * @param bionimbuzAddress
     * @return
     */
    public boolean ping(String bionimbuzAddress) {
        boolean result = false;

        // Creates client
        Client client = ClientBuilder.newClient();

        WebTarget pingTarget = client.target(bionimbuzAddress);

        try {
            // Fires a GET request
            Response response = pingTarget
                    .path(PING_URL)
                    .request()
                    .get(Response.class);

            return (response.getStatus() == 200);
        } catch (Exception e) {
            throw e;
        }
    }

}
