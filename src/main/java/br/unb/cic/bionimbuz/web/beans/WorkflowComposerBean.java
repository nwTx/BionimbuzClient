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
import br.unb.cic.bionimbuz.exception.ServerNotReachableException;
import br.unb.cic.bionimbuz.model.DiagramElement;
import br.unb.cic.bionimbuz.model.WorkflowJobInfo;
import br.unb.cic.bionimbuz.model.ProgramInfo;
import br.unb.cic.bionimbuz.model.User;
import br.unb.cic.bionimbuz.model.Workflow;
import br.unb.cic.bionimbuz.model.WorkflowDiagram;
import br.unb.cic.bionimbuz.rest.service.RestService;
import java.util.ArrayList;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.diagram.ConnectEvent;
import org.primefaces.event.diagram.ConnectionChangeEvent;
import org.primefaces.event.diagram.DisconnectEvent;
import br.unb.cic.bionimbuz.model.FileInfo;
import java.net.MalformedURLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls workflow_composer.xhtml page
 *
 * @author Vinicius
 */
@Named
@SessionScoped
public class WorkflowComposerBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowComposerBean.class);

    private static final long serialVersionUID = 1L;
    private final RestService restService;

    @Inject
    private SessionBean sessionBean;

    // List of programs
    private final List<ProgramInfo> programList;
    private ArrayList<DiagramElement> elements;

    // Workflow
    private WorkflowDiagram workflowDiagram;

    // Control attributes
    private ProgramInfo program;
    private String workflowDescription;
    private boolean suspendEvent;
    private String clickedElementId;
    private String inputURL;
    private ArrayList<FileInfo> inputFiles = new ArrayList<>();

    // Logged user
    private User loggedUser;

    public WorkflowComposerBean() {
        restService = new RestService();
        elements = new ArrayList<>();
        programList = ConfigurationRepository.getProgramList().getPrograms();
    }

    @PostConstruct
    public void init() {
        loggedUser = sessionBean.getLoggedUser();
    }

    /**
     * Adds an element to the chosen programs list
     *
     * @param info
     */
    public void addElement(ProgramInfo info) {
        elements.add(new DiagramElement(info));

        showMessage("Elemento " + info.getName() + " adicionado");
    }

    /**
     * Removes an element from the chosen programs list
     *
     * @param element
     */
    public void removeElement(DiagramElement element) {
        elements.remove(element);

        showMessage("Elemento " + element.getName() + " removido");
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

    /**
     * Controlls Wizard flow. It is linked with the HTML page that renders the
     * diagram and contains Wizard PrimeFaces component.
     *
     * @param event
     * @return
     */
    public String flowController(FlowEvent event) {
        String currentStep = event.getOldStep();
        String toGoStep = event.getNewStep();

        // Resets Workflow
        if (toGoStep.equals("element_selection")) {

            // Verifies if the user is entering pipeline composer page
        } else if (toGoStep.equals("workflow_design")) {
            if (elements.isEmpty()) {
                showMessage("Workflow vazio! Favor selecionar elementos");

                return currentStep;
            }
            try {
                // Creates workflow diagram
                workflowDiagram = new WorkflowDiagram(loggedUser.getId(), workflowDescription, elements);

            } catch (MalformedURLException e) {
                LOGGER.error("[MalformedURLException] " + e.getMessage());
            }
        }

        return toGoStep;
    }

    /**
     * Server side method called when an element is connected to another
     *
     * @param event
     */
    public void onConnect(ConnectEvent event) {
        DiagramElement clickedElement = ((DiagramElement) event.getTargetElement().getData());

        if (!suspendEvent && (!clickedElement.getName().equals("Inicio")) && (!clickedElement.getName().equals("Fim"))) {
            RequestContext context = RequestContext.getCurrentInstance();

            // Call input pick painel
            context.execute("PF('file_dlg').show();");

            // Sets clicked element id to be used to set element input file
            clickedElementId = ((DiagramElement) event.getTargetElement().getData()).getId();

        } else {
            suspendEvent = false;
        }
    }

    /**
     * Server side method called when a connection is changed from an element to
     * another
     *
     * @param event
     */
    public void onDisconnect(DisconnectEvent event) {
        DiagramElement clickedElement = ((DiagramElement) event.getTargetElement().getData());

        if (!suspendEvent && (!clickedElement.getName().equals("Inicio")) && (!clickedElement.getName().equals("Fim"))) {
            RequestContext context = RequestContext.getCurrentInstance();

            // Call input pick painel
            context.execute("PF('file_dlg').show();");

            // Sets clicked element id to be used to set element input file
            clickedElementId = ((DiagramElement) event.getTargetElement().getData()).getId();

        } else {
            suspendEvent = false;
        }
    }

    /**
     * Server side method called when a connection is taken to another element
     *
     * @param event
     */
    public void onConnectionChange(ConnectionChangeEvent event) {

        suspendEvent = true;
    }

    /**
     * Sets an file input for a worflow step
     */
    public void setInputFile() {
        // Sets element input list
        workflowDiagram.setInputFile(clickedElementId, inputFiles);

        // Resets input list
        inputFiles = new ArrayList<>();
    }

    /**
     * Sets an URL as a step input
     */
    public void setURL() {
        System.out.println("Set URL \"" + inputURL + "\" as input for element " + clickedElementId);

    }

    /**
     * Send out workflow to be processed by BioNimbuZ core
     */
    public void startWorkflow() {
        try {
            // Calls RestService to send the workflow to core
            restService.startWorkflow(workflowDiagram.getWorkflow());

            // Updates user workflow list
            sessionBean.getLoggedUser().getWorkflows().add(workflowDiagram.getWorkflow());
        } catch (ServerNotReachableException e) {
            LOGGER.error("[ServerNotReachableException] " + e.getMessage());
        }
    }

    /**
     * Returns workflow status as a String
     *
     * @return
     */
    public String getWorkflowStatus() {
        return this.workflowDiagram.getWorkflow().getStatus().toString();
    }

    /**
     * Returns the color of the status
     *
     * @return
     */
    public String getWorkflowColor() {
        return this.workflowDiagram.getWorkflow().getStatus().getColor();
    }

    public DefaultDiagramModel getWorkflowModel() {
        return workflowDiagram.getWorkflowModel();
    }

    public List<ProgramInfo> getProgramList() {
        return programList;
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

    public List<DiagramElement> getElements() {
        return elements;
    }

    public void setElements(ArrayList<DiagramElement> elements) {
        this.elements = elements;
    }

    public String getInputURL() {
        return inputURL;
    }

    public void setInputURL(String inputURL) {
        this.inputURL = inputURL;
    }

    public void setInputFiles(ArrayList<FileInfo> inputFiles) {
        this.inputFiles = inputFiles;
    }

    public ArrayList<FileInfo> getInputFiles() {
        return inputFiles;
    }

    public ArrayList<WorkflowJobInfo> getPipeline() {
        return (ArrayList<WorkflowJobInfo>) this.workflowDiagram.getWorkflow().getPipeline();
    }

    public Workflow getWorkflow() {
        return this.workflowDiagram.getWorkflow();
    }
}
