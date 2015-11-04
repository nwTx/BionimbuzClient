package br.unb.cic.bionimbuz.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unb.cic.bionimbuz.info.FileInfo;

public class JobInfo {

	private String id = UUID.randomUUID().toString();

	private String localId;

	private long serviceId;

	private String args = "";

	private List<FileInfo> inputs = new ArrayList<FileInfo>();

	private List<String> outputs = new ArrayList<String>();

	private long timestamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public List<FileInfo> getInputs() {
		return inputs;
	}

	public void addInput(FileInfo fileInfo) {
		// Verify if it wasn't already added
		for (FileInfo f : inputs) {
			if (f.getId().equals(fileInfo.getId())) {
				return;
			}
		}
		inputs.add(fileInfo);
	}

	public List<String> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<String> outputs) {
		this.outputs = outputs;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
