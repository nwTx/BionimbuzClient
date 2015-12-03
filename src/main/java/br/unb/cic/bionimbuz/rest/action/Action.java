package br.unb.cic.bionimbuz.rest.action;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import br.unb.cic.bionimbuz.configuration.ApplicationConfiguration;
import br.unb.cic.bionimbuz.configuration.ConfigurationRepository;
import br.unb.cic.bionimbuz.exception.ServerNotReachableException;
import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.response.ResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Action {

    private static final Logger LOGGER = LoggerFactory.getLogger(Action.class);

    private final String PING_URL = "/rest/ping";

    protected ApplicationConfiguration appConfiguration = ConfigurationRepository.getApplicationConfiguration();

    protected RequestInfo request;

    protected WebTarget target;

    public abstract void setup(Client client, RequestInfo reqInfo);

    public abstract void prepareTarget();

    public abstract ResponseInfo execute();

    /**
     * Ping server
     *
     * @return
     */
    public boolean ping() {

        // Creates client
        Client client = ClientBuilder.newClient();

        WebTarget target = client.target(appConfiguration.getBionimbuzAddress());

        Response response = null;

        try {
            // Fires a GET request
            response = target
                    .path(PING_URL)
                    .request()
                    .get(Response.class);

        } catch (WebApplicationException w) {
            LOGGER.error("[WebApplicationException] " + w.getMessage());

        } catch (ProcessingException p) {
            LOGGER.error("[ProcessingException] " + p.getMessage());

        } catch (Exception e) {
            LOGGER.error("[Exception] " + e.getMessage());

        }

        // If Response is not 200, throws an Exception
        if (response.getStatus() != 200) {
            LOGGER.warn("Response different from HTTP 200 (OK). Received [status=" + response.getStatus()
                    + ", status-info=" + response.getStatusInfo() + "]");
        }

        client.close();
        return false;
    }

    /**
     * Logs the request action
     *
     * @param path
     * @param c
     */
    protected void logAction(String path, Class<? extends Action> c) {
        LOGGER.info("[" + c.getSimpleName() + "] Sending request to BioNimbuZ (path: " + path + ")");
    }
}
