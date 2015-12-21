package br.unb.cic.bionimbuz.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Workflow {

    private final String id = UUID.randomUUID().toString();

    private final List<JobInfo> pipeline;

    private final Date creationDatestamp;

    private final User user;

    private final String description;

    public Workflow(User user, String description) {
        this.user = user;
        this.creationDatestamp = Calendar.getInstance().getTime();
        this.pipeline = new ArrayList<>();
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Date getCreationDatestamp() {
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

}
