package br.unb.cic.bionimbuz.model;

import java.util.ArrayList;
import java.util.List;

public class JobInfo {

    private String id;

    private String localId;

    private long serviceId;

    private String args = "";

    private List<InputData> inputs;

    private List<String> outputs = new ArrayList<>();

    private String timestamp;

    public JobInfo() {
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
    }

    public JobInfo(String id) {
        this.id = id;
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
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

    public List<InputData> getInputs() {
        return inputs;
    }

    public void setInputs(List<InputData> inputs) {
        this.inputs = inputs;
    }

    public void addInput(InputData input) {
        inputs.add(input);
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
