package br.unb.cic.bionimbuz.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JobInfo {

    private String id = UUID.randomUUID().toString();

    private String localId;

    private long serviceId;

    private String args = "";

    private List<UploadedFileInfo> inputs;

    private List<String> outputs = new ArrayList<String>();

    private String timestamp;

    public JobInfo() {
        this.inputs = new ArrayList<UploadedFileInfo>();
        this.outputs = new ArrayList<String>();
    }

    public String getId() {
        return id;
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

    public List<UploadedFileInfo> getInputs() {
        return inputs;
    }

    public void addInput(UploadedFileInfo fileInfo) {
        // Verify if it wasn't already added
        for (UploadedFileInfo f : inputs) {
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
