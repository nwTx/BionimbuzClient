package br.unb.cic.bionimbuz.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Workflow {

    private final String id = UUID.randomUUID().toString();

    private final List<JobInfo> pipeline;

    private final String creationDatestamp;

    private final User user;

    private final String description;

    private WorkflowStatus status;

    public Workflow(User user, String description) {
        this.user = user;
        this.creationDatestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        this.pipeline = new ArrayList<>();
        this.description = description;
        this.status = WorkflowStatus.PENDING;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getCreationDatestamp() {
        return creationDatestamp;
    }

    public String getDescription() {
        return description;
    }

    public List<JobInfo> getPipeline() {
        return pipeline;
    }

    public void addJobToPipeline(JobInfo job) {
        this.pipeline.add(job);
    }

    public void setStatus(WorkflowStatus status) {
        this.status = status;
    }

    public WorkflowStatus getStatus() {
        return status;
    }

}
