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
import br.unb.cic.bionimbuz.info.ProgramInfo;
import br.unb.cic.bionimbuz.model.User;
import br.unb.cic.bionimbuz.model.WorkflowDiagram;

@Named
@SessionScoped
public class PipelineComposerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject SessionBean sessionBean;
	
	// List of programs
	private List<ProgramInfo> sequentialProgramList = ConfigurationRepository.getSequentialProgramList().getPrograms();
	private List<ProgramInfo> parallelProgramList = ConfigurationRepository.getParallelProgramList().getPrograms();
	
	// Workflow
	private WorkflowDiagram workflowDiagram;
	
	// Control attributes
	private boolean parallelElementAdded = false;
	private boolean workflowFinished = false;
	
	// Logged user
	private User loggedUser;
	
	@PostConstruct
	public void init() {
		this.workflowDiagram = new WorkflowDiagram();
		this.loggedUser = sessionBean.getLoggedUser();
	}
	
	/**
	 * Adds a sequential element to the workflow diagram
	 */
	public void addSequentialElement(String program) {
		workflowDiagram.addSequentialElement(program);
		
		// Reset variable
        parallelElementAdded = false;
        
        showMessage("Elemento " + program + " adicionado");
	}
	
	/**
	 * Add parallel element to the workflow diagram
	 * @param program
	 */
	public void addParallelElement(String program) {
		workflowDiagram.addParallelElement(program);
	
		// Tells that a parallel element was added 
		parallelElementAdded = true;
		
		showMessage("Elemento " + program + " adicionado");
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
	 * @param msg
	 */
	private void showMessage (String msg) {
		FacesMessage message = new FacesMessage(msg, "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void endWorkflow() {
		workflowDiagram.endWorkflow();
        
        // Finish workflow
        workflowFinished = true;
		
        showMessage("Workflow finalizado");
	}
	
	public DefaultDiagramModel getWorkflowModel() {
		return workflowDiagram.getWorkflow();
	}

	public List<ProgramInfo> getSequentialProgramList() {
		return sequentialProgramList;
	}

	public List<ProgramInfo> getParallelProgramList() {
		return parallelProgramList;
	}

	public int getWorkflowIndex() {
		return workflowDiagram.getWorkflowIndex();
	}

	public boolean isParallelElementAdded() {
		return parallelElementAdded;
	}
	
	public boolean isWorkflowFinished() {
		return workflowFinished;
	}

	public User getLoggedUser() {
		return loggedUser;
	}
}
