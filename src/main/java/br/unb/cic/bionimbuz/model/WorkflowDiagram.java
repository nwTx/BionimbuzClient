package br.unb.cic.bionimbuz.model;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;

public class WorkflowDiagram {
	private static final int     INITIAL_X_POSITION   = 1;
	private static final int     X_POSITION_INCREMENT = 15;
	private static final String  Y_POSITION		      = "15em";
	
	private DefaultDiagramModel workflowModel;
	private Element fromElement;
	private Element toElement;
	private StraightConnector connector;
	private int elementXPosition = INITIAL_X_POSITION;
	private List<Element> undoWorkflowList;
	private int workflowIndex = 0;
	
	/**
	 * Calls the method that initializes everything...
	 */
	public WorkflowDiagram() {
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
		fromElement = new Element("Início", getElementXPosition(), Y_POSITION);
		fromElement.addEndPoint(createEndPoint(EndPointAnchor.LEFT));
		fromElement.addEndPoint(createEndPoint(EndPointAnchor.RIGHT));
		
		// Adds the 'from element' to the diagram 
		workflowModel.addElement(fromElement);
		
		connector = new StraightConnector();
		connector.setPaintStyle("{strokeStyle:'#404a4e', lineWidth:2}");
		connector.setHoverPaintStyle("{strokeStyle:'#20282b'}");
		
		undoWorkflowList.add(fromElement);
	}
	
	/**
	 * Adds a sequential program element to the workflow model
	 * @param program
	 */
	public void addSequentialElement(String program) {
		// Create new element
		toElement = new Element(program, getElementXPosition(), Y_POSITION);
        toElement.addEndPoint(createEndPoint(EndPointAnchor.LEFT));
        toElement.addEndPoint(createEndPoint(EndPointAnchor.RIGHT));
        
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
	 * @param program
	 */
	public void addParallelElement(String program) {
		String xPosition = getElementXPosition();
		
		// Create new element
		toElement = new Element(program, xPosition, Y_POSITION);
        toElement.addEndPoint(createEndPoint(EndPointAnchor.LEFT));
        toElement.addEndPoint(createEndPoint(EndPointAnchor.RIGHT));
        
        Element sElement = new Element(program, xPosition, Y_POSITION);
        sElement.addEndPoint(createEndPoint(EndPointAnchor.LEFT));
        sElement.addEndPoint(createEndPoint(EndPointAnchor.RIGHT));
        
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
		toElement = new Element("Fim", getElementXPosition(), Y_POSITION);
        toElement.addEndPoint(createEndPoint(EndPointAnchor.LEFT));
        toElement.addEndPoint(createEndPoint(EndPointAnchor.RIGHT));
        
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
	 * Create DotEndPoint to connect elements
	 * @param anchor
	 * @return
	 */
	private EndPoint createEndPoint(EndPointAnchor anchor) {
        DotEndPoint endPoint = new DotEndPoint(anchor);
        endPoint.setStyle("{fillStyle:'#404a4e'}");
        endPoint.setHoverStyle("{fillStyle:'#20282b'}");
        
        return endPoint;
    }
	
	/**
	 * Returns X position of element (int -> String)
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
		
		/* Reset variables */
		initialize();
	}
	
	public DefaultDiagramModel getWorkflow() {
		return this.workflowModel;
	}
	
	public int getWorkflowIndex() {
		return this.workflowIndex;
	}
}
