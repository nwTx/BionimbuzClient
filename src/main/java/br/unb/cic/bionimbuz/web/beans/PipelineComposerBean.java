package br.unb.cic.bionimbuz.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.diagram.DefaultDiagramModel;

import br.unb.cic.bionimbuz.configuration.ConfigurationRepository;
import br.unb.cic.bionimbuz.model.UploadedFileInfo;
import br.unb.cic.bionimbuz.model.ProgramInfo;
import br.unb.cic.bionimbuz.model.User;
import br.unb.cic.bionimbuz.model.WorkflowDiagram;
import org.primefaces.context.RequestContext;
import org.primefaces.event.diagram.ConnectEvent;
import org.primefaces.event.diagram.ConnectionChangeEvent;
import org.primefaces.event.diagram.DisconnectEvent;

@Named
@SessionScoped
public class PipelineComposerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    SessionBean sessionBean;

    // List of programs
    private List<ProgramInfo> programList = ConfigurationRepository.getProgramList().getPrograms();

    // Workflow
    private WorkflowDiagram workflowDiagram;

    // Control attributes
    private boolean workflowFinished = false;
    private ProgramInfo program;
    private String workflowDescription;
    private boolean suspendEvent;    

    // Logged user
    private User loggedUser;

    @PostConstruct
    public void init() {
        this.loggedUser = sessionBean.getLoggedUser();
        this.workflowDiagram = new WorkflowDiagram(loggedUser, workflowDescription);
    }

    /**
     * Adds a sequential element to the workflow diagram
     */
    public void addElement(UploadedFileInfo inputFile) {
        workflowDiagram.addElement(this.program, inputFile);

        showMessage("Elemento " + program.getName() + " adicionado");
    }

    /**
     * Resets current workflow
     */
    public void resetWorkflow() {
        workflowDiagram.resetWorkflow();

        showMessage("Workflow reiniciado");
    }

    /**
     * Undo an element addition and updates the references
     */
    public void undoAddition() {
        workflowDiagram.undoAddition();

        showMessage("Ação desfeita");
    }

    /**
     * Show message in growl component (View)
     *
     * @param msg
     */
    private void showMessage(String msg) {
        FacesMessage message = new FacesMessage(msg, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void endWorkflow() {
        workflowDiagram.endWorkflow();

        // Finish workflow
        workflowFinished = true;

        showMessage("Workflow finalizado!");
    }

    public void onConnect(ConnectEvent event) { 
        if (!suspendEvent) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('file_dlg').show();");
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Connected",
                    "From " + event.getSourceElement().getData() + " To " + event.getTargetElement().getData());

            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {
            suspendEvent = false;
        }
    }

    public void onDisconnect(DisconnectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Disconnected",
                "From " + event.getSourceElement().getData() + " To " + event.getTargetElement().getData());

        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public void onConnectionChange(ConnectionChangeEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Connection Changed",
                "Original Source:" + event.getOriginalSourceElement().getData()
                + ", New Source: " + event.getNewSourceElement().getData()
                + ", Original Target: " + event.getOriginalTargetElement().getData()
                + ", New Target: " + event.getNewTargetElement().getData());

        FacesContext.getCurrentInstance().addMessage(null, msg);

        suspendEvent = true;
    }
    
    public DefaultDiagramModel getWorkflowModel() {
        return workflowDiagram.getWorkflow();
    }

    public List<ProgramInfo> getProgramList() {
        return programList;
    }

    public int getWorkflowIndex() {
        return workflowDiagram.getWorkflowIndex();
    }

    public boolean isWorkflowFinished() {
        return workflowFinished;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setProgram(ProgramInfo program) {
        this.program = program;
    }

    public String getWorkflowDescription() {
        return workflowDescription;
    }

    public void setWorkflowDescription(String workflowDescription) {
        this.workflowDescription = workflowDescription;
    }
}
