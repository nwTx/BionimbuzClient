package resources;

public class ServiceWorkflow {
	public int idService;
	public String nameService;
	public double qntReads;
	public double fileInput;
	public double timeEstimated;
	public double costExecution;
		
	public ServiceWorkflow(int idService, String nameService, double qntReads, double fileInput, double timeEstimated,
			double costExecution) {
		super();
		this.idService = idService;
		this.nameService = nameService;
		this.qntReads = qntReads;
		this.fileInput = fileInput;
		this.timeEstimated = timeEstimated;
		this.costExecution = costExecution;
	}
	public double getTimeEstimated() {
		return timeEstimated;
	}
	public void setTimeEstimated(double timeEstimated) {
		this.timeEstimated = timeEstimated;
	}
	public double getCostExecution() {
		return costExecution;
	}
	public void setCostExecution(double costExecution) {
		this.costExecution = costExecution;
	}
	
	public int getIdService() {
		return idService;
	}
	public void setIdService(int idService) {
		this.idService = idService;
	}
	public String getNameService() {
		return nameService;
	}
	public void setNameService(String nameService) {
		this.nameService = nameService;
	}
	public double getQntReads() {
		return qntReads;
	}
	public void setQntReads(double qntReads) {
		this.qntReads = qntReads;
	}
	public double getFileInput() {
		return fileInput;
	}
	public void setFileInput(double fileInput) {
		this.fileInput = fileInput;
	}
	
}

