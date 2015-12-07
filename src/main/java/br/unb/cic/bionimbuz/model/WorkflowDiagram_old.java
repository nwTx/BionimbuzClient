package br.unb.cic.bionimbuz.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;

public class WorkflowDiagram_old {
    // Constants
    private static final int INITIAL_X_POSITION = 1;
    private static final int X_POSITION_INCREMENT = 15;
    private static final String Y_POSITION = "15em";

    // Primefaces diagram model
    private DefaultDiagramModel workflowModel;
    
    private boolean suspendEvent;

    // Elements of the pipeline
    private Element fromElement;
    private Element toElement;
    private StraightConnector connector;

    // X position of the element
    private int elementXPosition = INITIAL_X_POSITION;

    // List to implement the 'undo' action
    private List<Element> undoWorkflowList;

    // Index to point to the actual element 
    private int workflowIndex = 0;

    // The Pipeline object
    private Pipeline pipeline;

    /**
     * Calls the method that initializes everything...
     */
    public WorkflowDiagram_old(User user, String description) {
        // Initializes Pipeline
        this.pipeline = new Pipeline(user, description);
        initialize();
    }

    /**
     * Initializes variables, models, lists...
     */
    public void initialize() {
        // Initializes Workflow Model
        workflowModel = new DefaultDiagramModel();
        workflowModel.setMaxConnections(-1);

        // Initializes workflow list
        undoWorkflowList = new ArrayList<Element>();

        // First element > "Início"
        fromElement = createNewElement("Início", getElementXPosition(), Y_POSITION);

        // Adds the 'from element' to the diagram 
        workflowModel.addElement(fromElement);

        connector = new StraightConnector();
        connector.setPaintStyle(DiagramStyle.CONNECTOR_STYLE);
        connector.setHoverPaintStyle(DiagramStyle.CONNECTOR_HOVER_STYLE);

        undoWorkflowList.add(fromElement);
    }

    /**
     * Adds a sequential program element to the workflow model
     *
     * @param program
     */
    public void addSequentialElement(ProgramInfo program, UploadedFileInfo inputFile) {
        // Creates the new Job
        JobInfo newJob = new JobInfo();
        newJob.setServiceId(program.getId());
        newJob.setTimestamp(Calendar.getInstance().getTime().toString());
        newJob.addInput(inputFile);

        // Adds it to the pipeline
        pipeline.addJobToPipeline(newJob);

        // Create new element
        toElement = createNewElement(program.getName(), getElementXPosition(), Y_POSITION);

        // Adds workflow model to workflow list and update its index 
        undoWorkflowList.add(toElement);
        workflowIndex++;

        // Add it to the model and connects it 
        workflowModel.addElement(toElement);
        workflowModel.connect(new Connection(fromElement.getEndPoints().get(1), toElement.getEndPoints().get(0), connector));

        // Turn the new element the new FROM element
        fromElement = toElement;
    }

    /**
     * Add parallel element to the workflow diagram
     *
     * @param program
     */
    public void addParallelElement(String program) {
        String xPosition = getElementXPosition();

        // Create new element
        toElement = createNewElement(program, xPosition, Y_POSITION);

        Element sElement = createNewElement(program, xPosition, Y_POSITION);

        // Adds workflow model to workflow list and update its index
        undoWorkflowList.add(toElement);
        workflowIndex++;

        // Add it to the model and connects it
        workflowModel.addElement(toElement);
        workflowModel.addElement(sElement);
        workflowModel.connect(new Connection(fromElement.getEndPoints().get(1), toElement.getEndPoints().get(0), connector));
        workflowModel.connect(new Connection(fromElement.getEndPoints().get(1), sElement.getEndPoints().get(0), connector));
    }

    public void endWorkflow() {
        // Create new element
        toElement = createNewElement("Fim", getElementXPosition(), Y_POSITION);

        // Adds workflow model to workflow list and update its index
        undoWorkflowList.add(toElement);
        workflowIndex++;

        // Add it to the model and connects it
        workflowModel.addElement(toElement);
        workflowModel.connect(new Connection(fromElement.getEndPoints().get(1), toElement.getEndPoints().get(0), connector));

        // Turn the new element the new FROM element
        fromElement = toElement;
    }

    /**
     * Returns a new diagram element
     *
     * @param text
     * @return
     */
    private Element createNewElement(String text, String xPosition, String yPosition) {
        // Create new element
        Element newElement = new Element(text, xPosition, yPosition);
        newElement.addEndPoint(createEndPoint(EndPointAnchor.LEFT));
        newElement.addEndPoint(createEndPoint(EndPointAnchor.RIGHT));

        return newElement;
    }

    /**
     * Create DotEndPoint to connect elements
     *
     * @param anchor
     * @return
     */
    private EndPoint createEndPoint(EndPointAnchor anchor) {
        DotEndPoint endPoint = new DotEndPoint(anchor);
        endPoint.setStyle(DiagramStyle.DOT_STYLE);
        endPoint.setHoverStyle(DiagramStyle.DOT_HOVER_STYLE);

        return endPoint;
    }

    /**
     * Returns X position of element (int -> String)
     *
     * @return
     */
    private String getElementXPosition() {
        String xPosition = Integer.toString(elementXPosition);
        elementXPosition += X_POSITION_INCREMENT;

        return (xPosition + "em");
    }

    /**
     * Undo an element addition and updates the references
     */
    public void undoAddition() {
        workflowModel.removeElement(undoWorkflowList.get(workflowIndex));
        fromElement = undoWorkflowList.get(workflowIndex - 1);
        undoWorkflowList.remove(workflowIndex);
        workflowIndex--;

        elementXPosition -= X_POSITION_INCREMENT;
    }

    /**
     * Resets current workflow
     */
    public void resetWorkflow() {
        elementXPosition = INITIAL_X_POSITION;
        workflowIndex = 0;

        // Reset variables
        initialize();
    }

    public DefaultDiagramModel getWorkflow() {
        return this.workflowModel;
    }

    public int getWorkflowIndex() {
        return this.workflowIndex;
    }
}
