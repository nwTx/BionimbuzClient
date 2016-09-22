package br.unb.cic.bionimbuz.rest.response;

import br.unb.cic.bionimbuz.model.Instance;
import br.unb.cic.bionimbuz.model.PluginService;
import java.util.List;

/**
 *
 * @author Vinicius
 */
public class GetConfigurationsResponse implements ResponseInfo {

    private List<PluginService> servicesList;

    private List<String> references;

    private List<String> supportedFormats;
    
    //private List<Instance> instancesList;

    public GetConfigurationsResponse() {
    }
    //List<Instance> instanceList
    public GetConfigurationsResponse(List<PluginService> servicesList, List<String> references, List<String> supportedFormats) {
        this.servicesList = servicesList;
        this.references = references;
        this.supportedFormats = supportedFormats;
    //    this.instancesList= instanceList;
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

    public List<String> getSupportedFormats() {
        return supportedFormats;
    }

    public void setSupportedFormats(List<String> supportedFormats) {
        this.supportedFormats = supportedFormats;
    }

    /**
     * @return the InstancesList
     */
//    public List<Instance> getInstancesList() {
//        return instancesList;
//    }

    /**
     * @param instancesList the InstancesList to set
     */
//    public void setInstancesList(List<Instance> instancesList) {
//        this.instancesList = instancesList;
//    }

}
