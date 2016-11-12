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
import br.unb.cic.bionimbuz.model.Instance;
import br.unb.cic.bionimbuz.model.Job;
import br.unb.cic.bionimbuz.model.PluginService;
import br.unb.cic.bionimbuz.model.SLA;
import br.unb.cic.bionimbuz.model.WorkflowStatus;
import br.unb.cic.bionimbuz.security.PBKDF2;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
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

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowComposerBean.class);
    private static final String PROVIDER = "BioNimbuZ";
    String provider =PROVIDER;
    String objetive;
    @Inject
    private SessionBean sessionBean;

//    @Inject
//    private SlaComposerBean slacomp;
    private final RestService restService;
    private final List<PluginService> servicesList;
    private ArrayList<DiagramElement> elements;
    private WorkflowDiagram workflowDiagram;
    private PluginService service;
    private String workflowDescription;
    private boolean suspendEvent;
    private String clickedElementId;
    private String inputURL;
    private String arguments;
    private String dependency;
    private int jobListSize = 0;
    private String currentJobOutput = "";
    private ArrayList<FileInfo> inputFiles = new ArrayList<>();
    private final ArrayList<String> references;
    private String chosenReference = "";
    private DiagramElement clickedElement;
    private final List<String> supportedFormats;
    private String fileFormat;
    private SLA sla,template;

    //----------------------- SLA Declarations------------------
    private String panel1 = "Hide-Panel1";
    private boolean limitation=false;
    private Integer limitationType;
    private String limitationValueExecutionTime;
    private String limitationValueExecutionCost;
    private List<Instance> instances;
    private List<Instance> selectedInstances;
    private Instance instance;
    private String chosenInstanceId;
    private Integer quantity;
    private Integer objective;
    //---------------------------------------------------------

    // Used by the user to download a workflow
    private StreamedContent workflowToDownload;

    // Logged user
    private User loggedUser;

    public WorkflowComposerBean() {
        restService = new RestService();
        elements = new ArrayList<>();
        servicesList = ConfigurationRepository.getSupportedServices();
        references = ConfigurationRepository.getReferences();
        supportedFormats = ConfigurationRepository.getSupportedFormats();
        instances = ConfigurationRepository.getInstances();
    }

    @PostConstruct
    public void init() {
        loggedUser = sessionBean.getLoggedUser();

        //------------- SLA inicialization------------------
        selectedInstances = new ArrayList<>();
//        instances.add(new Instance("Micro", 0.03, 10, "Brazil", 1.0, 3.3, "Xeon", 1, 20.0, "sata"));
//        instances.add(new Instance("Macro", 0.24, 5, "us-west", 4.0, 3.3, "Xeon", 4, 120.0, "sata"));
//        instances.add(new Instance("Large", 0.41, 3, "us-west", 8.0, 3.3, "Xeon", 8, 240.0, "sata"));
        limitation=false;

        //--------------------------------------------------
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

        if (toGoStep.equals("template")) {
            String provider;
////    //        System.out.println("peguei: "+slacomp.getSelectedInstancies().get(0).toString());

            selectedInstances.stream().forEach((f) -> {
                System.out.println(f.toString());
            });
            System.out.println("objective: "+getObjective());
            System.out.println("getLimitationValueExecutionCost:"+this.getLimitationValueExecutionCost());
            System.out.println("getLimitationValueExecutionTime:"+this.getLimitationValueExecutionTime());
            //TODO: alterar para o usuário bionimbuz depois da implementação do cadastro de usuário
            
            
////            System.out.println(sla.getId());
//                provider = new User("bionimbuz", PBKDF2.generatePassword("@BioNimbuZ!"), "BioNimbuZ", "71004832206", "bionimbuz@gmail.com", "0");

            try {
                sla= new SLA(this,loggedUser, getProvider(),this.getServicesList());
                template = new SLA(restService.startSla(sla, workflowDiagram.getWorkflow()));                   
            }catch (ServerNotReachableException ex) {
                java.util.logging.Logger.getLogger(WorkflowComposerBean.class.getName()).log(Level.SEVERE, null, ex);
            }
//            sla= new SLA(this,loggedUser,loggedUser,this.getServicesList());
//            System.out.println(sla.getId());
            
//            try {
//                restService.startSla(sla,workflowDiagram.getWorkflow());
//            } catch (ServerNotReachableException ex) {
//                java.util.logging.Logger.getLogger(WorkflowComposerBean.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        //Módulo Michel
        if(toGoStep.equals("SLA")){
            
        }
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

            // Sets clicked element id to be used to set element input file
            clickedElementId = ((DiagramElement) event.getTargetElement().getData()).getId();
            this.clickedElement = (DiagramElement) event.getTargetElement().getData();

            // Call input pick painel
            context.execute("PF('file_dlg').show();");

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

            // Sets clicked element id to be used to set element input file
            clickedElementId = ((DiagramElement) event.getTargetElement().getData()).getId();

            // Call input pick painel
            context.execute("PF('file_dlg').show();");

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
        workflowDiagram.setJobFields(clickedElementId, inputFiles, chosenReference, arguments, inputURL, dependency, clickedElement.getName(), fileFormat);

        // Saves current job output filename in case the next job uses it as input
        currentJobOutput = clickedElement.getName() + "_output_" + clickedElementId + fileFormat;

        // Resets fields
        inputFiles = new ArrayList<>();
        arguments = "";
        inputURL = "";
        fileFormat = "";
    }

    /**
     * Sets job fields when the "jump" button is pressed
     */
    public void setJobFieldsWithOutput() {
        // Creates the input file as the output file from the last Job. Need 
        // fake values, because of "null of string" exception of Avro.
        FileInfo file = new FileInfo();
        file.setName(currentJobOutput);
        file.setHash("");
        file.setSize(0l);
        file.setUploadTimestamp("");
        file.setUserId(sessionBean.getLoggedUser().getId());

        // Creates the input file list with the file info
        ArrayList<FileInfo> inputs = new ArrayList<>();
        inputs.add(file);

        // Sets element input list
        workflowDiagram.setJobFields(clickedElementId, inputs, chosenReference, arguments, inputURL, dependency, clickedElement.getName(), fileFormat);

        // Saves current job output filename in case the next job uses it as input
        currentJobOutput = clickedElement.getName() + "_output_" + clickedElementId + fileFormat;

        // Resets fields
        inputFiles = new ArrayList<>();
        arguments = "";
        inputURL = "";
        fileFormat = "";
    }

    /**
     * Send out workflow to be processed by BioNimbuZ core
     *
     * @return
     */
    public String startWorkflow() {
        try {
            // Calls RestService to send the workflow to core
            if (restService.startWorkflow(workflowDiagram.getWorkflow())) {

                // Updates user workflow list
                workflowDiagram.getWorkflow().setStatus(WorkflowStatus.EXECUTING);
                sessionBean.getLoggedUser().getWorkflows().add(workflowDiagram.getWorkflow());

                return "start_success";
            }
        } catch (ServerNotReachableException e) {
            LOGGER.error("[ServerNotReachableException] " + e.getMessage());
        }

        return "start_error";
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

    /**
     * Method used for users to download a .json workflow representation
     *
     * @return
     */
    public StreamedContent downloadWorkflow() {
        InputStream stream;
        ObjectMapper mapper = new ObjectMapper();
        DefaultStreamedContent content = null;
        String path = ConfigurationRepository.TEMPORARY_WORKFLOW_PATH + this.workflowDiagram.getWorkflow().getId() + ".json";
        File jsonFile = null;

        try {
            jsonFile = new File(path);
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, this.workflowDiagram.getWorkflow());

            FileInputStream is = new FileInputStream(jsonFile);
            content = new DefaultStreamedContent(is, "application/json", this.workflowDiagram.getWorkflow().getId() + ".flow");
        } catch (IOException ex) {
            LOGGER.error("[IOException] - " + ex.getMessage());
        } finally {
            jsonFile.delete();
        }

        return content;
    }

    /**
     * Called when an user clicks to import a workflow file to application
     *
     * @param event
     */
    public void handleImportedWorkflow(FileUploadEvent event) {
        try {
            String path = ConfigurationRepository.TEMPORARY_WORKFLOW_PATH + event.getFile().getFileName();

            File workflowFile = new File(path);

            BufferedReader br = new BufferedReader(new FileReader(workflowFile));

            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                builder.append(line);
            }

            ObjectMapper mapper = new ObjectMapper();
            Workflow workflow = mapper.readValue(builder.toString(), Workflow.class);

            System.out.println("Tamanho: " + workflow.getJobs().size());
            System.out.println("Descriçao: " + workflow.getDescription());
            System.out.println("UserId: " + workflow.getUserId());

        } catch (Exception ex) {
            LOGGER.error("[Exception] - " + ex.getMessage());
        }

    }

    /**
     * COMMENTED BECAUSE IT IS BEEN PASSED DIRECTLY TO THE SERVER (WITHOUT SAVE
     * IN DISK) Saves temporary file in disk
     *
     * @param fileName
     * @param in
     * @return public String saveTempFile(String fileName, InputStream in)
     * throws FileNotFoundException, IOException { String path =
     * ConfigurationRepository.TEMPORARY_WORKFLOW_PATH + fileName; // write the
     * inputStream to a FileOutputStream OutputStream outputStream = new
     * FileOutputStream(new File(path));
     *
     * int read = 0; byte[] bytes = new byte[1024];
     *
     * while ((read = in.read(bytes)) != -1) { outputStream.write(bytes, 0,
     * read); }
     *
     * // in.close(); LOGGER.info("Temporary file created [path=" +
     * ConfigurationRepository.UPLOADED_FILES_PATH + fileName + "]");
     *
     * return path; }
     */
    public StreamedContent getWorkflowToDownload() {
        return workflowToDownload;
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

    public ArrayList<String> getReferences() {
        return references;
    }

    public String getChosenReference() {
        return chosenReference;
    }

    public void setChosenReference(String chosenReference) {
        this.chosenReference = chosenReference;
    }

    public List<String> getSupportedFormats() {
        return supportedFormats;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

//-----------------------------SLA Methods---------------------------------
    public SLA getSla() {
        sla = new SLA(this, loggedUser, getProvider(), servicesList);
        return sla;
    }

    public void setPanel1(String panel1) {
        this.panel1 = panel1;
    }

    public String getPanel1() {
        return this.panel1;
    }

    /**
     * @return the limitation
     */
    public boolean isLimitation() {
        return limitation;
    }

    /**
     * @param limitation the limitation to set
     */
    public void setLimitation(boolean limitation) {
        this.limitation = limitation;
    }

    /**
     * @return the limitationType
     */
    public Integer getLimitationType() {
        return limitationType;
    }

    /**
     * @param limitationType the limitationType to set
     */
    public void setLimitationType(Integer limitationType) {
        this.limitationType = limitationType;
    }


    /**
     * @return the instances
     */
    public List<Instance> getInstancies() {
        return instances;
    }

    /**
     * @param instancies the instances to set
     */
    public void setInstancies(List<Instance> instancies) {
        this.instances = instancies;
    }

    /**
     * @return the selectedInstances
     */
    public List<Instance> getSelectedInstancies() {
        return selectedInstances;
    }

    /**
     * @param selectedInstancies the selectedInstances to set
     */
    public void setSelectedInstancies(List<Instance> selectedInstancies) {
        this.selectedInstances = selectedInstancies;
    }

    public List<String> getListInstancesString() {
        List<String> instancesString = new ArrayList<>();
        instances.stream().forEach((i) -> {
            instancesString.add(i.toString());
        });

        return instancesString;
    }

    public void adSelectedInstance(ActionEvent actionEvent) {
        for (Instance i : instances) {

            if (i.getId().equals(chosenInstanceId) && !instances.isEmpty()) {
                i.setQuantity(getQuantity());
                selectedInstances.add(i);
                instances.remove(i);
                showMessage("Elemento " + i.getType() + " adicionado");
                break;
            } else {
                System.out.println("Not found!!");
            }

        }

    }

    /**
     * Removes an element from the selected instances list
     *
     * @param element
     */
    public void removeElement(Instance element) {
        if (!selectedInstances.isEmpty()) {
            selectedInstances.remove(element);
            instances.add(element);
            showMessage("Elemento " + element.getType() + " removido");
        } else {
            showMessage("Não existem elementos para ser removidos!");
        }
    }

    /**
     * @return the instance
     */
    public Instance getInstance() {
        return instance;
    }

    /**
     * @param instance the instance to set
     */
    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    /**
     * @return the chosenInstanceId
     */
    public String getChosenInstanceId() {
        return chosenInstanceId;
    }

    /**
     * @param chosenInstanceId the chosenInstanceId to set
     */
    public void setChosenInstanceId(String chosenInstanceId) {
        this.chosenInstanceId = chosenInstanceId;
    }

   

//-------------------------------------------------------------------------

    /**
     * @return the limitationValueExecutionTime
     */
    public String getLimitationValueExecutionTime() {
        return limitationValueExecutionTime;
    }

    /**
     * @param limitationValueExecutionTime the limitationValueExecutionTime to set
     */
    public void setLimitationValueExecutionTime(String limitationValueExecutionTime) {
        this.limitationValueExecutionTime = limitationValueExecutionTime;
    }

    /**
     * @return the limitationValueExecutionCost
     */
    public String getLimitationValueExecutionCost() {
        return limitationValueExecutionCost;
    }

    /**
     * @param limitationValueExecutionCost the limitationValueExecutionCost to set
     */
    public void setLimitationValueExecutionCost(String limitationValueExecutionCost) {
        this.limitationValueExecutionCost = limitationValueExecutionCost;
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the objective
     */
    public Integer getObjective() {
        return objective;
    }

    /**
     * @param objective the objective to set
     */
    public void setObjective(Integer objective) {
        this.objective = objective;
    }

    public  String getProvider() {
        return provider;
    }
    public String getObjetive(){
        switch(this.objective){
            case 1:
                objetive="Desempenho";
                break;
            case 2:
                objetive= "Menor Custo";
                break;
            case 3:
                objetive= "Custo/Benificio";
                break;
            
        }
        return objetive;
    }
}
