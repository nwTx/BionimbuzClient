package br.unb.cic.bionimbuz.rest.request;

/**
 *
 * @author Vinicius
 */
public class GetServicesRequest implements RequestInfo {

    private boolean request;

    public GetServicesRequest() {
    }

    public GetServicesRequest(boolean request) {
        this.request = request;
    }

    public boolean isRequest() {
        return request;
    }

    public void setRequest(boolean request) {
        this.request = request;
    }
}
