package br.unb.cic.bionimbuz.rest.response;

import br.unb.cic.bionimbuz.model.Log;
import java.util.List;

/**
 *
 * @author Vinicius
 */
public class GetWorkflowHistoryResponse implements ResponseInfo {

    private List<Log> history;

    public GetWorkflowHistoryResponse() {
    }

    public List<Log> getHistory() {
        return history;
    }

    public void setHistory(List<Log> history) {
        this.history = history;
    }

}
