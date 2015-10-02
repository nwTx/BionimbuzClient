package br.unb.cic.bionimbuz.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;

import br.unb.cic.bionimbuz.configuration.ConfigurationRepository;
import br.unb.cic.bionimbuz.info.ProgramInfo;

@Named
@SessionScoped
public class SubmitJobBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<ProgramInfo> sequentialProgramList = ConfigurationRepository.getSequentialProgramList().getPrograms();
	private List<ProgramInfo> parallelProgramList = ConfigurationRepository.getParallelProgramList().getPrograms();
	private DefaultDiagramModel workflowModel;
	private List<Element> undoWorkflowList;
	
	/* Workflow controllers */
	private int workflowIndex = 0;
	private int elementYPosition = 2;
	private Element fromElement;
	private Element toElement;
	private StraightConnector connector;
	private boolean parallelElementAdded = false;
	
	@PostConstruct
	public void init() {
		/* Initializes Workflow Model */
		workflowModel = new DefaultDiagramModel();
		workflowModel.setMaxConnections(-1);
		
		/* Initializes workflow list */
		undoWorkflowList = new ArrayList<Element>();
		
		/* First element > "Início" */
		fromElement = new Element("Início", "25em", getElementYPosition() + "em");
		fromElement.addEndPoint(createEndPoint(EndPointAnchor.TOP));
		fromElement.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));

		workflowModel.addElement(fromElement);
		
        connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'#404a4e', lineWidth:3}");
        connector.setHoverPaintStyle("{strokeStyle:'#20282b'}");
        
        undoWorkflowList.add(fromElement);
        
	}
	
	/**
	 * Adds a sequential element to the workflow diagram
	 */
	public void addSequentialElement(String program) {
		/* Create new element */
		toElement = new Element(program, "25em", getElementYPosition() + "em");
        toElement.addEndPoint(createEndPoint(EndPointAnchor.TOP));
        toElement.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));
        
        /* Adds workflow model to workflow list and update its index */
        undoWorkflowList.add(toElement);
        workflowIndex++;
		
        /* Add it to the model and connects it */
        workflowModel.addElement(toElement);
        workflowModel.connect(new Connection(fromElement.getEndPoints().get(1), toElement.getEndPoints().get(0), connector));

        /* Turn the new element the new FROM element */
        fromElement = toElement;
        
        /* Reset variable */
        parallelElementAdded = false;
        
        showMessage("Elemento " + program + " adicionado");
	}
	
	/**
	 * Add parallel element to the workflow diagram
	 * @param program
	 */
	public void addParallelElement(String program) {
		System.out.println(program);
		
		String yPosition = getElementYPosition();
		
		/* Create new element */
		toElement = new Element(program, "15em", yPosition + "em");
        toElement.addEndPoint(createEndPoint(EndPointAnchor.TOP));
        toElement.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));
        
        Element sElement = new Element(program, "35em", yPosition + "em");
        sElement.addEndPoint(createEndPoint(EndPointAnchor.TOP));
        sElement.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));
        
        /* Adds workflow model to workflow list and update its index */
        undoWorkflowList.add(toElement);
        workflowIndex++;
		
        /* Add it to the model and connects it */
        workflowModel.addElement(toElement);
        workflowModel.addElement(sElement);
        workflowModel.connect(new Connection(fromElement.getEndPoints().get(1), toElement.getEndPoints().get(0), connector));
        workflowModel.connect(new Connection(fromElement.getEndPoints().get(1), sElement.getEndPoints().get(0), connector));
        
		parallelElementAdded = true;
	}
	
	/**
	 * Resets current workflow
	 */
	public void resetWorkflow() {
		elementYPosition = 2;
		workflowIndex = 0;
		init();
		
		showMessage("Workflow reiniciado");
	}
	
	/**
	 * Undo an element addition and updates the references
	 */
	public void undoAddition() {
		workflowModel.removeElement(undoWorkflowList.get(workflowIndex));
		fromElement = undoWorkflowList.get(workflowIndex - 1);
		undoWorkflowList.remove(workflowIndex);
		workflowIndex--;
		
		elementYPosition -= 10;
		
		showMessage("Ação desfeita");
	}
	
	/**
	 * Show message in growl component (View)
	 * @param msg
	 */
	private void showMessage (String msg) {
		FacesMessage message = new FacesMessage(msg, "");
		FacesContext.getCurrentInstance().addMessage(null, message);
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
	 * Returns Y position of element (int -> String)
	 * @return
	 */
	private String getElementYPosition() {
		String yPosition = Integer.toString(elementYPosition); 
		elementYPosition += 10;
		
		return yPosition;
	}
	
	public DefaultDiagramModel getWorkflowModel() {
		return workflowModel;
	}

	public List<ProgramInfo> getSequentialProgramList() {
		return sequentialProgramList;
	}

	public List<ProgramInfo> getParallelProgramList() {
		return parallelProgramList;
	}

	public List<Element> getUndoWorkflowList() {
		return undoWorkflowList;
	}

	public int getWorkflowIndex() {
		return workflowIndex;
	}

	public boolean isParallelElementAdded() {
		return parallelElementAdded;
	}

}
