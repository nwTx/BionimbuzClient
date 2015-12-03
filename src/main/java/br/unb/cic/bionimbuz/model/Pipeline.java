package br.unb.cic.bionimbuz.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Pipeline {

    private String id = UUID.randomUUID().toString();

    private User user;

    private Date creationDatestamp;

    private String description;

    private List<JobInfo> pipeline;

    public Pipeline(User user, String description) {
        this.user = user;
        this.creationDatestamp = Calendar.getInstance().getTime();
        this.pipeline = new ArrayList<JobInfo>();
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreationDatestamp() {
        return creationDatestamp;
    }

    public void setCreationDatestamp() {
        this.creationDatestamp.setTime(Calendar.getInstance().getTime().getTime());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<JobInfo> getPipeline() {
        return pipeline;
    }

    public void addJobToPipeline(JobInfo job) {
        this.pipeline.add(job);
    }

}
