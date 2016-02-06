package br.unb.cic.bionimbuz.rest.response;

import br.unb.cic.bionimbuz.model.FileInfo;
import br.unb.cic.bionimbuz.model.Log;
import java.util.List;

/**
 *
 * @author Vinicius
 */
public class GetWorkflowHistoryResponse implements ResponseInfo {

    private List<Log> history;

    // List with output files for the given workflow
    private List<FileInfo> outputFiles;

    public GetWorkflowHistoryResponse() {
    }

    public GetWorkflowHistoryResponse(List<Log> history, List<FileInfo> outputFiles) {
        this.history = history;
        this.outputFiles = outputFiles;
    }

    public List<Log> getHistory() {
        return history;
    }

    public void setHistory(List<Log> history) {
        this.history = history;
    }

    public List<FileInfo> getOutputFiles() {
        return outputFiles;
    }

    public void setOutputFiles(List<FileInfo> outputFiles) {
        this.outputFiles = outputFiles;
    }

}
