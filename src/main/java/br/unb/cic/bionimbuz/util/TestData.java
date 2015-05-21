package br.unb.cic.bionimbuz.util;

import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class TestData {
	private ArrayList<TestFile> fileList = new ArrayList<TestFile>();
	private ArrayList<String> jobList = new ArrayList<String>();

	public TestData() {
		fileList.add(new TestFile("input1_sam_2_bed.txt", "173.3 MB", "12/01/2014 17:32"));
		fileList.add(new TestFile("input2_sam_2_bed.txt", "145.8 MB", "11/03/2014 12:12"));
		fileList.add(new TestFile("genome2interval_input.txt", "162.1 MB", "29/06/2014 11:57"));
		fileList.add(new TestFile("coverage_2_bed.txt", "121.1 MB", "01/01/2015 03:10"));
	}

	public ArrayList<TestFile> getFileList() {
		return fileList;
	}

	public void setFileList(ArrayList<TestFile> fileList) {
		this.fileList = fileList;
	}

	public ArrayList<String> getJobList() {
		return jobList;
	}

	public void setJobList(ArrayList<String> jobList) {
		this.jobList = jobList;
	}

}
