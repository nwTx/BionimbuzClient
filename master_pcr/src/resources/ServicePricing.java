package resources;

public class ServicePricing {
	public int idProvider;
	public String nameProvider;
	public double costBucket;
	public double costDownIn;
	public double costDownEx;
	
	public ServicePricing(int idProvider, String nameProvider, double costBucket, double costDownIn,
			double costDownEx) {
		super();
		this.idProvider = idProvider;
		this.nameProvider = nameProvider;
		this.costBucket = costBucket;
		this.costDownIn = costDownIn;
		this.costDownEx = costDownEx;
	}
	public int getIdProvider() {
		return idProvider;
	}
	public void setIdProvider(int idProvider) {
		this.idProvider = idProvider;
	}
	public String getNameProvider() {
		return nameProvider;
	}
	public void setNameProvider(String nameProvider) {
		this.nameProvider = nameProvider;
	}
	public double getCostBucket() {
		return costBucket;
	}
	public void setCostBucket(double costBucket) {
		this.costBucket = costBucket;
	}
	public double getCostDownIn() {
		return costDownIn;
	}
	public void setCostDownIn(double costDownIn) {
		this.costDownIn = costDownIn;
	}
	public double getCostDownEx() {
		return costDownEx;
	}
	public void setCostDownEx(double costDownEx) {
		this.costDownEx = costDownEx;
	}
	
}
