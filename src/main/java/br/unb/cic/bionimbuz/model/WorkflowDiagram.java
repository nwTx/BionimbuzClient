package br.unb.cic.bionimbuz.model;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.endpoint.RectangleEndPoint;
import org.primefaces.model.diagram.overlay.ArrowOverlay;

public class WorkflowDiagram {
    // Constants
    private static final int INITIAL_X_POSITION = 1;
    private static final int X_POSITION_INCREMENT = 15;
    private static final String Y_POSITION = "15em";

    // Primefaces diagram model
    private DefaultDiagramModel workflowModel;

    // Elements of the pipeline
    private Element fromElement;
    private Element toElement;
    private StraightConnector connector;

    // X position of the element
    private int elementXPosition = INITIAL_X_POSITION;

    // The Workflow object
    private final Workflow workflow;

    /**
     * Calls the method that initializes everything...
     *
     * @param userId
     * @param description
     * @param elements
     * @throws java.net.MalformedURLException
     */
    public WorkflowDiagram(Long userId, String description, ArrayList<DiagramElement> elements) throws MalformedURLException {
        // Initializes Workflow
        this.workflow = new Workflow(userId, description);
        initialize(elements);
    }

    /**
     * Initializes variables, models, lists...
     */
    private void initialize(ArrayList<DiagramElement> elements) throws MalformedURLException {
        // Initializes Workflow Model
        workflowModel = new DefaultDiagramModel();
        workflowModel.setMaxConnections(-1);
        workflowModel.getDefaultConnectionOverlays().add(new ArrowOverlay(20, 20, 1, 1));

        // Creates the Connector type
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle(DiagramStyle.CONNECTOR_STYLE);
        connector.setHoverPaintStyle(DiagramStyle.CONNECTOR_HOVER_STYLE);
        workflowModel.setDefaultConnector(connector);

        // ToDo: Verificar addElement(inicio)
        fromElement = createNewElement(new DiagramElement("Inicio"), getElementXPosition(), Y_POSITION);

        workflowModel.addElement(fromElement);

        for (DiagramElement d : elements) {
            addElement(d);
        }

        // Finish the workflow putting a "Fim" element
        endWorkflow();
    }

    /**
     * Adds a sequential program element to the workflow model
     *
     * @param element
     */
    public void addElement(DiagramElement element) {
        // Create new element
        toElement = createNewElement(element, getElementXPosition(), Y_POSITION);

        // Add it to the model and connects it 
        workflowModel.addElement(toElement);

        // Turn the new element the new FROM element
        fromElement = toElement;

        // Creates the new Job
        WorkflowJobInfo newJob;
        try {
            newJob = new WorkflowJobInfo(element.getId());
            newJob.setServiceId(element.getServiceId());
            newJob.setTimestamp(Calendar.getInstance().getTime().toString());

            // Adds it to the pipeline
            workflow.addJobToPipeline(newJob);
        } catch (MalformedURLException ex) {
            Logger.getLogger(WorkflowDiagram.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Returns a new diagram element
     *
     * @param text
     * @return
     */
    private Element createNewElement(DiagramElement element, String xPosition, String yPosition) {
        // Completes element data
        element.setId(UUID.randomUUID().toString().substring(0, 8));
        element.setxPosition(Integer.parseInt(xPosition.substring(0, (xPosition.length() - 2))));   // take out 'em' from 10em
        element.setyPosition(Integer.parseInt(yPosition.substring(0, (xPosition.length() - 2))));

        // Create new element
        Element newElement = new Element(element, xPosition, yPosition);

        // Creates Rectangle End Point
        EndPoint rectEndPoint = new RectangleEndPoint(EndPointAnchor.RIGHT);
        rectEndPoint.setSource(true);
        rectEndPoint.setStyle(DiagramStyle.RECTANGLE_STYLE);
        rectEndPoint.setHoverStyle(DiagramStyle.RECTANGLE_HOVER_STYLE);
        rectEndPoint.setSource(true);

        // Creates Dot End Point
        DotEndPoint dotEndPoint = new DotEndPoint(EndPointAnchor.LEFT);
        dotEndPoint.setTarget(true);
        dotEndPoint.setStyle(DiagramStyle.DOT_STYLE);
        dotEndPoint.setHoverStyle(DiagramStyle.DOT_HOVER_STYLE);
        dotEndPoint.setTarget(true);

        // Adds it to the new element
        newElement.addEndPoint(rectEndPoint);
        newElement.addEndPoint(dotEndPoint);

        return newElement;
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
     * Finishes workflow putting and "Fim" element at the end
     */
    public void endWorkflow() {
        // Create new element
        toElement = createNewElement(new DiagramElement("Fim"), getElementXPosition(), Y_POSITION);

        // Add it to the model and connects it
        workflowModel.addElement(toElement);

        // Turn the new element the new FROM element
        fromElement = toElement;
    }

    /**
     * Updates a WorkflowJobInfo putting an input data at it
     *
     * @param id
     * @param inputs
     */
    public void setInputFile(String id, ArrayList<UploadedFileInfo> inputs) {
        int cont = 0;

        // Iterates over the joblist
        for (WorkflowJobInfo j : (ArrayList<WorkflowJobInfo>) workflow.getPipeline()) {

            if (j.getId().equals(id)) {
                // Gets the right job
                WorkflowJobInfo job = workflow.getPipeline().get(cont);

                // Sets its input
                job.setInputFiles(inputs);

                // When added, reinsert this job on the pipeline
                workflow.getPipeline().set(cont, job);

                return;
            }

            cont++;
        }
    }

    public DefaultDiagramModel getWorkflowModel() {
        return this.workflowModel;
    }

    public Workflow getWorkflow() {
        return this.workflow;
    }
}
