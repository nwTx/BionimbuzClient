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

@Named
@SessionScoped
public class PipelineComposerBean_old implements Serializable {

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
    public void addSequentialElement(UploadedFileInfo inputFile) {
//        workflowDiagram.addSequentialElement(this.program, inputFile);

        showMessage("Elemento " + program.getName() + " adicionado");
    }

    /**
     * Add parallel element to the workflow diagram
     *
     * @param program
     */
    public void addParallelElement(String program) {
//        workflowDiagram.addParallelElement(program);

        showMessage("Elemento " + program + " adicionado");
    }

    /**
     * Resets current workflow
     */
    public void resetWorkflow() {
//        workflowDiagram.resetWorkflow();

        showMessage("Workflow reiniciado");
    }

    /**
     * Undo an element addition and updates the references
     */
    public void undoAddition() {
//        workflowDiagram.undoAddition();

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
//        workflowDiagram.endWorkflow();

        // Finish workflow
        workflowFinished = true;

        showMessage("Workflow finalizado!");
    }

    public DefaultDiagramModel getWorkflowModel() {
        return workflowDiagram.getWorkflow();
    }

    public List<ProgramInfo> getProgramList() {
        return programList;
    }

    public int getWorkflowIndex() {
//        return workflowDiagram.getWorkflowIndex();
        return 1;
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
