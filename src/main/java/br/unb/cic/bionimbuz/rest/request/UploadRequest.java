package br.unb.cic.bionimbuz.rest.request;

import br.unb.cic.bionimbuz.model.UploadedFileInfo;

public class UploadRequest implements RequestInfo {

    private UploadedFileInfo uploadedFileInfo;

    public UploadRequest() {
    }

    public UploadRequest(UploadedFileInfo fileInfo) {
        this.uploadedFileInfo = fileInfo;
    }

    public UploadedFileInfo getUploadedFileInfo() {
        return uploadedFileInfo;
    }

    public void setUploadedFileInfo(UploadedFileInfo uploadedFileInfo) {
        this.uploadedFileInfo = uploadedFileInfo;
    }

}
