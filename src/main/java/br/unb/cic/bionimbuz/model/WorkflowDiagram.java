package br.unb.cic.bionimbuz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.diagram.ConnectEvent;
import org.primefaces.event.diagram.ConnectionChangeEvent;
import org.primefaces.event.diagram.DisconnectEvent;
import org.primefaces.model.diagram.Connection;

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

    // The Pipeline object
    private Pipeline pipeline;

    /**
     * Calls the method that initializes everything...
     *
     * @param user
     * @param description
     */
    public WorkflowDiagram(User user, String description) {
        // Initializes Pipeline
        this.pipeline = new Pipeline(user, description);
        initialize();
    }

    /**
     * Initializes variables, models, lists...
     */
    private void initialize() {
        // Initializes Workflow Model
        workflowModel = new DefaultDiagramModel();
        workflowModel.setMaxConnections(-1);
        workflowModel.getDefaultConnectionOverlays().add(new ArrowOverlay(20, 20, 1, 1));

        // Creates the Connector type
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle(DiagramStyle.CONNECTOR_STYLE);
        connector.setHoverPaintStyle(DiagramStyle.CONNECTOR_HOVER_STYLE);
        workflowModel.setDefaultConnector(connector);

        fromElement = createNewElement("Inicio", getElementXPosition(), Y_POSITION);

        workflowModel.addElement(fromElement);
    }

    /**
     * Adds a sequential program element to the workflow model
     *
     * @param program
     */
    public void addElement(ProgramInfo program) {
        // Creates the new Job
        JobInfo newJob = new JobInfo();
        newJob.setServiceId(program.getId());
        newJob.setTimestamp(Calendar.getInstance().getTime().toString());

        // Adds it to the pipeline
        pipeline.addJobToPipeline(newJob);

        // Create new element
        toElement = createNewElement(program.getName(), getElementXPosition(), Y_POSITION);

        // Add it to the model and connects it 
        workflowModel.addElement(toElement);

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

    public void endWorkflow() {
        // Create new element
        toElement = createNewElement("Fim", getElementXPosition(), Y_POSITION);

        // Add it to the model and connects it
        workflowModel.addElement(toElement);

        // Turn the new element the new FROM element
        fromElement = toElement;
    }

    public DefaultDiagramModel getWorkflow() {
        return this.workflowModel;
    }
}
