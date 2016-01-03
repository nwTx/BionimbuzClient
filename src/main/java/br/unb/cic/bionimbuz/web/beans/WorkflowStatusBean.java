package br.unb.cic.bionimbuz.web.beans;

import br.unb.cic.bionimbuz.exception.ServerNotReachableException;
import br.unb.cic.bionimbuz.model.Workflow;
import br.unb.cic.bionimbuz.model.WorkflowStatus;
import br.unb.cic.bionimbuz.rest.service.RestService;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls workflow/status.xhtml page
 *
 * @author Vinicius
 */
@Named
@SessionScoped
public class WorkflowStatusBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowStatusBean.class);

    @Inject
    private SessionBean sessionBean;

    private List<Workflow> userWorkflows;
    private final RestService restService = new RestService();

    public WorkflowStatusBean() {
        try {
            System.out.println("RestService: " + (restService == null));
            System.out.println("User: " + (sessionBean == null));

            userWorkflows = restService.getWorkflowStatus(sessionBean.getLoggedUser());
        } catch (Exception e) {
            LOGGER.error("[Exception] " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns the color of a Status
     *
     * @param status
     * @return
     */
    public String getStatusColor(WorkflowStatus status) {
        return status.getColor();
    }

    /**
     * Returns the status text
     *
     * @param status
     * @return
     */
    public String getStatusText(WorkflowStatus status) {
        return status.toString();
    }

    /**
     * Returns user workflow list
     *
     * @return
     */
    public List<Workflow> getUserWorkflows() {
        try {
            return restService.getWorkflowStatus(sessionBean.getLoggedUser());
        } catch (Exception ex) {
            LOGGER.error("[Exception] " + ex.getMessage());
            ex.printStackTrace();

            return null;
        }
    }

}
