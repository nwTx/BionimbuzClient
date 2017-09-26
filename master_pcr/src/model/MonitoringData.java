package model;
//time ~ qntReads + tamArq + cpu.usage + qntCPU
public class MonitoringData {

	private double time;
	private double nReads;
	private double sizeFile;
	private double cpuUsage;
	private double nCpu;
	private double nProcs;	
	
	public MonitoringData(double time, double nReads, double sizeFile, double cpuUsage, double nCpu, double nProcs) {
		super();
		this.time = time;
		this.nReads = nReads;
		this.sizeFile = sizeFile;
		this.cpuUsage = cpuUsage;
		this.nCpu = nCpu;
		this.nProcs = nProcs;
	}
	public MonitoringData() {
		// TODO Auto-generated constructor stub
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public double getnReads() {
		return nReads;
	}
	public void setnReads(double nReads) {
		this.nReads = nReads;
	}
	public double getSizeFile() {
		return sizeFile;
	}
	public void setSizeFile(double sizeFile) {
		this.sizeFile = sizeFile;
	}
	public double getCpuUsage() {
		return cpuUsage;
	}
	public void setCpuUsage(double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}
	public double getnCpu() {
		return nCpu;
	}
	public void setnCpu(double nCpu) {
		this.nCpu = nCpu;
	}
	public double getnProcs() {
		return nProcs;
	}
	public void setnProcs(double nProcs) {
		this.nProcs = nProcs;
	}
	
	@Override
	public String toString() {
		return "MonitoringData [time=" + time + ", nReads=" + nReads + ", sizeFile=" + sizeFile + ", cpuUsage="
				+ cpuUsage + ", nCpu=" + nCpu + ", nProcs=" + nProcs + "]\n";
	}

}
