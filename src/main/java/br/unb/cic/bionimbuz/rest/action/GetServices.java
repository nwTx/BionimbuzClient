package br.unb.cic.bionimbuz.rest.action;

import br.unb.cic.bionimbuz.model.PluginService;
import br.unb.cic.bionimbuz.rest.request.GetServicesRequest;
import br.unb.cic.bionimbuz.rest.request.RequestInfo;
import br.unb.cic.bionimbuz.rest.response.GetServicesResponse;
import br.unb.cic.bionimbuz.rest.response.ResponseInfo;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Send a request to the server to get the supported Services
 *
 * @author Vinicius
 */
public class GetServices extends Action {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetServices.class);
    private static final String REST_GET_SERVICES_URL = "/rest/services";

    @Override
    public void setup(Client client, RequestInfo reqInfo) {
        this.target = client.target(appConfiguration.getBionimbuzAddress());
        this.request = (GetServicesRequest) reqInfo;
    }

    @Override
    public void prepareTarget() {
        target = target.path(REST_GET_SERVICES_URL);
    }

    @Override
    public ResponseInfo execute() {
        logAction(REST_GET_SERVICES_URL, GetServices.class);

        try {
            Response r = target
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(request, MediaType.APPLICATION_JSON), Response.class);
            
            List<PluginService> response = r.readEntity(new GenericType<List<PluginService>>() {});    
            
            LOGGER.info("Received from server: " + response.size());
            
            return new GetServicesResponse(response);
        } catch (Exception e) {
            LOGGER.error("[Exception] " + e.getMessage());
            e.printStackTrace();
            
            return null;
        }

    }
}