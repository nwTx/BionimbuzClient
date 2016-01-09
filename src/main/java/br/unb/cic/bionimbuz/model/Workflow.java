package br.unb.cic.bionimbuz.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Workflow {

    private final String id = "Workflow" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "-" + UUID.randomUUID().toString().substring(0, 13);

    private final List<JobInfo> jobs;

    private final String creationDatestamp;

    private final Long userId;

    private final String description;

    private WorkflowStatus status;

    public Workflow() {
        this.jobs = null;
        this.creationDatestamp = null;
        this.userId = null;
        this.description = null;
    }

    public Workflow(Long userId, String description) {
        this.userId = userId;
        this.creationDatestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        this.jobs = new ArrayList<>();
        this.description = description;
        this.status = WorkflowStatus.PENDING;
    }

    public String getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCreationDatestamp() {
        return creationDatestamp;
    }

    public String getDescription() {
        return description;
    }

    public List<JobInfo> getJobs() {
        return jobs;
    }

    public void addJob(JobInfo job) {
        this.jobs.add(job);
    }

    public void setStatus(WorkflowStatus status) {
        this.status = status;
    }

    public WorkflowStatus getStatus() {
        return status;
    }

}
