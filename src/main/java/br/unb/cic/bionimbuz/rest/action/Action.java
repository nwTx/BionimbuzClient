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
     * @throws ServerNotReachableException
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
            w.printStackTrace();
            System.out.println(w.getMessage());

        } catch (ProcessingException p) {            
            p.printStackTrace();
            System.out.println(p.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            
        }

        // If Response is not 200, throws an Exception
        if (response.getStatus() != 200) {
            System.out.println("Resposta diferente de 200: " + response.getStatus());
            System.out.println("StatusInfo: " + response.getStatusInfo());
        }

        client.close();
        return false;
    }
    
    protected void logAction(String path) {
        LOGGER.info("Sending request to BioNimbuZ (path: " + path + ")");
    }
}
