package br.unb.cic.bionimbuz.test;

import java.util.List;

import br.unb.cic.bionimbuz.model.UploadedFileInfo;

public class JobStatusInfoTest {

    private String idJob;
    private List<UploadedFileInfo> fileList;
    private List<String> workflow;

    public JobStatusInfoTest() {
    }

    public String getIdJob() {
        return idJob;
    }

    public void setIdJob(String idJob) {
        this.idJob = idJob;
    }

    public List<UploadedFileInfo> getFileList() {
        return fileList;
    }

    public void setFileList(List<UploadedFileInfo> fileList) {
        this.fileList = fileList;
    }

    public List<String> getWorkflow() {
        return workflow;
    }

    public void setWorkflow(List<String> workflow) {
        this.workflow = workflow;
    }

}
