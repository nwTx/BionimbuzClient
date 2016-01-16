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
import br.unb.cic.bionimbuz.model.Job;
import br.unb.cic.bionimbuz.model.PluginService;
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

    @Inject
    private SessionBean sessionBean;

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowComposerBean.class);

    private final RestService restService;

    private final List<PluginService> servicesList;

    private ArrayList<DiagramElement> elements;

    private WorkflowDiagram workflowDiagram;

//    private ProgramInfo program;
    private PluginService service;

    private String workflowDescription;

    private boolean suspendEvent;

    private String clickedElementId;

    private String inputURL;

    private String arguments;

    private String dependency;

    private int jobListSize = 0;

    private ArrayList<FileInfo> inputFiles = new ArrayList<>();

    // Logged user
    private User loggedUser;

    public WorkflowComposerBean() {
        restService = new RestService();
        elements = new ArrayList<>();
        servicesList = ConfigurationRepository.getSupportedServices();
    }

    @PostConstruct
    public void init() {
        loggedUser = sessionBean.getLoggedUser();
    }

    /**
     * Adds an element to the chosen programs list
     *
     * @param service
     */
    public void addElement(PluginService service) {
        elements.add(new DiagramElement(service));

        showMessage("Elemento " + service.getName() + " adicionado");
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

            // Verifies if the user is entering workflow composer page
        } else if (toGoStep.equals("workflow_design")) {
            jobListSize = elements.size();

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

        // Add dependency
        if (((DiagramElement) event.getSourceElement().getData()).getName().equals("Inicio")
                || ((DiagramElement) event.getSourceElement().getData()).getName().equals("Fim")) {
            dependency = null;
        } else {
            dependency = ((DiagramElement) event.getSourceElement().getData()).getId();
        }

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
     * Sets the step fields (like, inputfiles, arguments, ...)
     */
    public void setJobFields() {
        // Sets element input list
        workflowDiagram.setJobFields(clickedElementId, inputFiles, arguments, inputURL, dependency);

        // Resets input list
        inputFiles = new ArrayList<>();
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

    public List<PluginService> getServicesList() {
        return servicesList;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setService(PluginService service) {
        this.service = service;
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

    public ArrayList<Job> getJobs() {
        return (ArrayList<Job>) this.workflowDiagram.getWorkflow().getJobs();
    }

    public Workflow getWorkflow() {
        return this.workflowDiagram.getWorkflow();
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

}
