package br.unb.cic.bionimbuz.web.beans;

import br.unb.cic.bionimbuz.model.Log;
import br.unb.cic.bionimbuz.model.Workflow;
import br.unb.cic.bionimbuz.rest.service.RestService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Controls workflow/history.xhtml page.
 *
 * @author Vinicius
 */
@Named
@SessionScoped
public class WorkflowHistoryBean implements Serializable {

    private RestService restService;
    private Workflow selectedWorkflow;
    private List<Log> history;

    @PostConstruct
    private void initialize() {
        restService = new RestService();
    }

    /**
     * Returns the color of a Status
     *
     * @return
     */
    public String getStatusColor() {
        return this.selectedWorkflow.getStatus().getColor();
    }

    /**
     * Returns the status text
     *
     * @return
     */
    public String getStatusText() {
        return this.selectedWorkflow.getStatus().toString();
    }

    /**
     * Calls server to retrieve workflow history log
     *
     * @param workflow
     * @return
     */
    public String selectWorkflow(Workflow workflow) {
        this.selectedWorkflow = workflow;

        try {
            this.history = restService.getWorkflowHistory(workflow.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "workflow_history";
    }

    /**
     * Calls server to refresh workflow history
     *
     * @param workflow
     */
    public void updateWorkflowHistory(Workflow workflow) {
        try {
            this.history = restService.getWorkflowHistory(workflow.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Workflow getSelectedWorkflow() {
        return selectedWorkflow;
    }

    public void setSelectedWorkflow(Workflow selectedWorkflow) {
        this.selectedWorkflow = selectedWorkflow;
    }

    public List<Log> getHistory() {
        return history;
    }

    public void setHistory(List<Log> history) {
        this.history = history;
    }

}
