package br.unb.cic.bionimbuz.pipeline;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import br.unb.cic.bionimbuz.model.JobInfo;
import br.unb.cic.bionimbuz.model.User;

public class Pipeline {
	private String id = UUID.randomUUID().toString();

	private User user;

	private Calendar creationDatestamp;

	private String description;

	private List<JobInfo> pipeline;

	public String getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Calendar getCreationDatestamp() {
		return creationDatestamp;
	}

	public void setCreationDatestamp() {
		this.creationDatestamp.setTime(Calendar.getInstance().getTime());;
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
