package br.unb.cic.bionimbuz.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class SubmitJobBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<String> list;
	private String label;

	@PostConstruct
	public void init() {
		list = new ArrayList<String>();

		list.add("Bowtie");
		list.add("Coverage 2 Bed");
		list.add("SAM 2 Bed");
	}

	public void choose(String label) {
		this.label = label;
	}
	
	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
