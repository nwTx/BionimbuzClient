package br.unb.cic.bionimbuz.rest.response;

import br.unb.cic.bionimbuz.model.PluginService;
import java.util.List;

/**
 *
 * @author Vinicius
 */
public class GetServicesResponse implements ResponseInfo {

    private List<PluginService> servicesList;

    private List<String> references;

    public GetServicesResponse() {
    }

    public GetServicesResponse(List<PluginService> servicesList, List<String> references) {
        this.servicesList = servicesList;
        this.references = references;
    }

    public List<PluginService> getServicesList() {
        return servicesList;
    }

    public void setServicesList(List<PluginService> servicesList) {
        this.servicesList = servicesList;
    }

    public List<String> getReferences() {
        return references;
    }

    public void setReferences(List<String> references) {
        this.references = references;
    }

}
